/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
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
package org.onap.ccsdk.dashboard.rest;

import java.io.InputStream;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.onap.ccsdk.dashboard.exception.DashboardControllerException;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintContent;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintUpload;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentRequest;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealthHistory;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides mock implementations that return contents of files on the classpath.
 */
public class ControllerRestClientMockImpl implements IControllerRestClient {

	private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ControllerRestClientMockImpl.class);

	/**
	 * For mock outputs
	 */
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * No-arg constructor
	 */
	public ControllerRestClientMockImpl() {
	}

	private String getMockDataContent(final String path) {
		String result = null;
		try {
			InputStream is = getClass().getResourceAsStream(path);
			if (is == null)
				throw new DashboardControllerException("Failed to find resource at path " + path);
			Scanner scanner = new Scanner(is, "UTF-8");
			result = scanner.useDelimiter("\\A").next();
			scanner.close();
			is.close();
		} catch (Exception ex) {
			logger.error("getMockDataContent failed", ex);
			throw new RuntimeException(ex);
		}
		return result;
	}

	/**
	 * Creates an input stream using the specified path and requests the mapper
	 * create an object of the specified type.
	 * 
	 * @param modelClass
	 *            Model class
	 * @param path
	 *            Path to classpath resource
	 * @return Instance of modelClass
	 */
	private ECTransportModel getMockData(final Class<? extends ECTransportModel> modelClass, final String path) {
        ECTransportModel result;
		String json = getMockDataContent(path);
		try {
            result = objectMapper.readValue(json, modelClass);
		} catch (Exception ex) {
			logger.error("getMockData failed", ex);
			throw new RuntimeException(ex);
		}
		return result;
	}

	@Override
	public CloudifyBlueprintList getBlueprints() {
		return (CloudifyBlueprintList) getMockData(CloudifyBlueprintList.class, "/blueprintList.json");
	}

	@Override
	public CloudifyBlueprintList getBlueprint(final String id) {
		return (CloudifyBlueprintList) getMockData(CloudifyBlueprintList.class, "/blueprintByID.json");
	}

	@Override
	public CloudifyBlueprintContent viewBlueprint(final String id) {
		String yaml = getMockDataContent("/blueprintContent.yaml");
        return new CloudifyBlueprintContent(id, yaml);
	}

	@Override
	public CloudifyBlueprintList uploadBlueprint(CloudifyBlueprintUpload blueprint) {
		logger.debug(EELFLoggerDelegate.debugLogger, "uploadBlueprint: {}", blueprint.toString());
		return new CloudifyBlueprintList(null, null);
	}

	@Override
	public int deleteBlueprint(final String id) {
		logger.debug(EELFLoggerDelegate.debugLogger, "deleteBlueprint: {}", id);
		return 204;
	}

	@Override
	public CloudifyDeploymentList getDeployments() {
		return (CloudifyDeploymentList) getMockData(CloudifyDeploymentList.class, "/deploymentList.json");
	}

	@Override
	public CloudifyDeploymentList getDeployment(final String id) {
		return (CloudifyDeploymentList) getMockData(CloudifyDeploymentList.class, "/deploymentByID.json");
	}

	public CloudifyDeploymentList createDeployment(CloudifyDeploymentRequest deployment) {
		logger.debug(EELFLoggerDelegate.debugLogger, "createDeployment: {}", deployment.toString());
		return new CloudifyDeploymentList(null, null);
	}

	@Override
	public int deleteDeployment(final String id, boolean ignoreLiveNodes) {
		logger.debug(EELFLoggerDelegate.debugLogger, "deleteDeployment: id {}, ignoreLiveNodes", id, ignoreLiveNodes);
		return 204;
	}

	@Override
	public CloudifyExecutionList getExecutions(final String deploymentId) {
		return (CloudifyExecutionList) getMockData(CloudifyExecutionList.class, "/listExecutionForDeploymentID.json");
	}

	@Override
	public CloudifyExecutionList getExecution(String executionId, String deploymentId) {
		return (CloudifyExecutionList) getMockData(CloudifyExecutionList.class, "/listExecutionForDeploymentID.json");
	}

	@Override
	public CloudifyExecution startExecution(CloudifyExecutionRequest execution) {
		logger.debug(EELFLoggerDelegate.debugLogger, "startExecution: {}", execution.toString());
		return new CloudifyExecution(null, null, null, null, null, null, null, null, null);
	}

	@Override
	public int cancelExecution(String executionId, String deploymentId, String action) {
		logger.debug(EELFLoggerDelegate.debugLogger, "deleteExecution: executionId {}, deploymentId {}, action {}",
				executionId, deploymentId, action);
		return 204;
	}

	@Override
	public URI registerService(ConsulHealthServiceRegistration registration) {
		logger.debug(EELFLoggerDelegate.debugLogger, "registerService: {}", registration);
		return null;
	}

	@Override
	public int deregisterService(String serviceName) {
		logger.debug(EELFLoggerDelegate.debugLogger, "deregisterService: {}", serviceName);
		return 200;
	}

	@Override
	public List<ConsulServiceHealth> getServiceHealth(String serviceName) {
		logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealth: serviceName={}", serviceName);
		String json = getMockDataContent("/serviceHealth.json");
		TypeReference<List<ConsulServiceHealth>> typeRef = new TypeReference<List<ConsulServiceHealth>>() {
		};
		List<ConsulServiceHealth> result = null;
		try {
			result = objectMapper.readValue(json, typeRef);
		} catch (Exception ex) {
			logger.error(EELFLoggerDelegate.errorLogger, "getServiceHealth failed", ex);
		}
		return result;
	}

	@Override
	public List<ConsulServiceHealthHistory> getServiceHealthHistory(String serviceName, Instant start, Instant end) {
		logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealthHistory: serviceName={}", serviceName);
		String json = getMockDataContent("/serviceHealthHistory.json");
		TypeReference<List<ConsulServiceHealthHistory>> typeRef = new TypeReference<List<ConsulServiceHealthHistory>>() {
		};
		List<ConsulServiceHealthHistory> result = null;
		try {
			result = objectMapper.readValue(json, typeRef);
		} catch (Exception ex) {
			logger.error(EELFLoggerDelegate.errorLogger, "getServiceHealthHistory failed", ex);
		}
		return result;
	}

	@Override
	public List<ConsulServiceHealth> getNodeServicesHealth(String nodeId) {
		logger.debug(EELFLoggerDelegate.debugLogger, "getNodeServicesHealth: nodeId={}", nodeId);
		String json = getMockDataContent("/nodeServicesHealth.json");
		TypeReference<List<ConsulServiceHealth>> typeRef = new TypeReference<List<ConsulServiceHealth>>() {
		};
		List<ConsulServiceHealth> result = null;
		try {
			result = objectMapper.readValue(json, typeRef);
		} catch (Exception ex) {
			logger.error(EELFLoggerDelegate.errorLogger, "getNodeServicesHealth failed", ex);
		}
		return result;
	}

	@Override
	public List<ConsulServiceInfo> getServices() {
		logger.debug(EELFLoggerDelegate.debugLogger, "getServices");
		String json = getMockDataContent("/services.json");
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		HashMap<String, Object> map = null;
		try {
			map = objectMapper.readValue(json, typeRef);
		} catch (Exception ex) {
			logger.error(EELFLoggerDelegate.errorLogger, "getNode failed", ex);
		}
		ArrayList<ConsulServiceInfo> result = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                final String service = entry.getKey();
                @SuppressWarnings("unchecked") final List<String> addrs = (List<String>) entry.getValue();
                result.add(new ConsulServiceInfo(service, addrs));
            }
        }
		return result;
	}

	@Override
	public List<ConsulNodeInfo> getNodes() {
		logger.debug(EELFLoggerDelegate.debugLogger, "getNodes");
		String json = getMockDataContent("/nodesHealth.json");
		TypeReference<List<ConsulNodeInfo>> typeRef = new TypeReference<List<ConsulNodeInfo>>() {
		};
		List<ConsulNodeInfo> result = null;
		try {
			result = objectMapper.readValue(json, typeRef);
		} catch (Exception ex) {
			logger.error(EELFLoggerDelegate.errorLogger, "getNode failed", ex);

		}
		return result;
	}

	@Override
	public List<ConsulDatacenter> getDatacenters() {
		logger.debug(EELFLoggerDelegate.debugLogger, "getDatacentersHealth");
		return null;
	}

	/**
	 * Simple test
	 * 
	 * @param args
	 *            blueprint ID
	 * @throws DashboardControllerException
	 *             On any failure
	 */
	public static void main(String[] args) throws DashboardControllerException {
        logger.info("Testing paths and parsing mock data");
		ControllerRestClientMockImpl client = new ControllerRestClientMockImpl();
		CloudifyBlueprintList list1 = client.getBlueprints();
		CloudifyBlueprintList list2 = client.getBlueprint("mock");
		CloudifyDeploymentList list3 = client.getDeployments();
		CloudifyDeploymentList list4 = client.getDeployment("mock");
		CloudifyExecutionList list5 = client.getExecutions("mock");
		List<ConsulServiceInfo> list6 = client.getServices();
		List<ConsulNodeInfo> list7 = client.getNodes();
		List<ConsulServiceHealth> list8 = client.getServiceHealth("mock");
		List<ConsulServiceHealthHistory> list9 = client.getServiceHealthHistory("mock", Instant.now(), Instant.now());
		if (list1 == null || list2 == null || list3 == null || list4 == null || list5 == null || list6 == null
				|| list7 == null || list8 == null || list9 == null)
			throw new DashboardControllerException("Failed");
        logger.info("Pass.");
	}

}
