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
package de.ii.xsf.core.api.organization;

/**
 *
 * @author fischer
 */
public class OrganizationDecider {
    public static String MULTI_TENANCY_ROOT = "_multi_tenancy_root_";
    
    public static boolean isRootOrg(String orgid) {
        return orgid == null || orgid.equals(MULTI_TENANCY_ROOT);
    }

    public static boolean isMultiTenancyRootOrg(String orgid) {
        return MULTI_TENANCY_ROOT.equals(orgid);
    }
}
