/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xsf.core.util.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.io.Files;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author fischer
 */
public class DeepUpdater<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeepUpdater.class);

    protected final ObjectMapper jsonMapper;

    public DeepUpdater(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void applyUpdate(T orig, File json) throws IOException {
        String update = Files.toString(json, Charset.forName("UTF-8"));
        applyUpdate(orig, update);
    }

    public T applyUpdate(T orig, String json) throws IOException {
        LOGGER.debug("APPLY UPDATE {}", json);
        ObjectNode update = (ObjectNode) jsonMapper.readTree(json);
        applyUpdate(orig, update);

        return orig;
    }

    public void applyUpdate(T orig, Reader jsonReader) throws IOException {
        ObjectNode update = (ObjectNode) jsonMapper.readTree(jsonReader);
        applyUpdate(orig, update);
    }

    public T applyUpdate(final T orig, final T obj) throws IOException {
        ObjectNode update = (ObjectNode) jsonMapper.valueToTree(obj);

        applyUpdate(orig, update);

        return orig;
    }

    // recursion
    public void applyUpdate(Object original, ObjectNode updateRoot) throws IOException {
        for (Iterator<Map.Entry<String, JsonNode>> i = updateRoot.fields(); i.hasNext(); ) {
            Map.Entry<String, JsonNode> fieldEntry = i.next();
            JsonNode child = fieldEntry.getValue();

            if (child.isArray()) {
                try {
                    // We ignore arrays so they get instantiated fresh every time
                    // root.remove(fieldEntry.getKey());
                    Object o2 = null;
                    if (original instanceof Map)
                        o2 = ((Map) original).get(fieldEntry.getKey());
                    else
                        o2 = PropertyUtils.getProperty(original, fieldEntry.getKey());
                    if (o2 != null && !(o2 instanceof int[])) {

                        this.processFieldOfTypeArray(i, fieldEntry, o2, (T) original);

                        int j = 0;
                        for (Object o3 : (Iterable) o2) {
                            if (child.has(j)) {
                                applyUpdate(o3, (ObjectNode) child.get(j));
                            }
                            j++;
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {

                }
            } else if (child.isObject()) {
                try {
                    Object o2 = null;
                    if (original instanceof Map)
                        o2 = ((Map) original).get(fieldEntry.getKey());
                    else
                        o2 = PropertyUtils.getProperty(original, fieldEntry.getKey());
                    if (o2 != null) {
                        // Only remove the JsonNode if the object already exists
                        // Otherwise it will be instantiated when the parent gets
                        // deserialized

                        this.processFieldOfTypeObject(i, fieldEntry, o2, child);

                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {

                }
            }
        }

        //TODO: workaround for Enums as Map keys, worked before upgrade to jackson 2.9
        if (original.getClass().equals(LinkedHashMap.class) && !((LinkedHashMap) original).isEmpty()) {
            Map.Entry entry = (Map.Entry) ((LinkedHashMap) original).entrySet().iterator().next();
            JavaType type = TypeFactory.defaultInstance().constructMapType(LinkedHashMap.class, entry.getKey().getClass(), entry.getValue().getClass());
            jsonMapper.readerForUpdating(original).forType(type).readValue(updateRoot);
            return;
        }
        jsonMapper.readerForUpdating(original).readValue(updateRoot);
    }

    protected void processFieldOfTypeObject(Iterator<Map.Entry<String, JsonNode>> i, Map.Entry<String, JsonNode> fieldEntry, Object o2, JsonNode child) throws IOException {
        i.remove();
        applyUpdate(o2, (ObjectNode) child);
    }

    /* ABLEITUNG
     protected void processFieldOfTypeObject(Iterator<Map.Entry<String, JsonNode>> i, Map.Entry<String, JsonNode> fieldEntry, Object o2, JsonNode child) throws IOException {
     if (!(fieldEntry.getKey().equals("elementMappings") && o2 instanceof Map && ((Map) o2).isEmpty())) {
     i.remove();
     execute(o2, (ObjectNode) child);
     }
     }*/
    protected void processFieldOfTypeArray(Iterator<Map.Entry<String, JsonNode>> i, Map.Entry<String, JsonNode> fieldEntry, Object o2, T original) {
        i.remove();

    }

    /* ABLEITUNG
     protected void processFieldOfTypeArray(Iterator<Map.Entry<String, JsonNode>> i, Map.Entry<String, JsonNode> fieldEntry, Object o2) {
     if (!fieldEntry.getKey().equals("fields")
     && !fieldEntry.getKey().equals("fieldsConfig")
     && !fieldEntry.getKey().equals("labelingInfo")) {
     i.remove();
     }
    
     if (service != null && fieldEntry.getKey().equals("fullLayers") && ((List) o2).isEmpty()) {
     for (int k = 0; k < service.getFullLayers().size(); k++) {
     ((List) o2).add(new WFS2GSFSLayer());
     }
     }
     }*/
}
