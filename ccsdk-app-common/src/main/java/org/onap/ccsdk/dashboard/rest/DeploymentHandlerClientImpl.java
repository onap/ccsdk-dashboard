package org.onap.ccsdk.dashboard.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentErrorResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentLink;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentsListResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class DeploymentHandlerClientImpl extends RestClientBase implements DeploymentHandlerClient {

	private final String baseUrl;
	//private final RestTemplate restTemplate;
	private static final String DEPLOYMENTS = "dcae-deployments";
	private static final String UPDATE_PATH = "dcae-deployment-update";
	
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	public DeploymentHandlerClientImpl(String webapiUrl) {
		this(webapiUrl, null, null);
	}
	
	/**
	 * Builds a restTemplate. If username and password are supplied, uses basic
	 * HTTP authentication.
	 * 
	 * @param webapiUrl
	 *            URL of the web endpoint
	 * @param user
	 *            user name; ignored if null
	 * @param pass
	 *            password
	 */
	public DeploymentHandlerClientImpl(String webapiUrl, String user, String pass) {
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
		// Do not serialize null values
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// Register Jdk8Module() for Stream and Optional types
		objectMapper.registerModule(new Jdk8Module());
	}
	
	public Stream<DeploymentLink> getDeployments() {
		String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, null);
		ResponseEntity<DeploymentsListResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<DeploymentsListResponse>() {
				});
		DeploymentsListResponse result = response.getBody();
		return result.getDeployments().stream();
	}

	@Override
	public Stream<DeploymentLink> getDeployments(String serviceTypeId) {
		String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, new String[] {"serviceTypeId", serviceTypeId});
		ResponseEntity<DeploymentsListResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<DeploymentsListResponse>() {
				});
		DeploymentsListResponse result = response.getBody();
		return result.getDeployments().stream();
	}

	@Override
	public DeploymentResponse putDeployment(String deploymentId, String tenant, DeploymentRequest deploymentRequest)
			throws BadRequestException, ServiceAlreadyExistsException, ServerErrorException, DownstreamException {
		String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS, deploymentId}, new String[] {"cfy_tenant_name",tenant});
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<DeploymentResponse> result = restTemplate.exchange(url,  HttpMethod.PUT, new HttpEntity<DeploymentRequest>(deploymentRequest, headers),
					new ParameterizedTypeReference<DeploymentResponse>() {
			});
			return result.getBody();
		} catch(HttpServerErrorException  | HttpClientErrorException e) { 
			DeploymentErrorResponse errBody = null;
			String errMsg = "";
			try {
				errBody = objectMapper.readValue(e.getResponseBodyAsString(), DeploymentErrorResponse.class);
			} catch (IOException e1) {
				errBody = null;
			}
			if (errBody != null) {
				errMsg = errBody.getMessage();
			}
			StringBuilder errDetails = new StringBuilder();
			errDetails.append(e.getMessage()).append("  ").append(errMsg);
			if (e.getStatusCode().value() == 400 || e.getStatusCode().value() == 415 || e.getStatusCode().value() == 404) {
				throw new BadRequestException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 409) {
				throw new ServiceAlreadyExistsException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 500) {
				throw new ServerErrorException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 502 || e.getStatusCode().value() == 504) {
				throw new DownstreamException(errDetails.toString());
			}
		}
		return null;
	}

	@Override
	public DeploymentResponse updateDeployment(String deploymentId, String tenant, 
	DeploymentRequest deploymentRequest) throws BadRequestException,
	ServiceAlreadyExistsException,
	ServerErrorException,
	DownstreamException {
		String url = buildUrl(new String[] {baseUrl, UPDATE_PATH, deploymentId}, new String[] {"cfy_tenant_name",tenant});
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<DeploymentResponse> result = restTemplate.exchange(url,  HttpMethod.PUT, new HttpEntity<DeploymentRequest>(deploymentRequest, headers),
					new ParameterizedTypeReference<DeploymentResponse>() {
			});
			return result.getBody();
		} catch(HttpServerErrorException  | HttpClientErrorException e) { 
			DeploymentErrorResponse errBody = null;
			String errMsg = "";
			try {
				errBody = objectMapper.readValue(e.getResponseBodyAsString(), DeploymentErrorResponse.class);
			} catch (IOException e1) {
				errBody = null;
			}
			if (errBody != null) {
				errMsg = errBody.getMessage();
			}
			StringBuilder errDetails = new StringBuilder();
			errDetails.append(e.getMessage()).append("  ").append(errMsg);
			if (e.getStatusCode().value() == 400 || e.getStatusCode().value() == 415 || e.getStatusCode().value() == 404) {
				throw new BadRequestException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 409) {
				throw new ServiceAlreadyExistsException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 500) {
				throw new ServerErrorException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 502 || e.getStatusCode().value() == 504) {
				throw new DownstreamException(errDetails.toString());
			}
		}
		return null; // Perhaps this should be a proper JSON error response.
	}

	
	@Override
	public void deleteDeployment(String deploymentId, String tenant)
			throws BadRequestException, ServerErrorException, DownstreamException, DeploymentNotFoundException {
		String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS, deploymentId}, new String[] {"cfy_tenant_name",tenant, "ignore_failure", "true"});
		try {
			restTemplate.exchange(url, HttpMethod.DELETE, null,
					new ParameterizedTypeReference<DeploymentResponse>() {
			});
		} catch(HttpServerErrorException  | HttpClientErrorException e) { 
			DeploymentErrorResponse errBody = null;
			String errMsg = "";
			try {
				errBody = objectMapper.readValue(e.getResponseBodyAsString(), DeploymentErrorResponse.class);
			} catch (IOException e1) {
				errBody = null;
			}
			if (errBody != null) {
				errMsg = errBody.getMessage();
			}
			StringBuilder errDetails = new StringBuilder();
			errDetails.append(e.getMessage()).append("  ").append(errMsg);
			if (e.getStatusCode().value() == 400 || e.getStatusCode().value() == 415) {
				throw new BadRequestException(errDetails.toString());
			}
			else if (e.getStatusCode().value() == 404) {
				throw new DeploymentNotFoundException(e.getMessage());
			}
			else if(e.getStatusCode().value() == 500) {
				throw new ServerErrorException(errDetails.toString());
			}
			else if(e.getStatusCode().value() == 502 || e.getStatusCode().value() == 504) {
				throw new DownstreamException(errDetails.toString());
			}
		}
	}
}
