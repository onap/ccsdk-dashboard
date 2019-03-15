/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
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
package org.onap.ccsdk.dashboard.controller;

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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.onap.portalsdk.core.auth.LoginStrategy;
import org.onap.portalsdk.core.command.LoginBean;
import org.onap.portalsdk.core.controller.UnRestrictedBaseController;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.menu.MenuProperties;
import org.onap.portalsdk.core.onboarding.listener.PortalTimeoutHandler;
import org.onap.portalsdk.core.onboarding.util.PortalApiConstants;
import org.onap.portalsdk.core.onboarding.util.PortalApiProperties;
import org.onap.portalsdk.core.service.LoginService;
import org.onap.portalsdk.core.service.RoleService;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/")
/**
 * Replicated from
 * org.openecomp.portalapp.controller.core.SingleSignOnController to modify the
 * behavior of sending user's browser on a detour of Portal app to get the
 * EPService cookie.
 */
public class ECDSingleSignOnController extends UnRestrictedBaseController {

    private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ECDSingleSignOnController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginStrategy loginStrategy;

    @Autowired
    private RoleService roleService;

    private String viewName;
    private String welcomeView;

    /**
     * Handles requests directed to the single sign-on page by the session timeout
     * interceptor.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Redirect to an appropriate address
     * @throws Exception On any failure
     */
    @RequestMapping(value = { "/single_signon.htm" }, method = RequestMethod.GET)
    public ModelAndView singleSignOnLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> model = new HashMap<String, String>();
        HashMap<String, String> additionalParamsMap = new HashMap<String, String>();
        LoginBean commandBean = new LoginBean();

        // SessionTimeoutInterceptor sets these parameters
        String forwardURL = URLDecoder.decode(request.getParameter("forwardURL"), "UTF-8");
        String redirectToPortal = request.getParameter("redirectToPortal");

        if (isLoginCookieExist(request) && redirectToPortal == null) {
            HttpSession session = null;
            session = AppUtils.getSession(request);
            User user = UserUtils.getUserSession(request);
            if (session == null || user == null) {

                final String authMech = SystemProperties.getProperty(SystemProperties.AUTHENTICATION_MECHANISM);
                String userId = loginStrategy.getUserId(request);
                commandBean.setUserid(userId);
                commandBean = getLoginService().findUser(commandBean,
                        (String) request.getAttribute(MenuProperties.MENU_PROPERTIES_FILENAME_KEY),
                        additionalParamsMap);
                if (commandBean.getUser() == null) {
                    String loginErrorMessage = (commandBean.getLoginErrorMessage() != null)
                            ? commandBean.getLoginErrorMessage()
                            : SystemProperties.MESSAGE_KEY_LOGIN_ERROR_USER_NOT_FOUND;
                    model.put(LoginStrategy.ERROR_MESSAGE_KEY, SystemProperties.getProperty(loginErrorMessage));
                    final String redirectUrl = PortalApiProperties.getProperty(PortalApiConstants.ECOMP_REDIRECT_URL)
                            + "?noUserError=Yes";
                    logger.debug(EELFLoggerDelegate.debugLogger, "singleSignOnLogin: user is null, redirect URL is {}",
                            redirectUrl);
                    return new ModelAndView("redirect:" + redirectUrl);
                } else {
                    // store the user's information in the session
                    String loginMethod;
                    if (null == authMech || "".equals(authMech) || "BOTH".equals(authMech)) {
                        loginMethod = SystemProperties.getProperty(SystemProperties.LOGIN_METHOD_CSP);
                    } else if ("CSP".equals(authMech)) {
                        loginMethod = SystemProperties.getProperty(SystemProperties.LOGIN_METHOD_CSP);
                    } else {
                        loginMethod = SystemProperties.getProperty(SystemProperties.LOGIN_METHOD_WEB_JUNCTION);
                    }
                    UserUtils.setUserSession(request, commandBean.getUser(), commandBean.getMenu(),
                            commandBean.getBusinessDirectMenu(), loginMethod, roleService.getRoleFunctions(userId));
                    initateSessionMgtHandler(request);
                    logger.debug(EELFLoggerDelegate.debugLogger,
                            "singleSignOnLogin: create new user session for expired user {}; user {} exists in the system",
                            userId, commandBean.getUser().getOrgUserId());
                    return new ModelAndView("redirect:" + forwardURL);
                }
            } // user is null or session is null
            else {
                // both user and session are non-null.
                logger.info(EELFLoggerDelegate.debugLogger, "singleSignOnLogin: redirecting to the forwardURL {}",
                        forwardURL);
                return new ModelAndView("redirect:" + forwardURL);
            }
        } else {
            /*
             * Login cookie not found, or redirect-to-portal parameter was found.
             */
            if (isPortalAvailable()) {
                /*
                 * Redirect the user to the portal with a suitable return URL. The forwardURL
                 * parameter that arrives as a parameter is a partial (not absolute) request
                 * path for a page in the application. The challenge here is to compute the
                 * correct absolute path for the original request so the portal can redirect the
                 * user back to the right place. If the application sits behind WebJunction, or
                 * if separate FE-BE hosts are used, then the URL yielded by the request has a
                 * host name that is not reachable by the user.
                 */
                String returnToAppUrl = null;
                if (SystemProperties.containsProperty(SystemProperties.APP_BASE_URL)) {
                    // New feature as of 1610, release 3.3.3:
                    // application can publish a base URL in system.properties
                    String appUrl = SystemProperties.getProperty(SystemProperties.APP_BASE_URL);
                    returnToAppUrl = appUrl + (appUrl.endsWith("/") ? "" : "/") + forwardURL;
                    logger.debug(EELFLoggerDelegate.debugLogger,
                            "singleSignOnLogin: using app base URL {} and redirectURL {}", appUrl, returnToAppUrl);
                } else {
                    /**
                     * Be backward compatible with applications that don't need this feature. This
                     * is the controller for the single_signon.htm page, so the replace should
                     * always find the specified token.
                     */
                    returnToAppUrl = ((HttpServletRequest) request).getRequestURL().toString()
                            .replace("single_signon.htm", forwardURL);
                    logger.debug(EELFLoggerDelegate.debugLogger, "singleSignOnLogin: computed redirectURL {}",
                            returnToAppUrl);
                }
                final String encodedReturnToAppUrl = URLEncoder.encode(returnToAppUrl, "UTF-8");
                // Also send the application's UEB key so Portal can block URL
                // reflection attacks.
                final String uebAppKey = PortalApiProperties.getProperty(PortalApiConstants.UEB_APP_KEY);
                final String url = PortalApiProperties.getProperty(PortalApiConstants.ECOMP_REDIRECT_URL);
                final String portalUrl = url.substring(0, url.lastIndexOf('/')) + "/process_csp";
                final String redirectUrl = portalUrl + "?uebAppKey=" + uebAppKey + "&redirectUrl="
                        + encodedReturnToAppUrl;
                logger.debug(EELFLoggerDelegate.debugLogger, "singleSignOnLogin: portal-bound redirect URL is {}",
                        redirectUrl);
                return new ModelAndView("redirect:" + redirectUrl);
            } // portal is available

            else {
                /*
                 * Portal is not available. Redirect user to the login page, ignoring the
                 * forwardURL parameter.
                 */
                return new ModelAndView("redirect:login.htm");
            }

        }
    }

    /**
     * Discover if the portal is available by GET-ing a resource from the REST URL
     * specified in portal.properties, using a very short timeout.
     * 
     * @return True if the portal answers, otherwise false.
     */
    private boolean isPortalAvailable() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        final int oneSecond = 1000;
        httpRequestFactory.setConnectionRequestTimeout(oneSecond);
        httpRequestFactory.setConnectTimeout(oneSecond);
        httpRequestFactory.setReadTimeout(oneSecond);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        boolean avail = true;
        try {
            final String portalUrl = PortalApiProperties.getProperty(PortalApiConstants.ECOMP_REST_URL);
            String s = restTemplate.getForObject(portalUrl, String.class);
            logger.trace("isPortalAvailable got response {}", s);
        } catch (RestClientException ex) {
            logger.debug("isPortalAvailable failed", ex);
            avail = false;
        }
        return avail;
    }

    protected void initateSessionMgtHandler(HttpServletRequest request) {
        String portalJSessionId = getPortalJSessionId(request);
        String jSessionId = getJessionId(request);
        PortalTimeoutHandler.sessionCreated(portalJSessionId, jSessionId, AppUtils.getSession(request));
    }

    public boolean isLoginCookieExist(HttpServletRequest request) {
        Cookie ep = WebUtils.getCookie(request, LoginStrategy.EP_SERVICE);
        return (ep != null);
    }

    public String getPortalJSessionId(HttpServletRequest request) {
        Cookie ep = WebUtils.getCookie(request, LoginStrategy.EP_SERVICE);
        return ep.getValue();
    }

    public String getJessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getWelcomeView() {
        return welcomeView;
    }

    public void setWelcomeView(String welcomeView) {
        this.welcomeView = welcomeView;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
