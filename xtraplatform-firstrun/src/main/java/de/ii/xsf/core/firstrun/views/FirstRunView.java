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
package de.ii.xsf.core.firstrun.views;

import de.ii.xsf.core.api.firstrun.FirstRunPage;
import io.dropwizard.views.View;

/**
 *
 * @author fischer
 */
public class FirstRunView extends View {

    private FirstRunPage page;
    private String pageid;
    
    public FirstRunView() {
        super("firstrun.mustache");
    }

    public FirstRunPage getPage() {
        return page;
    }

    public void setPage(FirstRunPage page) {
        this.page = page;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }
    
}
