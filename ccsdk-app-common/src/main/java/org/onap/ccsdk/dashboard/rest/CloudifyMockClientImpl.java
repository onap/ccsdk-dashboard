package org.onap.ccsdk.dashboard.rest;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import org.onap.ccsdk.dashboard.model.CloudifyBlueprintContent;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintUpload;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateResponse;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.CloudifySecret;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides mock implementations that return contents of files on the classpath.
 */
public class CloudifyMockClientImpl implements CloudifyClient {

	private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(CloudifyMockClientImpl.class);

	/**
	 * For mock outputs
	 */
	private final ObjectMapper objectMapper = new ObjectMapper();

	private String getMockDataContent(final String path) {
		String result = null;
		try {
			InputStream is = getClass().getResourceAsStream(path);
			if (is == null)
				throw new Exception("Failed to find resource at path " + path);
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
		ECTransportModel result = null;
		String json = getMockDataContent(path);
		try {
			result = (ECTransportModel) objectMapper.readValue(json, modelClass);
		} catch (Exception ex) {
			logger.error("getMockData failed", ex);
			throw new RuntimeException(ex);
		}
		return result;
	}

	@Override
	public CloudifyTenantList getTenants() {
		return (CloudifyTenantList) getMockData(CloudifyTenantList.class, "/tenantsList.json");
	}
	
	@Override
	public CloudifyDeployedTenantList getTenantInfoFromDeploy(String tenant) {
		return (CloudifyDeployedTenantList) getMockData(CloudifyDeployedTenantList.class, "/serviceTenantList.json");

	}
	
	@Override
	public CloudifyNodeInstanceIdList getNodeInstanceId(String deploymentId, String nodeId, String tenant) {
		return null;
	}

	@Override
	public CloudifyNodeInstanceIdList getNodeInstanceId(String deploymentId, String tenant) {
		return null;
	}
	
	@Override
	public CloudifyNodeInstanceList getNodeInstanceVersion(String bpId, String tenant) {
		return null;
	}
	@Override
	public CloudifyNodeInstanceList getNodeInstanceVersion(String deploymentId, String nodeId, String tenant) {
		return null;
	}
	
	@Override
	public CloudifyDeploymentUpdateResponse updateDeployment(CloudifyDeploymentUpdateRequest execution) {
		return null;
	}
	@Override
	public CloudifyEventList getEventlogs(String executionId, String tenant) {
		return null;
	}

	public CloudifyDeploymentList createDeployment(CloudifyDeploymentRequest deployment) {
		logger.debug(EELFLoggerDelegate.debugLogger, "createDeployment: {}", deployment.toString());
		return new CloudifyDeploymentList(null, null);
	}

	@Override
	public CloudifyExecutionList getExecutions(final String deploymentId, final String tenant) {
		return (CloudifyExecutionList) getMockData(CloudifyExecutionList.class, "/listExecutionForDeploymentID.json");
	}

	@Override
	public CloudifyExecutionList getExecutionsSummary(final String deploymentId, final String tenant) {
		return (CloudifyExecutionList) getMockData(CloudifyExecutionList.class, "/listExecutionForDeploymentID.json");
	}
	
	@Override
	public CloudifyExecutionList getExecution(String executionId, String deploymentId) {
		return (CloudifyExecutionList) getMockData(CloudifyExecutionList.class, "/listExecutionForDeploymentID.json");
	}

	@Override
	public CloudifyExecution startExecution(CloudifyExecutionRequest execution) {
		logger.debug(EELFLoggerDelegate.debugLogger, "startExecution: {}", execution.toString());
		return new CloudifyExecution(null, null, null, null, null, null, null, null, null, null,null, null);
	}

	@Override
	public CloudifyExecution cancelExecution(final String executionId, Map<String, String> parameters, final String tenant) {
		return null;
	}

	public int deleteDeployment(String id, boolean ignoreLiveNodes) {
		return 0;
	}
	public CloudifyDeploymentList getDeployment(String id, String tenant) {
		return null;
	}
	public CloudifyDeploymentList getDeployment(String id) {
		return null;
	}
	public CloudifyDeploymentList getDeployments() {
		return null;
	}
	public int deleteBlueprint(String id) {
		return 0;
	}
	public CloudifyBlueprintList uploadBlueprint(CloudifyBlueprintUpload blueprint) {
		return null;
	}
	public CloudifyBlueprintContent viewBlueprint(String id) {
		return null;
	}
	public CloudifyBlueprintList getBlueprint(String id, String tenant) {
		return null;
	}
	public CloudifyBlueprintList getBlueprints() {
		return null;
	}
	
	/**
	 * Get the a cloudify secret
	 * 
	 * @return CloudifySecret
	 */
	@Override
	public CloudifySecret getSecret(String secretName, String tenant) {
		return null;
	}
	
	/**
	 * Simple test
	 * 
	 * @param args
	 *            blueprint ID
	 * @throws Exception
	 *             On any failure
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Testing paths and parsing mock data");
	}

	@Override
	public CloudifyDeploymentList getDeploymentInputs(String id, String tenant) {
		// TODO Auto-generated method stub
		return null;
	}

}
