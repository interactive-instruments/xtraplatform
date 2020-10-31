package de.ii.xtraplatform.store.domain;

import org.immutables.value.Value;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value.Immutable
public interface EventFilter {
    String getEventType();

    List<String> getEntityTypes();

    List<String> getIds();

    default boolean matches(MutationEvent event) {
        Identifier identifier = event.identifier();

        if (!getEntityTypes().contains("*") && (identifier.path().isEmpty() || !getEntityTypes().contains(identifier.path().get(0)))) {
            return false;
        }

        //TODO
        if (!getIds().isEmpty() && !Objects.equals(event.type(), "defaults")) {
            String id = identifier.path().size() > 1 ? identifier.path().get(1) : identifier.id();

            return getIds().contains(id) || getIds().contains("*");
        }

        return true;
    }

    static EventFilter fromPath(Path path) {
        if (path.getNameCount() < 2) {
            return null;
        }

        String eventType = path.getName(0)
                       .toString();
        String entityType = path.getName(1)
                       .toString();
        if (entityType.contains(".")) {
            entityType = entityType.substring(0, entityType.indexOf("."));
        }

        ImmutableEventFilter.Builder builder = ImmutableEventFilter.builder()
                                                                   .eventType(eventType)
                                                                   .addEntityTypes(entityType);
        //TODO
        if (eventType.equals("defaults")) {
            builder.addIds("*");
        } else if (path.getNameCount() > 2) {
            String id = path.getName(2)
                           .toString();
            if (id.contains(".")) {
                id = id.substring(0, id.indexOf("."));
            }
            builder.addIds(id);
        }

        return builder.build();
    }
}