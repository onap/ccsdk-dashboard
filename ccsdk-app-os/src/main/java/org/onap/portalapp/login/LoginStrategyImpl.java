/*-
 * ================================================================================
 * ECOMP Portal SDK
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ================================================================================
 */

package org.onap.portalapp.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onap.portalsdk.core.auth.LoginStrategy;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.onboarding.exception.PortalAPIException;
import org.onap.portalsdk.core.onboarding.util.CipherUtil;
import org.onap.portalsdk.core.onboarding.util.PortalApiConstants;
import org.onap.portalsdk.core.onboarding.util.PortalApiProperties;
import org.springframework.web.servlet.ModelAndView;

public class LoginStrategyImpl extends LoginStrategy {

    EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(LoginStrategyImpl.class);

    @Override
    public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        // 'login' for opensource is same as 'external' login.
        return doExternalLogin(request, response);
    }

    @Override
    public String getUserId(HttpServletRequest request) throws PortalAPIException {
        // Check ECOMP Portal cookie
        if (!isLoginCookieExist(request))
            return null;

        String userid = null;
        try {
            userid = getUserIdFromCookie(request);
        } catch (Exception e) {
            logger.error(EELFLoggerDelegate.errorLogger, "getUserId failed", e);
        }
        return userid;
    }

    private static String getUserIdFromCookie(HttpServletRequest request){
        String userId = "";
        Cookie[] cookies = request.getCookies();
        Cookie userIdcookie = null;
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(USER_ID))
                    userIdcookie = cookie;
        if (userIdcookie != null) {
            userId = ""; // CipherUtil.decrypt(userIdcookie.getValue(),
            // PortalApiProperties.getProperty(PortalApiConstants.Decryption_Key));
        }
        return userId;

    }

    private static boolean isLoginCookieExist(HttpServletRequest request) {
        Cookie ep = getCookie(request, EP_SERVICE);
        return (ep != null);
    }

    private static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(cookieName))
                    return cookie;

        return null;
    }

}