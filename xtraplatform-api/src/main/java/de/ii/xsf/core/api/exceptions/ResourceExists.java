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
package de.ii.xsf.core.api.exceptions;

import javax.ws.rs.core.Response;

/**
 *
 * @author fischer
 */
public class ResourceExists extends XtraserverFrameworkException {

    public ResourceExists(Object m, Object... args) {
        super(m, args);
        this.init();
    }
    
    public ResourceExists() {
        this.init();
    }
    
    private void init() {
        this.code = Response.Status.BAD_REQUEST;
        this.htmlCode = this.code;             
        this.stdmsg = "ResourceExists";
    }
    
    public ResourceExists(String msg) {
        this();
        this.msg = msg;
    }
    
    public ResourceExists(String msg, String callback) {
        this();
        this.msg = msg;
        this.callback = callback;
    }
}