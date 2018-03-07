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
package org.onap.ccsdk.dashboard.model;

import org.onap.ccsdk.dashboard.exception.DashboardControllerException;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.onboarding.util.CipherUtil;

/**
 * Model with Controller username and password for use only within the back end;
 * never serialized as JSON.
 */
public class ControllerEndpointCredentials extends ControllerEndpointTransport {

	private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ControllerEndpointCredentials.class);

	public String username;
	public String password;
	public boolean isEncryptedPass;

	public ControllerEndpointCredentials(boolean selected, String name, String url, String username, String password,
			boolean isEncryptedPass) {
		super(selected, name, url);
		this.username = username;
		this.password = password;
		this.isEncryptedPass = isEncryptedPass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getEncryptedPassword() {
		return isEncryptedPass;
	}

	public void setEncryptedPassword(boolean isEncryptedPass) {
		this.isEncryptedPass = isEncryptedPass;
	}

	/**
	 * Convenience method to yield a ControllerEndpointTransport object.
	 * 
	 * @return ControllerEndpoint with copy of the non-privileged data
	 */
	public ControllerEndpointTransport toControllerEndpointTransport() {
		return new ControllerEndpointTransport(getSelected(), getName(), getUrl());
	}

	/**
	 * Accepts clear text and stores an encrypted value; as a side effect, sets the
	 * encrypted flag to true.
	 * 
	 * @param plainText
	 *            Clear-text password
	 * @throws DashboardControllerException
	 *             If encryption fails
	 */
	public void encryptPassword(final String plainText) throws DashboardControllerException {
		try {
			this.password = CipherUtil.encrypt(plainText);
			this.isEncryptedPass = true;
		} catch (Exception ex) {
			logger.error("encryptPassword failed", ex);
			throw new DashboardControllerException(ex);
		}
	}

	/**
	 * Client should call this method if {@link #getEncryptedPassword()} returns
	 * true.
	 * 
	 * @return Clear-text password.
	 * @throws DashboardControllerException
	 *             If decryption fails
	 */
	public String decryptPassword() throws DashboardControllerException {
		try {
			return CipherUtil.decrypt(password);
		} catch (Exception ex) {
			logger.error("decryptPassword failed", ex);
			throw new DashboardControllerException(ex);
		}
	}
}