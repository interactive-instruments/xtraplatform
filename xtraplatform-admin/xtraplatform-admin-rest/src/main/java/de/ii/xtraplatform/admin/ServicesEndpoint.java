package de.ii.xtraplatform.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ii.xtraplatform.api.exceptions.BadRequest;
import de.ii.xtraplatform.dropwizard.api.Jackson;
import de.ii.xtraplatform.entity.api.EntityData;
import de.ii.xtraplatform.entity.api.EntityDataGenerator;
import de.ii.xtraplatform.entity.api.EntityRegistry;
import de.ii.xtraplatform.event.store.EntityDataStore;
import de.ii.xtraplatform.scheduler.api.TaskStatus;
import de.ii.xtraplatform.service.api.ImmutableServiceStatus;
import de.ii.xtraplatform.service.api.Service;
import de.ii.xtraplatform.service.api.ServiceBackgroundTasks;
import de.ii.xtraplatform.service.api.ServiceData;
import de.ii.xtraplatform.service.api.ServiceStatus;
import de.ii.xtraplatform.web.api.Endpoint;
import io.dropwizard.jersey.caching.CacheControl;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@Provides
@Instantiate
@Path("/admin/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesEndpoint implements Endpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesEndpoint.class);

    private final EntityDataStore<ServiceData> serviceRepository;
    private final EntityRegistry entityRegistry;
    private final ServiceBackgroundTasks serviceBackgroundTasks;
    private final EntityDataGenerator entityDataGenerator;
    private final ObjectMapper objectMapper;

    ServicesEndpoint(@Requires EntityDataStore<EntityData> entityRepository, @Requires EntityRegistry entityRegistry,
                     @Requires ServiceBackgroundTasks serviceBackgroundTasks, @Requires Jackson jackson, @Requires EntityDataGenerator entityDataGenerator) {
        this.serviceRepository = entityRepository.forType(ServiceData.class);
        this.entityRegistry = entityRegistry;
        this.serviceBackgroundTasks = serviceBackgroundTasks;
        this.objectMapper = jackson.getDefaultObjectMapper();
        this.entityDataGenerator = entityDataGenerator;
    }

    @GET
    @CacheControl(noCache = true)
    public List<ServiceStatus> getServices(/*@Auth AuthenticatedUser authUser*/) {
        return serviceRepository.ids()
                                .stream()
                                .map(this::getServiceStatus)
                                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addService(/*@Auth(minRole = Role.PUBLISHER) AuthenticatedUser authUser,*/
            Map<String, String> request) {

        if (!request.containsKey("id")) {
            throw new BadRequest("No id given");
        }

        String id = request.get("id");

        if (serviceRepository.has(id)) {
            throw new BadRequest("A service with id '" + id + "' already exists");
        }

        try {
            MDC.put("service", id);

            ServiceData serviceData = (ServiceData) entityDataGenerator.generate(request); //= serviceRepository.generateEntity(request);
            //throw BadRequest

            ServiceData added = serviceRepository.put(id, serviceData)
                                                 .get();

            return Response.ok()
                           .entity(getServiceStatus(added))
                           .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalServerErrorException();
        } finally {
            MDC.remove("service");
        }
    }

    @Path("/{id}")
    @GET
    @CacheControl(noCache = true)
    public Response getService(/*@Auth AuthenticatedUser user,*/ @PathParam("id") String id) {

        if (!serviceRepository.has(id)) {
            throw new NotFoundException();
        }

        ServiceData serviceData = serviceRepository.get(id);

        try {
            return Response.ok()
                           .entity(objectMapper.writeValueAsString(serviceData))
                           .build();
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @POST
    public Response updateService(/*@Auth AuthenticatedUser authUser,*/ @PathParam("id") String id,
                                                                        Map<String, Object> request) {

        if (!serviceRepository.has(id)) {
            throw new NotFoundException();
        }

        try {
            MDC.put("service", id);

            ServiceData updated = serviceRepository.patch(id, request)
                                                   .get();

            return Response.ok()
                           .entity(objectMapper.writeValueAsString(updated))
                           .build();
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                throw new BadRequest(e.getCause()
                                      .getMessage());
            }
            throw new InternalServerErrorException();
        } finally {
            MDC.remove("service");
        }
    }

    @Path("/{id}")
    @DELETE
    public Response deleteService(/*@Auth AuthenticatedUser authUser,*/ @PathParam("id") String id) {
        try {
            MDC.put("service", id);

            serviceRepository.delete(id)
                             .get();

            return Response.noContent()
                           .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalServerErrorException();
        } finally {
            MDC.remove("service");
        }
    }

    private ServiceStatus getServiceStatus(String id) {
        ServiceData serviceData = serviceRepository.get(id);

        return getServiceStatus(serviceData);
    }

    private ServiceStatus getServiceStatus(ServiceData serviceData) {

        boolean started = entityRegistry.getEntity(Service.class, serviceData.getId())
                                        .isPresent();

        boolean loading = serviceData.isLoading();

        Optional<TaskStatus> currentTaskForService = serviceBackgroundTasks.getCurrentTaskForService(serviceData.getId());


        ImmutableServiceStatus.Builder serviceStatus = ImmutableServiceStatus.builder()
                                                                             .from(serviceData)
                                                                             .status(started ? ServiceStatus.STATUS.STARTED : ServiceStatus.STATUS.STOPPED);
        if (currentTaskForService.isPresent()) {
            serviceStatus.hasBackgroundTask(true)
                         .progress((int) Math.round(currentTaskForService.get()
                                                                         .getProgress() * 100))
                         .message(String.format("%s: %s", currentTaskForService.get()
                                                                               .getLabel(), currentTaskForService.get()
                                                                                                                 .getStatusMessage()));
        } else if (loading) {
            serviceStatus.hasBackgroundTask(true)
                         .message("Initializing");
        }

        return serviceStatus.build();
    }

}