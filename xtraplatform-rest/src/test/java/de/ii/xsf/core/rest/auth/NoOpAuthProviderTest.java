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
package de.ii.xsf.core.rest.auth;

import de.ii.xsf.core.api.permission.Auth;
import de.ii.xsf.core.api.permission.AuthenticatedUser;
import de.ii.xsf.core.api.permission.Organization;
import de.ii.xsf.core.api.permission.Role;
import javax.servlet.http.HttpServletRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

/**
 *
 * @author zahnen
 */
public class NoOpAuthProviderTest {
    private static final String TEST_ORG = "org1";
    private SoftAssert ass = new SoftAssert();

    @InjectMocks
    private NoOpAuthProvider provider;
 
    @Mock
    private HttpServletRequest request;

    @org.testng.annotations.BeforeClass(groups = {"default"})
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @org.testng.annotations.Test(groups = {"default"})
    public void testInjectionWithOrganization() {
        // return organization attribute from HttpServletRequest
        Mockito.when(request.getAttribute(Organization.class.getName())).thenReturn(TEST_ORG);
        
        AuthenticatedUser u = provider.getInjectable(null, null, null).getValue(null);
        
        Assert.assertEquals(u.getOrgId(), TEST_ORG);
        Assert.assertEquals(u.getRole(), Role.NONE);
        
    }
    
    @org.testng.annotations.Test(groups = {"default"})
    public void testInjectionWithoutOrganization() {
        // return null, happens when attribute does not exist in HttpServletRequest
        Mockito.when(request.getAttribute(Organization.class.getName())).thenReturn(null);
        
        AuthenticatedUser u = provider.getInjectable(null, null, null).getValue(null);
        
        Assert.assertEquals(u.getOrgId(), null);
        Assert.assertEquals(u.getRole(), Role.NONE);
                
    }
    
    @org.testng.annotations.Test(groups = {"default"})
    public void testInjectionWithRequired() {
        // return null, happens when attribute does not exist in HttpServletRequest
        Mockito.when(request.getAttribute(Organization.class.getName())).thenReturn(null);
        
        // set required to true
        Auth a = mock(Auth.class);
        when(a.required()).thenReturn(true);

        AuthenticatedUser u = provider.getInjectable(null, a, null).getValue(null);
        
        Assert.assertEquals(u.getOrgId(), null);
        Assert.assertEquals(u.getRole(), Role.NONE);
    }
    
    @org.testng.annotations.AfterClass(groups = {"default"})
    public void cleanup() {
        
    }

}
