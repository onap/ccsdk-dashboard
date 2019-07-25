/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 *  
 *  Modifications Copyright (C) 2019 IBM.
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

package org.onap.ccsdk.dashboard.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Publishes a list of constants and methods to access the properties that are
 * read by Spring from the specified configuration file(s).
 * 
 * Should be used like this (and never in a constructor):
 * 
 * <pre>
 * &#64;Autowired
 * DashboardProperties properties;
 * </pre>
 */
@Configuration
@PropertySource(value = { "${container.classpath:}/WEB-INF/conf/dashboard.properties" })
public class DashboardProperties {

    /**
     * Key for property that indicates if test data should be used
     */
    public static final String CONTROLLER_MOCK_DATA = "controller.mock.data";
    /**
     * Key for property with list of controllers
     */
    public static final String CONTROLLER_KEY_LIST = "controller.key.list";
    /**
     * Subkey for property with Controller name (description)
     */
    public static final String CONTROLLER_SUBKEY_NAME = "name";
    /**
     * Subkey for property with Controller URL
     */
    public static final String CONTROLLER_SUBKEY_URL = "url";
    /**
     * Subkey for property with Inventory URL
     */
    public static final String CONTROLLER_SUBKEY_INVENTORY_URL = "inventory.url";
    /**
     * Subkey for property with Deployment Handler URL
     */
    public static final String CONTROLLER_SUBKEY_DHANDLER_URL = "dhandler.url";
    /**
     * Subkey for property with Consul URL
     */
    public static final String CONTROLLER_SUBKEY_CONSUL_URL = "consul.url";
    /**
     * Subkey for property with Controller user name for authentication
     */
    public static final String CONTROLLER_SUBKEY_USERNAME = "username";
    /**
     * Subkey for property with Controller password
     */
    public static final String CONTROLLER_SUBKEY_PASS = "password";
    /**
     * Subkey for property with Controller password encryption status
     */
    public static final String CONTROLLER_SUBKEY_ENCRYPTED = "is_encrypted";
    /**
     * Key for dashboard deployment environment - dev/uat/prod
     */
    public static final String CONTROLLER_IN_ENV = "controller.env";

    /**
     * Key for cloudify tenant environment
     */
    public static final String CLOUDIFY_TENANT_PRIM = "cloudify.tenant.primary";

    /**
     * Key for aic tenant environment
     */
    public static final String AIC_TENANT_PRIM = "aic.tenant.primary";

    /**
     * Key for controller type: ATT or OS
     */
    public static final String CONTROLLER_TYPE = "controller.type";

    /**
     * Key for K8s deploy permission string
     * 
     */
    public static final String APP_K8S_PERM = "k8s.deploy.perm";

    public static final String OPS_K8S_URL = "ops.k8s.url";

    public static final String OPS_GRAFANA_URL = "ops.grf.url";

    public static final String OPS_CLOUDIFY_URL = "ops.cfy.url";

    public static final String OPS_CONSUL_URL = "ops.cnsl.url";

    public static final String OPS_PROMETHEUS_URL = "ops.prom.url";

    public static final String OPS_DBCL_URL = "ops.dbcl.url";

    private static Environment environment;

    protected Environment getEnvironment() {
        return environment;
    }

    /**
     * @param environment Environment
     */
    @Autowired
    public void setEnvironment(final Environment environment) {
        DashboardProperties.environment = environment;
    }

    /**
     * @param key Property key
     * @return True or false
     */
    public static boolean containsProperty(final String key) {
        return environment.containsProperty(key);
    }

    /**
     * @param key Property key
     * @return String value; throws unchecked exception if key is not found
     */
    public static String getProperty(final String key) {
        return environment.getRequiredProperty(key);
    }

    /**
     * @param key Property key
     * @return String value; throws unchecked exception if key is not found
     */
    public static String getPropertyDef(final String key, String defVal) {
        return environment.getProperty(key, defVal);
    }

    /**
     * @param key Property key
     * @return True or False; null if key is not found
     */
    public static Boolean getBooleanProperty(final String key) {
        final String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }

    /**
     * Gets the values for a comma-separated list property value as a String array.
     * 
     * @param key Property key
     * @return Array of values with leading and trailing whitespace removed; null if
     *         key is not found.
     */
    public static String[] getCsvListProperty(final String key) {
        String listVal = getProperty(key);
        if (listVal == null)
            return null;
        
        return listVal.split("\\s*,\\s*");
    }

    /**
     * Convenience method to get a property from the fake hierarchical key-value
     * set.
     * 
     * @param controllerKey First part of key
     * @param propKey       Second part of key
     * @return Property value for key "controllerKey.propKey"
     */
    public static String getControllerProperty(final String controllerKey, final String propKey) {
        final String key = controllerKey + '.' + propKey;
        return getProperty(key);
    }

}
