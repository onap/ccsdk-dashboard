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
package org.onap.portalapp.conf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.conf.AppInitializer;
import org.onap.portalsdk.core.logging.format.AlarmSeverityEnum;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;

public class ExternalAppInitializer extends AppInitializer {

    /*
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        WebApplicationContext context = super.createServletApplicationContext();
        try {
            ((ConfigurableEnvironment) context.getEnvironment()).setActiveProfiles("onap");
        } catch (Exception e) {
            System.out.println("Unable to set the active profile" + e.getMessage());
            //throw e;
        }
        return context;
    }
*/
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return super.getRootConfigClasses();
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        Class<?> appConfigClass = ExternalAppConfig.class;
        // Show something on stdout to indicate the app is starting.
        System.out.println(
            "ExternalAppInitializer: servlet configuration class is " + appConfigClass.getName());
        return new Class[] {appConfigClass, DashboardProperties.class};
    }

    /*
     * URL request will direct to the Spring dispatcher for processing
     */
    @Override
    protected String[] getServletMappings() {
        return super.getServletMappings();
    }

}
