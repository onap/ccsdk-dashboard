/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
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
package org.onap.portalapp.lm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

// import org.onap.portalsdk.core.lm.FusionLicenseManager;
interface FusionLicenseManager {
    public void initKeyStoreParam();

    public void initCipherParam();

    public void initLicenseParam();

    public int installLicense();

    public int verifyLicense(ServletContext context);

    public void setExpiredDate(Date expiredDate);

    public void doInitWork();

    public void generateLicense(Map<String, String> clientInfoMap, List<String> ipAddressList)
        throws Exception;

    public String nvl(String s);

    public Date getExpiredDate();
}


public class FusionLicenseManagerImpl implements FusionLicenseManager {

    private Date expiredDate;

    public FusionLicenseManagerImpl() {
    }

    /**
     * An implementation of the KeyStoreParam interface that returns the
     * information required to work with the keystore containing the private key.
     */
    @Override
    public void initKeyStoreParam() {
    }

    @Override
    public void initCipherParam() {
    }

    /**
     * Create/populate the "licenseParm" field.
     */
    @Override
    public void initLicenseParam() {
    }

    @Override
    public void doInitWork() {
    }

    /**
     * Prompt the user for the location of their license file, get the filename,
     * then try to install the file.
     * 
     * @return true if the license installed properly, false otherwise.
     */
    @Override
    public int installLicense() {
        // return INVALID_LICENSE;
        return -1;
    }

    @Override
    public synchronized int verifyLicense(ServletContext context) {

        // return INVALID_LICENSE;
        return -1;
    }

    @Override
    public void generateLicense(Map<String, String> clientInfoMap, List<String> ipAddressList)
        throws Exception {
    }

    @Override
    public String nvl(String s) {
        return (s == null) ? "" : s;
    }

    @Override
    public Date getExpiredDate() {
        return expiredDate;
    }

    @Override
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

}
