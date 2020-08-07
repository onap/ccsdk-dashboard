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

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PagingEventListener implements
        ApplicationListener<PaginatedResultsRetrievedEvent> {

  
    public static final String REL_COLLECTION = "collection"; 
    public static final String REL_NEXT = "next"; 
    public static final String REL_PREV = "prev"; 
    public static final String REL_FIRST = "first"; 
    public static final String REL_LAST = "last"; 
    private static final String PAGE = "page";
    
    @Override
    public void onApplicationEvent(PaginatedResultsRetrievedEvent event) {
        addLinkHeaderOnPagedResourceRetrieval(event.getUriBuilder(),
                event.getResponse(), event.getClazz(), event.getPage(),
                event.getMaxPages(), event.getPageSize());
    }

    void addLinkHeaderOnPagedResourceRetrieval(ServletUriComponentsBuilder uriBuilder,
            HttpServletResponse response, Class clazz, int page,
            int totalPages, int size) {
       
        StringBuilder linkHeader = new StringBuilder();
        if (hasNextPage(page, totalPages)) {
            String uriNextPage = constructNextPageUri(uriBuilder, page, size);
            linkHeader.append(createLinkHeader(uriNextPage, "next"));
        }
        if (hasPreviousPage(page)) {
            String uriPrevPage = constructPrevPageUri(uriBuilder, page, size);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriPrevPage, "prev"));
        }
        if (hasFirstPage(page)) {
            String uriFirstPage = constructFirstPageUri(uriBuilder, size);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriFirstPage, "first"));
        }
        if (hasLastPage(page, totalPages)) {
            String uriLastPage = constructLastPageUri(uriBuilder, totalPages,
                    size);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriLastPage, "last"));
        }
        response.addHeader("Link", linkHeader.toString());

    }

    final String constructNextPageUri(final UriComponentsBuilder uriBuilder,
            final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page + 1)
                .replaceQueryParam("size", size).build().toUriString();
    }

    final String constructPrevPageUri(final UriComponentsBuilder uriBuilder,
            final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page - 1)
                .replaceQueryParam("size", size).build().toUriString();
    }

    final String constructFirstPageUri(final UriComponentsBuilder uriBuilder,
            final int size) {
        return uriBuilder.replaceQueryParam(PAGE, 1)
                .replaceQueryParam("size", size).build().toUriString();
    }

    final String constructLastPageUri(final UriComponentsBuilder uriBuilder,
            final int totalPages, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, totalPages)
                .replaceQueryParam("size", size).build().toUriString();
    }

    final boolean hasNextPage(final int page, final int totalPages) {
        return page < totalPages - 1;
    }

    final boolean hasPreviousPage(final int page) {
        return page > 1;
    }

    final boolean hasFirstPage(final int page) {
        return hasPreviousPage(page);
    }

    final boolean hasLastPage(final int page, final int totalPages) {
        return totalPages > 1 && hasNextPage(page, totalPages);
    }

    final void appendCommaIfNecessary(final StringBuilder linkHeader) {
        if (linkHeader.length() > 0) {
            linkHeader.append(", ");
        }
    }

    // template

    protected void plural(final UriComponentsBuilder uriBuilder,
            final Class clazz) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase() + "s";
                //uriMapper.getUriBase(clazz);
        uriBuilder.path("/" + resourceName);
    }
    
    public static String createLinkHeader(final String uri, final String rel) { 
        return "<" + uri + ">; rel=\"" + rel + "\""; 
    } 
}
