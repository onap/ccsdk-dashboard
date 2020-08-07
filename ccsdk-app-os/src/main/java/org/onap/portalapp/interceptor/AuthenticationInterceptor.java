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
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.portalapp.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

import org.apache.http.HttpStatus;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private UserProfileService userSvc;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String authString = request.getHeader("Authorization");
		try {
		if(authString == null || authString.isEmpty())
		{
			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
			response.sendError(HttpStatus.SC_UNAUTHORIZED, "Authentication information is missing");
			return false; //Do not continue with request
		} else {
			String decodedAuth = "";
			String[] authParts = authString.split("\\s+");
			String authInfo = authParts[1];
			byte[] bytes = null;
			bytes = Base64.getDecoder().decode(authInfo);
					//DatatypeConverter.parseBase64Binary(authInfo);
			decodedAuth = new String(bytes,StandardCharsets.UTF_8);
			String[] authen = decodedAuth.split(":");

			if  (authen.length > 1) {
			    User user = userSvc.getUserByLoginId(authen[0]);
			    if (user == null) {
	                 response.sendError(HttpStatus.SC_UNAUTHORIZED, "Un-authorized to perform this operation");
	                    return false;
			    }
/*				ResponseEntity<String> getResponse = 
				    userSrvc.checkUserExists(authen[0], authen[1]);
				if (getResponse.getStatusCode().value() != 200) {	
					response.sendError(HttpStatus.SC_UNAUTHORIZED, "Un-authorized to perform this operation");
					return false;
				}*/
			} else {
				return false;
			}
		}
		} catch (Exception e) {
			try {
				response.sendError(HttpStatus.SC_UNAUTHORIZED, e.getMessage());
			} catch (IOException e1) {
				return false;
			}
			return false;
		}
		return true; //Continue with request
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//Ignore
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//Ignore
	}
}
