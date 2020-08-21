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

package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.onap.ccsdk.dashboard.model.cloudify.ServiceRefCfyList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author rp5662
 *
 */
public class ServiceTypeSummary {

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ServiceTypeSummary [owner=" + owner + ", typeName=" + typeName + ", typeVersion="
            + typeVersion + ", component=" + component + ", typeId=" + typeId + ", created="
            + created + "]";
    }

    /** Owner of the ServiceType */
    private final String owner;

    /** Name of the ServiceType */
    private final String typeName;

    /** Version number for this ServiceType */
    private final Integer typeVersion;

    /** controller application name */
    private final String application;

    /** onboarding component name */
    private final String component;

    /** Unique identifier for this ServiceType */
    private final Optional<String> typeId;

    /** Creation date of the ServiceType */
    private final Optional<String> created;

    /** internal role based setting */
    private Optional<Boolean> canDeploy;

    private ServiceRefCfyList deployments;

    private Optional<Boolean> expanded;

    @JsonCreator
    public ServiceTypeSummary(@JsonProperty("owner") String owner,
        @JsonProperty("typeName") String typeName, @JsonProperty("typeVersion") Integer typeVersion,
        @JsonProperty("application") String application,
        @JsonProperty("component") String component, @JsonProperty("typeId") String typeId,
        @JsonProperty("created") String created, @JsonProperty("canDeploy") Boolean canDeploy) {

        this.owner = owner;
        this.typeName = typeName;
        this.typeVersion = typeVersion;
        this.application = application;
        this.component = component;
        this.typeId = Optional.ofNullable(typeId);
        this.created = Optional.ofNullable(created);
        this.canDeploy = Optional.of(false);
        this.expanded = Optional.of(false);
    }

    private ServiceTypeSummary(Builder builder) {
        this.typeName = builder.typeName;
        this.owner = builder.owner;
        this.typeVersion = builder.typeVersion;
        this.typeId = builder.typeId;
        this.application = builder.application;
        this.component = builder.component;
        this.created = builder.created;
        this.canDeploy = builder.canDeploy;
    }

    public static class Builder {
        private String owner;
        private String typeName;
        private Integer typeVersion;
        private String application;
        private String component;
        private Optional<String> typeId = Optional.empty();
        private Optional<String> created = Optional.empty();
        private Optional<Boolean> canDeploy = Optional.empty();

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder typeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public Builder typeVersion(Integer typeVersion) {
            this.typeVersion = typeVersion;
            return this;
        }

        public Builder application(String application) {
            this.application = application;
            return this;
        }

        public Builder component(String component) {
            this.component = component;
            return this;
        }

        public ServiceTypeSummary build() {
            return new ServiceTypeSummary(this);
        }
    }

    public String getTypeName() {
        return typeName;
    }

    public String getOwner() {
        return owner;
    }

    public Integer getTypeVersion() {
        return typeVersion;
    }

    public String getApplication() {
        return application;
    }

    public String getComponent() {
        return component;
    }

    public Optional<String> getTypeId() {
        return typeId;
    }

    public Optional<String> getCreated() {
        return created;
    }

    public Optional<Boolean> getCanDeploy() {
        return canDeploy;
    }

    public void setCanDeploy(Optional<Boolean> canDeploy) {
        this.canDeploy = canDeploy;
    }

    public ServiceRefCfyList getDeployments() {
        return deployments;
    }

    public void setDeployments(ServiceRefCfyList deployments) {
        this.deployments = deployments;
    }

    // Used for back end search, only searches the fields displayed in the front
    // end.
    public boolean contains(String searchString) {
        if (StringUtils.containsIgnoreCase(this.getOwner(), searchString)
            || StringUtils.containsIgnoreCase(this.getTypeId().get(), searchString)
            || StringUtils.containsIgnoreCase(this.getTypeName(), searchString)
            || StringUtils.containsIgnoreCase(Integer.toString(this.getTypeVersion()), searchString)
            || StringUtils.containsIgnoreCase(this.getCreated().get(), searchString)
            || StringUtils.containsIgnoreCase(this.getComponent(), searchString)
            || StringUtils.containsIgnoreCase(this.getApplication(), searchString)) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceTypeSummary other = (ServiceTypeSummary) obj;
        if (typeId == null) {
            if (other.typeId != null)
                return false;
        } else if (!typeId.equals(other.typeId))
            return false;
        return true;
    }

}
