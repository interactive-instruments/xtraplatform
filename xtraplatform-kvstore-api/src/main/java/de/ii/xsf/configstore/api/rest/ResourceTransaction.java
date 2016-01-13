/**
 * Copyright 2016 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ii.xsf.configstore.api.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ii.xsf.configstore.api.MultiTransaction;
import de.ii.xsf.configstore.api.Transaction;
import de.ii.xsf.configstore.api.WriteTransaction;
import de.ii.xsf.core.api.Resource;
import de.ii.xsf.core.api.exceptions.ResourceNotFound;
import de.ii.xsf.core.api.exceptions.WriteError;
import de.ii.xsf.logging.XSFLogger;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import org.forgerock.i18n.slf4j.LocalizedLogger;

/**
 *
 * @author zahnen
 */
public class ResourceTransaction<T extends Resource> extends MultiTransaction implements WriteTransaction<T> {

    private static final LocalizedLogger LOGGER = XSFLogger.getLogger(ResourceTransaction.class);

    public enum OPERATION {

        ADD,
        UPDATE,
        UPDATE_OVERRIDE,
        DELETE,
        DELETE_ALL
    }

    private  String[] path;
    private  String resourceId;
    protected  OPERATION operation;
    
    private ResourceSerializer<T> serializer;
    private T resource;
    private T emptyResource;
    private String serializedResource;
    private Lock resourceLock;
    private boolean closed;
    protected ResourceCache<T> cache;
    
    public ResourceTransaction() {
        this.closed = true;
    }
    
    public ResourceTransaction(String[] path, String resourceId, OPERATION operation) {
        this();
        this.path = path;
        this.resourceId = resourceId;
        this.operation = operation;
    }

    private void open() throws IOException {
        if (resourceLock == null) {
            LOGGER.getLogger().error("resourceLock is not set {}", this);
            throw new IOException("resourceLock is not set");
        }
        LOGGER.getLogger().debug("LOCK RT");

        if (closed) {
            resourceLock.lock();
            this.closed = false;
        }
    }

    // TODO: do we need cache for this?
    protected void validate() {
        LOGGER.getLogger().debug("VALIDATE RT {} {} {}", operation, path, resourceId);

        switch (operation) {
            case ADD:
                if (cache.hasResource(resourceId)) {
                    throw new WriteError("A resource with id '" + resourceId + "' already exists");
                }
                break;
            case UPDATE:
            case UPDATE_OVERRIDE:
            case DELETE:
                // TODO: necessary?
                if (resourceId == null) {
                    throw new ResourceNotFound("A resource with id 'NULL' does not exist");
                }
                if (!cache.hasResource(resourceId)) {
                    throw new ResourceNotFound("A resource with id '" + resourceId + "' does not exist");
                }
                break;
            case DELETE_ALL:
            default:
        }
    }

    @Override
    public void execute() throws IOException {
        // TODO
        if (operation == OPERATION.DELETE_ALL) {
            LOGGER.getLogger().debug("IGNORE RT {}", operation);
            return;
        }

        open();

        validate();
        
        LOGGER.getLogger().debug("EXEC RT {} {} {} {} {} {}", operation, path, resourceId, serializedResource, resource, serializer);

        checkDeSerialization();

        for (Transaction t : transactions) {
            LOGGER.getLogger().debug("WRITE T {}", t);

            try {
                ((WriteTransaction<T>) t).write(resource);
            } catch (ClassCastException e) {
                try {
                    ((WriteTransaction<String>) t).write(serializedResource);
                } catch (ClassCastException e2) {
                    // ignore
                }
            }

            LOGGER.getLogger().debug("EXECUTE T {}", t);

            t.execute();
        }
    }

    private void checkDeSerialization() throws IOException {
        if (serializedResource == null) {
            if (operation == OPERATION.ADD) {
                this.serializedResource = serializer.serializeAdd(resource);
            } else {
                this.serializedResource = serializer.serializeUpdate(resource);
            }
            LOGGER.getLogger().debug("SERIALIZE R {}", serializedResource);
        } else if (resource == null) {
            LOGGER.getLogger().debug("DESERIALIZE R {}", serializedResource);
            this.resource = serializer.deserialize(emptyResource, new StringReader(serializedResource));
        }
    }

    @Override
    public void write(T resource) throws IOException {
        this.resource = resource;

        checkDeSerialization();
    }

    @Override
    public void rollback() {
        super.rollback(); 
        close();
    }

    @Override
    public void commit() {
        LOGGER.getLogger().debug("COMMIT RT {} {} {}", operation, path, resourceId);
        super.commit(); 
        close();
    }

    @Override
    public void close() {
        // TODO: rollback if no commit/rollback happened yet ???
        if (!closed) {
            resourceLock.unlock();
            this.closed = true;
        }
    }

    public OPERATION getOperation() {
        return operation;
    }

    public String[] getPath() {
        return path;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResource() {
        return serializedResource;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setOperation(OPERATION operation) {
        this.operation = operation;
    }

    public void setResource(String resource) {
        this.serializedResource = resource;
    }

    public void setEmptyResource(T emptyResource) {
        this.emptyResource = emptyResource;
    }

    public void setSerializer(ResourceSerializer<T> serializer) {
        this.serializer = serializer;
    }

    public void setResourceLock(Lock resourceLock) {
        this.resourceLock = resourceLock;
    }

    public void setCache(ResourceCache<T> cache) {
        this.cache = cache;
    }

    @Override
    @JsonIgnore
    public List<Transaction> getTransactions() {
        return super.getTransactions(); 
        
    }

    @Override
    public String toString() {
        return "ResourceTransaction {" +
                "\npath=" + Arrays.toString(path) +
                ", \nresourceId=" + resourceId +
                ", \noperation=" + operation +
                ", \nserializedResource=" + serializedResource +
                "\n}";
    }
}
