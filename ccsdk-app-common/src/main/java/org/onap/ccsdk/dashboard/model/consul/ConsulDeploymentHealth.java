/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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
 *******************************************************************************/

package org.onap.ccsdk.dashboard.model.consul;

public class ConsulDeploymentHealth {

    private final String node;
    private final String checkID;
    private final String name;
    private final String status;
    private final String serviceID;
    private final String serviceName;

    private ConsulDeploymentHealth(String node, String checkID, String name, String status,
        String serviceID, String serviceName) {
        this.node = node;
        this.checkID = checkID;
        this.name = name;
        this.status = status;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
    }

    public String getNode() {
        return node;
    }

    public String getCheckID() {
        return checkID;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public static class Builder {
        private final String node;
        private final String checkID;
        private final String name;
        private final String status;
        private final String serviceID;
        private final String serviceName;

        public Builder(ConsulServiceHealth input) {
            this.node = input.node;
            this.checkID = input.checkID;
            this.name = input.name;
            this.status = input.status;
            this.serviceID = input.serviceID;
            this.serviceName = input.serviceName;
        }

        public ConsulDeploymentHealth build() {
            return new ConsulDeploymentHealth(node, checkID, name, status, serviceID, serviceName);
        }

        public String getNode() {
            return node;
        }

        public String getCheckID() {
            return checkID;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public String getServiceID() {
            return serviceID;
        }

        public String getServiceName() {
            return serviceName;
        }
    }
}
