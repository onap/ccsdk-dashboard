/*-
 * ================================================================================
 * DCAE Dashboard
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

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onap.portalsdk.core.auth.LoginStrategy;
import org.onap.portalsdk.core.command.LoginBean;
import org.onap.portalsdk.core.domain.Role;
import org.onap.portalsdk.core.domain.RoleFunction;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.menu.MenuProperties;
import org.onap.portalsdk.core.onboarding.exception.PortalAPIException;
import org.onap.portalsdk.core.service.LoginService;
import org.onap.portalsdk.core.service.RoleService;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class LoginStrategyImpl extends LoginStrategy {

    EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(LoginStrategyImpl.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @Override
    public ModelAndView doExternalLogin(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        invalidateExistingSession(request);

        LoginBean commandBean = new LoginBean();
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        commandBean.setLoginId(loginId);
        commandBean.setLoginPwd(password);
        // commandBean.setUserid(loginId);
        commandBean = loginService.findUser(commandBean,
            (String) request.getAttribute(MenuProperties.MENU_PROPERTIES_FILENAME_KEY),
            new HashMap());
        List<RoleFunction> roleFunctionList = roleService.getRoleFunctions(loginId);

        if (commandBean.getUser() == null) {
            String loginErrorMessage =
                (commandBean.getLoginErrorMessage() != null) ? commandBean.getLoginErrorMessage()
                    : "login.error.external.invalid - User name and/or password incorrect";
            Map<String, String> model = new HashMap<>();
            model.put("error", loginErrorMessage);
            return new ModelAndView("login_external", "model", model);
        } else {
            // store the currently logged in user's information in the session
            UserUtils.setUserSession(request, commandBean.getUser(), commandBean.getMenu(),
                commandBean.getBusinessDirectMenu(),
                SystemProperties.getProperty(SystemProperties.LOGIN_METHOD_BACKDOOR),
                roleFunctionList);
            // set the user's max role level in session
            final String adminRole = "System Administrator";
            final String standardRole = "Standard User";
            final String readRole = "Read Access";
            final String writeRole = "Write Access";

            String maxRole = "";
            String authType = "READ";
            String accessLevel = "app";

            Predicate<Role> adminRoleFilter =
                p -> p.getName() != null && p.getName().equalsIgnoreCase(adminRole);

            Predicate<Role> writeRoleFilter =
                p -> p.getName() != null && (p.getName().equalsIgnoreCase(writeRole)
                    || p.getName().equalsIgnoreCase(standardRole));

            Predicate<Role> readRoleFilter =
                p -> p.getName() != null && (p.getName().equalsIgnoreCase(readRole));

            if (UserUtils.getUserSession(request) != null) {
                @SuppressWarnings("unchecked")
                Collection<org.onap.portalsdk.core.domain.Role> userRoles =
                    UserUtils.getRoles(request).values();
                if (userRoles.stream().anyMatch(adminRoleFilter)) {
                    maxRole = "admin";
                } else if (userRoles.stream().anyMatch(writeRoleFilter)) {
                    maxRole = "write";
                } else if (userRoles.stream().anyMatch(readRoleFilter)) {
                    maxRole = "read";
                }
                switch (maxRole) {
                    case "admin":
                        authType = "ADMIN";
                        accessLevel = "ops";
                        break;
                    case "write":
                        authType = "WRITE";
                        accessLevel = "dev";
                        break;
                    case "read":
                        authType = "READ";
                        accessLevel = "dev";
                        break;
                    default:
                        accessLevel = "app";
                }
            }
            AppUtils.getSession(request).setAttribute("role_level", accessLevel);
            AppUtils.getSession(request).setAttribute("auth_role", authType);
            initateSessionMgtHandler(request);
            // user has been authenticated, now take them to the welcome page
            return new ModelAndView("redirect:welcome");
        }
    }

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

    private static String getUserIdFromCookie(HttpServletRequest request) {
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
