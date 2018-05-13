/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xsf.core.web;

import de.ii.xsf.core.web.amdatu.DefaultPageParser;
import de.ii.xsf.core.web.amdatu.DefaultPages;
import de.ii.xsf.core.web.amdatu.InvalidEntryException;
import de.ii.xsf.core.web.amdatu.ResourceEntry;
import de.ii.xsf.core.web.amdatu.ResourceKeyParser;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.extender.Extender;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static de.ii.xsf.core.web.StaticResourceConstants.WEB_RESOURCE_DEFAULT_PAGE;
import static de.ii.xsf.core.web.StaticResourceConstants.WEB_RESOURCE_KEY;

/**
 *
 * @author zahnen
 */
@Component
@Provides(specifications=StaticResourceRegistry.class)
@Instantiate
@Extender(
    onArrival="onBundleArrival",
    onDeparture="onBundleDeparture",
    extension=WEB_RESOURCE_KEY)
public class StaticResourceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceServlet.class);
    
    @Requires
    HttpService httpService;
    @Context
    BundleContext bc;
    
    private final Map<Long, List<ServiceRegistration>> servlets;

    public StaticResourceRegistry() {
        this.servlets = new ConcurrentHashMap<>();
    }
    
    private synchronized void onBundleArrival(Bundle bundle, String header) {
        try {
            Map<String, ResourceEntry> entryMap = ResourceKeyParser.getEntries(header);
            DefaultPages defaultPages = DefaultPageParser.parseDefaultPages((String)bundle.getHeaders().get(WEB_RESOURCE_DEFAULT_PAGE));
            //servlets.put(bundle.getBundleId(), entryMap);
            List<ServiceRegistration> srs = new ArrayList<>();
            
            for (ResourceEntry entry : entryMap.values()) {
                LOGGER.debug("web static: {} {} {}", entry.getPaths().get(0), entry.getAlias(), defaultPages.getDefaultPageFor(entry.getPaths().get(0)));
                HttpServlet s = new StaticResourceServlet(entry.getPaths().get(0), entry.getAlias(), Charset.forName("UTF-8"), bundle, defaultPages);
                //servlets.put(bundle.getBundleId(), entryMap);
                //httpService.registerServlet(entry.getAlias(), s, null, null);
                
                Hashtable props = new Hashtable();
                props.put("alias", entry.getAlias());
                ServiceRegistration sr = bc.registerService(Servlet.class.getName(), s, props);
                
                srs.add(sr);
            }
            servlets.put(bundle.getBundleId(), srs);
            
        } catch (InvalidEntryException ex) {
            //LOGGER.info("STATIC", ex);
        }
    }

    private synchronized void onBundleDeparture(Bundle bundle) {
         if (servlets.containsKey(bundle.getBundleId())) {
             for (ServiceRegistration sr: servlets.get(bundle.getBundleId())) {
                sr.unregister();
             }
         }
    }
}
