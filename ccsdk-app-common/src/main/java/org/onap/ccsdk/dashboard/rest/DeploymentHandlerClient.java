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
 *******************************************************************************/

package org.onap.ccsdk.dashboard.rest;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentLink;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;

public interface DeploymentHandlerClient {

    /**
     * Run deployment handler service health check
     * 
     */
    public String checkHealth();

    /**
     * Gets a list of all service deployments known to the orchestrator.
     * 
     * @return Stream<String>
     */
    public Stream<DeploymentLink> getDeployments();

    /**
     * Gets a list of all service deployments known to the orchestrator, restricted
     * to a single service type.
     * 
     * @param serviceTypeId Service type identifier for the type whose deployments
     * are to be listed.
     * 
     * @return Stream<String>
     */
    public Stream<DeploymentLink> getDeployments(String serviceTypeId);

    /**
     * Request deployment of a DCAE Service.
     * 
     * @param deploymentId Unique deployment identifier assigned by the API
     * client.
     * 
     * @param deploymentRequest Deployment request object that contains the
     * necessary fields for service deployment.
     * 
     * @return DeploymentResponse Response body for a PUT or DELETE to
     * /dcae-deployments/{deploymentId}
     *
     */
    public DeploymentResponse putDeployment(String deploymentId, String tenant,
        DeploymentRequest deploymentRequest, HttpServletRequest request) throws Exception;

    /**
     * For API use, Request deployment of a DCAE Service.
     * s
     * 
     * @param deploymentId Unique deployment identifier assigned by the API
     * client.
     * 
     * @param deploymentRequest Deployment request object that contains the
     * necessary fields for service deployment.
     * 
     * @return DeploymentResponse Response body for a PUT or DELETE to
     * /dcae-deployments/{deploymentId}
     *
     */
    public DeploymentResponse putDeployment(String deploymentId, String tenant,
        DeploymentRequest deploymentRequest) throws Exception;

    /**
     * Uninstall the DCAE service and remove all associated data from the
     * orchestrator.
     * 
     * @param deploymentId Unique deployment identifier assigned by the API client.
     * 
     */
    public void deleteDeployment(String deploymentId, String tenant, HttpServletRequest request)
        throws Exception;

    /**
     * For API use, Uninstall the DCAE service and remove all associated data from the
     * orchestrator.
     * 
     * @param deploymentId Unique deployment identifier assigned by the API client.
     * 
     */
    public void deleteDeployment(String deploymentId, String tenant) throws Exception;
}
