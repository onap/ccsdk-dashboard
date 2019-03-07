package org.onap.ccsdk.dashboard.rest;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.onap.ccsdk.dashboard.model.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsulMockClientImpl implements ConsulClient {

	private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ConsulMockClientImpl.class);

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
	public List<ConsulServiceInfo> getServices(String datacenter) {
		
		return null;
	}

	@Override
	public List<ConsulServiceHealth> getServiceHealth(String datacenter, String srvcName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsulNodeInfo> getNodes(String datacenter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsulServiceHealth> getNodeServicesHealth(String datacenter, String nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsulDatacenter> getDatacenters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerService(ConsulHealthServiceRegistration registration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deregisterService(String serviceName) {
		// TODO Auto-generated method stub
		return 0;
	}

}
