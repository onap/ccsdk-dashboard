/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *******************************************************************************/
package org.onap.ccsdk.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PaginatedResultsRetrievedEvent<T> extends ApplicationEvent {

    /**
     * 
     */
    private static final long serialVersionUID = 1513813910132068908L;
    private Class clazz;
    private ServletUriComponentsBuilder uriBuilder;
    private HttpServletResponse response;
    private int page;
    private int maxPages;
    private int pageSize;
    
    public PaginatedResultsRetrievedEvent(Class clazz,
            ServletUriComponentsBuilder uriBuilder, HttpServletResponse response,
            int page, int maxPages, int pageSize) {
        super(clazz);
        this.clazz = clazz;
        this.uriBuilder = uriBuilder;
        this.response = response;
        this.page = page;
        this.maxPages = maxPages;
        this.pageSize = pageSize;
    }

    public Class getClazz() {
        return clazz;
    }

    public ServletUriComponentsBuilder getUriBuilder() {
        return uriBuilder;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getMaxPages() {
        return maxPages;
    }

}
