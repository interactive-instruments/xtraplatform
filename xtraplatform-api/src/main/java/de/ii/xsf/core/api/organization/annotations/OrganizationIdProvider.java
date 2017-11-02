/**
 * Copyright 2017 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xsf.core.api.organization.annotations;

import de.ii.xsf.core.api.permission.Organization;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

/**
 *
 * @author zahnen
 */

// TODO: used in security-oauth2/src/main/java/org/surfnet/oaaas/resource/OAuth2TokenResource.java

/*@Component
@Provides
@Instantiate

@Provider*/
public class OrganizationIdProvider /*extends PerRequestTypeInjectableProvider<OrganizationId, String>*/ {

    /*@Context
    HttpServletRequest request;

    public OrganizationIdProvider() {
        super(String.class);
    }
    
    
    @Override
    public Injectable<String> getInjectable(ComponentContext ic, OrganizationId a) {
        return new AbstractHttpContextInjectable<String>(){

            @Override
            public String getValue(HttpContext c) {
                return (String) request.getAttribute(Organization.class.getName());
            }

        };
    }*/
}
