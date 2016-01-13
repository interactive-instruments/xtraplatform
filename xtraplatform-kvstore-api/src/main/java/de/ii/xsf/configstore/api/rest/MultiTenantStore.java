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
import de.ii.xsf.core.api.organization.OrganizationDecider;
import de.ii.xsf.core.api.permission.AuthenticatedUser;

/**
 *
 * @author zahnen
 */
public class MultiTenantStore {

    public static <T extends ResourceStore> T forUser(T store, AuthenticatedUser user) {
        return forOrgId(store, user.getOrgId());
    }

    public static <T extends ResourceStore> T forOrgId(T store, String orgId) {
        if (!OrganizationDecider.isRootOrg(orgId)) {
            return (T) store.withParent(orgId);
        }

        return store;
    }

    private static boolean isRootOrg(String orgid) {
        if (orgid == null) {
            return true;
        }

        return false;
    }
}
