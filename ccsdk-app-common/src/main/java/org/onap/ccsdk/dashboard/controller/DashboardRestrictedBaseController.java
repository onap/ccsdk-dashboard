/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
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
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.ccsdk.dashboard.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.controller.RestrictedBaseController;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * This base class provides utility methods to child controllers.
 */
public class DashboardRestrictedBaseController extends RestrictedBaseController {

    /**
     * Application name
     */
    protected static final String APP_NAME = "ecd-app";

    /**
     * EELF-approved format
     */
    protected static final DateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    /**
     * Query parameter for desired page number
     */
    protected static final String PAGE_NUM_QUERY_PARAM = "pageNum";

    /**
     * Query parameter for desired items per page
     */
    protected static final String PAGE_SIZE_QUERY_PARAM = "viewPerPage";

    /**
     * For general use in these methods and subclasses
     */
    protected final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Application properties - NOT available to constructor.
     */
    @Autowired
    protected DashboardProperties appProperties;

    /**
     * For getting selected controller
     */
    @Autowired
    private ControllerEndpointService controllerEndpointService;

    /**
     * Hello Spring, here's your no-arg constructor.
     */
    public DashboardRestrictedBaseController() {
        // Do not serialize null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Register Jdk8Module() for Stream and Optional types
        objectMapper.registerModule(new Jdk8Module());
    }

    /**
     * Access method for subclasses.
     * 
     * @return DbcappProperties object that was autowired by Spring.
     */
    protected DashboardProperties getAppProperties() {
        return appProperties;
    }

    /**
     * Gets the requested page number from a query parameter in the
     * HttpServletRequest. Defaults to 1, which is useful to allow manual testing of
     * endpoints without supplying those pesky parameters.
     * 
     * @param request HttpServletRequest
     * @return Value of query parameter {@link #PAGE_NUM_QUERY_PARAM}; 1 if not
     *         found.
     */
    protected int getRequestPageNumber(HttpServletRequest request) {
        int pageNum = 1;
        String param = request.getParameter(PAGE_NUM_QUERY_PARAM);
        if (param != null)
            pageNum = Integer.parseInt(param);
        return pageNum;
    }

    /**
     * Gets the requested page size from a query parameter in the
     * HttpServletRequest. Defaults to 50, which is useful to allow manual testing
     * of endpoints without supplying those pesky parameters.
     * 
     * @param request HttpServletRequest
     * @return Value of query parameter {@link #PAGE_SIZE_QUERY_PARAM}; 50 if not
     *         found.
     */
    protected int getRequestPageSize(HttpServletRequest request) {
        int pageSize = 50;
        String param = request.getParameter(PAGE_SIZE_QUERY_PARAM);
        if (param != null)
            pageSize = Integer.parseInt(param);
        return pageSize;
    }

    /**
     * Gets the items for the specified page from the specified list.
     * 
     * @param pageNum  Page number requested by user, indexed from 1
     * @param pageSize Number of items per page
     * @param itemList List of items to adjust
     * @return List of items; empty list if from==to
     */
    @SuppressWarnings("rawtypes")
    protected static List getPageOfList(final int pageNum, final int pageSize, final List itemList) {
        int firstIndexOnThisPage = pageSize * (pageNum - 1);
        int firstIndexOnNextPage = pageSize * pageNum;
        int fromIndex = firstIndexOnThisPage < itemList.size() ? firstIndexOnThisPage : itemList.size();
        int toIndex = firstIndexOnNextPage < itemList.size() ? firstIndexOnNextPage : itemList.size();
        return itemList.subList(fromIndex, toIndex);
    }
}
