package org.onap.ccsdk.dashboard.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.onap.ccsdk.dashboard.model.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration.EndpointCheck;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsulRestClientImpl extends RestClientBase implements ConsulClient {
	
	private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ConsulRestClientImpl.class);
	private final String baseUrl;
	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String API_VER = "v1";
	private static final String CATALOG = "catalog";
	private static final String SERVICES = "services";
	private static final String HEALTH = "health";
	private static final String CHECKS = "checks";
	private static final String HEALTH_SERVICES = "healthservices";
	
	public ConsulRestClientImpl(String webapiUrl, String user, String pass) {
		super();
		if (webapiUrl == null)
			throw new IllegalArgumentException("Null URL not permitted");

		URL url = null;
		String urlScheme = "http";
		try {
			url = new URL(webapiUrl);
			baseUrl = url.toExternalForm();
		} catch (MalformedURLException ex) {
			throw new RuntimeException("Failed to parse URL", ex);
		}
		
		urlScheme = webapiUrl.split(":")[0];
		createRestTemplate(url, user, pass, urlScheme);
	}
	
	@Override
	public List<ConsulServiceHealth> getServiceHealth(String dc, String srvc) {	
		String url = buildUrl(new String[] { baseUrl, API_VER, HEALTH, CHECKS, srvc}, new String[] {"dc", dc});
		logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealth: url {}", url);
		ResponseEntity<List<ConsulServiceHealth>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ConsulServiceHealth>>() {
				});
		return response.getBody();
	}

	@Override
	public List<ConsulServiceInfo> getServices(String dc) {
		String url = buildUrl(new String[] { baseUrl, API_VER, CATALOG, SERVICES}, new String[] {"dc", dc});
		logger.debug(EELFLoggerDelegate.debugLogger, "getServices: url {}", url);
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});
		Map<String, Object> serviceInfo = response.getBody();
		List<ConsulServiceInfo> list = new ArrayList<>();
		for (Map.Entry<String, Object> entry : serviceInfo.entrySet()) {
			// Be defensive
			List<String> addrs = null;
			if (entry.getValue() instanceof List<?>)
				addrs = (List<String>) entry.getValue();
			else
				addrs = new ArrayList<>();
			list.add(new ConsulServiceInfo(entry.getKey(), addrs));
		}
		return list;
	}
	
	@Override
	public List<ConsulNodeInfo> getNodes(String dc) {
		String url = buildUrl(new String[] { baseUrl, API_VER, CATALOG, "nodes" }, new String[] {"dc", dc});
		logger.debug(EELFLoggerDelegate.debugLogger, "getNodesHealth: url {}", url);
		ResponseEntity<List<ConsulNodeInfo>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ConsulNodeInfo>>() {
				});
		return response.getBody();
	}
	
	@Override
	public List<ConsulServiceHealth> getNodeServicesHealth(String dc, String nodeId) {
		String url = buildUrl(new String[] { baseUrl, API_VER, HEALTH, "node", nodeId }, new String[] {"dc", dc});
		logger.debug(EELFLoggerDelegate.debugLogger, "getNodeServicesHealth: url {}", url);
		ResponseEntity<List<ConsulServiceHealth>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ConsulServiceHealth>>() {
				});
		return response.getBody();
	}

	@Override
	public List<ConsulDatacenter> getDatacenters() {
		String url = buildUrl(new String[] { baseUrl, API_VER, CATALOG, "datacenters" }, null);
		logger.debug(EELFLoggerDelegate.debugLogger, "getDatacentersHealth: url {}", url);
		ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<String>>() {
				});
		List<String> list = response.getBody();
		List<ConsulDatacenter> result = new ArrayList<>();
		for (String dc : list)
			result.add(new ConsulDatacenter(dc));
		return result;
	}

	@Override
	public String registerService(ConsulHealthServiceRegistration registration) {
		String url = buildUrl(new String[] { baseUrl, API_VER, "/agent/service/register" }, null);
		logger.debug(EELFLoggerDelegate.debugLogger, "registerService: url {}", url);
		String resultStr = "";
		JSONObject outputJson = new JSONObject();
		JSONObject checkObject = new JSONObject();
		List<EndpointCheck> checks = registration.services.get(0).checks;
		String service_name = registration.services.get(0).name;
		String service_port = registration.services.get(0).port;
		String service_address = registration.services.get(0).address;
		List<String> tags = registration.services.get(0).tags;

		outputJson.put("Name", service_name);
		outputJson.put("ID", service_name);
		outputJson.put("Port", Integer.parseInt(service_port));
		outputJson.put("Address", service_address);
		outputJson.put("Tags", tags);

		if (checks.size() == 1) {
			checkObject.put("HTTP", checks.get(0).endpoint);
			checkObject.put("Interval", checks.get(0).interval);
			if (!checks.get(0).description.isEmpty())
				checkObject.put("Notes", checks.get(0).description);
			checkObject.put("ServiceID", service_name);
			outputJson.put("Check", checkObject);
		} else {
			JSONArray checks_new = new JSONArray();
			for (EndpointCheck check : checks) {
				checkObject.put("HTTP", check.endpoint);
				checkObject.put("Interval", check.endpoint);
				if (!check.description.isEmpty())
					checkObject.put("Notes", check.description);
				checkObject.put("ServiceID", service_name);
				checks_new.put(checkObject);
			}
			outputJson.put("Checks", checks_new);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(outputJson.toString(), headers);
		ResponseEntity<JSONObject> result = 
		restTemplate.exchange(url, HttpMethod.PUT, entity, 
				new ParameterizedTypeReference<JSONObject>() {});
		try {
			resultStr = objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
	
		} finally {
		}
		return resultStr;

	}

	@Override
	public int deregisterService(String serviceName) {
		String url = buildUrl(new String[] { baseUrl, API_VER, "/agent/service/deregister", serviceName}, null);
		logger.debug(EELFLoggerDelegate.debugLogger, "deregisterService: url {}", url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<JSONObject> result = 
		restTemplate.exchange(url, HttpMethod.PUT, entity, 
				new ParameterizedTypeReference<JSONObject>() {});
		return result.getStatusCode().value();
	}
}
