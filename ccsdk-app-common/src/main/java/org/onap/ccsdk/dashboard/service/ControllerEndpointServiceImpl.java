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

package org.onap.ccsdk.dashboard.service;

import org.onap.ccsdk.dashboard.domain.ControllerEndpoint;
import org.onap.ccsdk.dashboard.domain.EcdComponent;

import java.util.List;

import org.onap.portalsdk.core.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Complete controller endpoint information is in properties. The database just
 * stores the user's selection. Users are not expected to enter credentials so
 * this hybrid solution keeps credentials out of the database.
 */
@Service("controllerEndpointService")
@Transactional
public class ControllerEndpointServiceImpl implements ControllerEndpointService {

    @Autowired
    private DataAccessService dataAccessService;

    /**
     * @return Data access service
     */
    public DataAccessService getDataAccessService() {
        return dataAccessService;
    }

    /**
     * @param dataAccessService Data access service
     */
    public void setDataAccessService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openecomp.controller.dashboard.service.ControllerEndpointService#
     * getControllerEndpoint(java.lang.Integer)
     */
    @Override
    public ControllerEndpoint getControllerEndpointSelection(long userId) {
        return (ControllerEndpoint) getDataAccessService().getDomainObject(ControllerEndpoint.class, userId, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openecomp.controller.dashboard.service.ControllerEndpointService#
     * getComponents()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EcdComponent> getComponents() {
        return dataAccessService.executeNamedQuery("getAllComponents", null, null);
    }

    @Override
    public void insertComponent(EcdComponent component) {
        dataAccessService.saveDomainObject(component, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openecomp.controller.dashboard.service.ControllerEndpointService#
     * updateControllerEndpoint(org.openecomp.controller.dashboard.domain.
     * ControllerEndpoint)
     */
    @Override
    public void updateControllerEndpointSelection(ControllerEndpoint endpoint) {
        getDataAccessService().saveDomainObject(endpoint, null);
    }

    /*
     * // (non-Javadoc)
     * 
     * @see org.openecomp.controller.dashboard.service.ControllerEndpointService#
     * deleteControllerEndpoint(java.lang.Integer)
     */
    @Override
    public void deleteControllerEndpointSelection(long userId) {
        ControllerEndpoint dbEntry = (ControllerEndpoint) getDataAccessService()
                .getDomainObject(ControllerEndpoint.class, userId, null);
        if (dbEntry != null)
            getDataAccessService().deleteDomainObject(dbEntry, null);
    }

}
