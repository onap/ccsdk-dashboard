/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {

    public String title;
    public String href;
    public String rel;
    public String uri;
    public UriBuilder uriBuilder;
    public Collection<String> rels;
    public Map<String, String> params;
    public String type;

    @JsonCreator
    public Link(@JsonProperty("title") String title, @JsonProperty("href") String href,
        @JsonProperty("rel") String rel, @JsonProperty("uri") String uri,
        @JsonProperty("uriBuilder") UriBuilder uriBuilder,
        @JsonProperty("rels") Collection<String> rels,
        @JsonProperty("params") Map<String, String> params, @JsonProperty("type") String type) {
        this.title = title;
        this.href = href;
        this.rel = rel;
        this.uri = uri;
        this.uriBuilder = uriBuilder;
        this.rels = rels;
        this.params = params;
        this.type = type;
    }
}
