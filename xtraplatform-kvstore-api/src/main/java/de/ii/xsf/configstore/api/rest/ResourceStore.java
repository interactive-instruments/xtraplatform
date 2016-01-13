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

import de.ii.xsf.core.api.Resource;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author zahnen
 * @param <T>
 */
public interface ResourceStore<T extends Resource> {

    List<String> getResourceIds();

    T getResource(String id);

    boolean hasResource(String id);

    void addResource(T resource) throws IOException;

    void deleteResource(String id) throws IOException;

    void updateResource(T resource) throws IOException;

    void updateResourceOverrides(String id, T resource) throws IOException;
    
    ResourceStore<T> withParent(String storeId);

    ResourceStore<T> withChild(String storeId);
    
    List<String[]> getAllPaths();
}
