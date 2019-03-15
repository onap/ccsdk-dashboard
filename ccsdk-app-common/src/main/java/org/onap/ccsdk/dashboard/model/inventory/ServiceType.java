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
package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceType {

    /** Owner of the ServiceType */
    private final String owner;

    /** Name of the ServiceType */
    private final String typeName;

    /** Version number for this ServiceType */
    private final Integer typeVersion;

    /** String representation of a Cloudify blueprint with unbound variables */
    private final String blueprintTemplate;

    /** controller application name */
    private final String application;
    /** onboarding component name */
    private final String component;
    /**
     * List of service ids used to associate with this ServiceType. ServiceTypes
     * with this property as null or empty means they apply for every service id.
     */
    private final Collection<String> serviceIds;

    /** Collection of vnfTypes associated with this ServiceType */
    private final Collection<String> vnfTypes;

    /**
     * List of service locations used to associate with this ServiceType.
     * ServiceTypes with this property as null or empty means they apply for every
     * service location.
     */
    private final Collection<String> serviceLocations;

    /**
     * Id of service this ServiceType is associated with. Value source is from
     * ASDC's notification event's field 'serviceInvariantUUID'.
     */
    private final Optional<String> asdcServiceId;

    /**
     * Id of vf/vnf instance this ServiceType is associated with. Value source is
     * from ASDC's notification event's field 'resourceInvariantUUID'.
     */
    private final Optional<String> asdcResourceId;

    /** URL to the ASDC service model */
    private final Optional<String> asdcServiceURL;

    /** Unique identifier for this ServiceType */
    private final Optional<String> typeId;

    /** Link to the ServiceType */
    private final Optional<Link> selfLink;

    /** Creation date of the ServiceType */
    private final Optional<String> created;

    /** Deactivated timestamp for this ServiceType */
    private final Optional<String> deactivated;

    /** Map that stores the inputs for a Blueprint */
    private final Map<String, BlueprintInput> blueprintInputs;

    /** Description of a blueprint */
    private final String blueprintDescription;

    /** internal role based setting */
    private Optional<Boolean> canDeploy;

    public static class Builder {
        private final String blueprintTemplate;
        private final String owner;
        private final String typeName;
        private final Integer typeVersion;
        private final String application;
        private final String component;

        private Optional<String> asdcResourceId = Optional.empty();
        private Optional<String> asdcServiceId = Optional.empty();
        private Optional<String> asdcServiceURL = Optional.empty();
        private Optional<String> created = Optional.empty();
        private Optional<String> deactivated = Optional.empty();
        private Optional<Link> selfLink = Optional.empty();
        private Optional<String> typeId = Optional.empty();
        private Collection<String> serviceIds = new LinkedList<String>();
        private Collection<String> serviceLocations = new LinkedList<String>();
        private Collection<String> vnfTypes = new LinkedList<String>();
        private Map<String, BlueprintInput> blueprintInputs = new HashMap<String, BlueprintInput>();
        private final String blueprintDescription;
        private Optional<Boolean> canDeploy = Optional.of(true);

        public Builder(String owner, String typeName, Integer typeVersion, String blueprintTemplate,
                String blueprintDescription, String application, String component) {
            this.owner = owner;
            this.typeName = typeName;
            this.typeVersion = typeVersion;
            this.blueprintTemplate = blueprintTemplate;
            this.blueprintDescription = blueprintDescription;
            this.application = application;
            this.component = component;
        }

        public Builder(ServiceType clone) {
            this.asdcResourceId = clone.getAsdcResourceId();
            this.asdcServiceId = clone.getAsdcServiceId();
            this.asdcServiceURL = clone.getAsdcServiceURL();
            this.blueprintTemplate = clone.getBlueprintTemplate();
            this.created = clone.getCreated();
            this.deactivated = clone.getDeactivated();
            this.owner = clone.getOwner();
            this.selfLink = clone.getSelfLink();
            this.serviceIds = clone.getServiceIds();
            this.serviceLocations = clone.getServiceLocations();
            this.typeId = clone.getTypeId();
            this.typeName = clone.getTypeName();
            this.typeVersion = clone.getTypeVersion();
            this.vnfTypes = clone.getVnfTypes();
            this.blueprintInputs = clone.getBlueprintInputs();
            this.blueprintDescription = clone.getBlueprintDescription();
            this.canDeploy = clone.getCanDeploy();
            this.application = clone.getApplication();
            this.component = clone.getComponent();
        }

        public Builder typeId(String typeId) {
            this.typeId = Optional.of(typeId);
            return this;
        }

        public ServiceType build() {
            return new ServiceType(this);
        }
    }

    private ServiceType(Builder builder) {
        this.owner = builder.owner;
        this.typeName = builder.typeName;
        this.typeVersion = builder.typeVersion;
        this.blueprintTemplate = builder.blueprintTemplate;
        this.application = builder.application;
        this.component = builder.component;
        this.serviceIds = builder.serviceIds;
        this.vnfTypes = builder.vnfTypes;
        this.serviceLocations = builder.serviceLocations;

        this.asdcServiceId = builder.asdcServiceId;
        this.asdcResourceId = builder.asdcResourceId;
        this.asdcServiceURL = builder.asdcServiceURL;
        this.typeId = builder.typeId;
        this.selfLink = builder.selfLink;
        this.created = builder.created;
        this.deactivated = builder.deactivated;
        this.blueprintInputs = builder.blueprintInputs;
        this.blueprintDescription = builder.blueprintDescription;
        this.canDeploy = builder.canDeploy;
    }

    @JsonCreator
    public ServiceType(@JsonProperty("owner") String owner, @JsonProperty("typeName") String typeName,
            @JsonProperty("typeVersion") Integer typeVersion,
            @JsonProperty("blueprintTemplate") String blueprintTemplate,
            @JsonProperty("application") String application, @JsonProperty("component") String component,
            @JsonProperty("serviceIds") Collection<String> serviceIds,
            @JsonProperty("vnfTypes") Collection<String> vnfTypes,
            @JsonProperty("serviceLocations") Collection<String> serviceLocations,
            @JsonProperty("asdcServiceId") String asdcServiceId, @JsonProperty("asdcResourceId") String asdcResourceId,
            @JsonProperty("asdcServiceURL") String asdcServiceURL, @JsonProperty("typeId") String typeId,
            @JsonProperty("selfLink") Link selfLink, @JsonProperty("created") String created,
            @JsonProperty("deactivated") String deactivated, @JsonProperty("canDeploy") Boolean canDeploy) {

        if (owner == null)
            throw new IllegalArgumentException("owner cannot be null");
        if (typeName == null)
            throw new IllegalArgumentException("typeName cannot be null");
        if (typeVersion == null)
            throw new IllegalArgumentException("typeVersion cannot be null");
        if (blueprintTemplate == null)
            throw new IllegalArgumentException("blueprintTemplate cannot be null");

        this.owner = owner;
        this.typeName = typeName;
        this.typeVersion = typeVersion;
        this.blueprintTemplate = blueprintTemplate;
        this.application = application;
        this.component = component;

        this.serviceIds = (serviceIds == null) ? new LinkedList<String>() : serviceIds;
        this.vnfTypes = (vnfTypes == null) ? new LinkedList<String>() : vnfTypes;
        this.serviceLocations = (serviceLocations == null) ? new LinkedList<String>() : serviceLocations;

        this.asdcServiceId = Optional.ofNullable(asdcServiceId);
        this.asdcResourceId = Optional.ofNullable(asdcResourceId);
        this.asdcServiceURL = Optional.ofNullable(asdcServiceURL);
        this.typeId = Optional.ofNullable(typeId);
        this.selfLink = Optional.ofNullable(selfLink);
        this.created = Optional.ofNullable(created);
        this.deactivated = Optional.ofNullable(deactivated);
        this.canDeploy = Optional.of(false);
        try {
            this.blueprintInputs = Blueprint.parse(blueprintTemplate).getInputs();
            this.blueprintDescription = Blueprint.parse(blueprintTemplate).getDescription();
        } catch (BlueprintParseException e) {
            throw new RuntimeException(
                    "Error while parsing blueprint template for " + this.typeName + " " + this.typeVersion, e);
        }
    }

    public String getOwner() {
        return owner;
    }

    public String getTypeName() {
        return typeName;
    }

    public Integer getTypeVersion() {
        return typeVersion;
    }

    public String getBlueprintTemplate() {
        return blueprintTemplate;
    }

    public Collection<String> getServiceIds() {
        return serviceIds;
    }

    public Collection<String> getVnfTypes() {
        return vnfTypes;
    }

    public Collection<String> getServiceLocations() {
        return serviceLocations;
    }

    public Optional<String> getAsdcServiceId() {
        return asdcServiceId;
    }

    public Optional<String> getAsdcResourceId() {
        return asdcResourceId;
    }

    public Optional<String> getAsdcServiceURL() {
        return asdcServiceURL;
    }

    public Optional<String> getTypeId() {
        return typeId;
    }

    public Optional<Link> getSelfLink() {
        return selfLink;
    }

    public Optional<String> getCreated() {
        return created;
    }

    public Optional<String> getDeactivated() {
        return deactivated;
    }

    public Map<String, BlueprintInput> getBlueprintInputs() {
        return blueprintInputs;
    }

    public String getBlueprintDescription() {
        return blueprintDescription;
    }

    public Optional<Boolean> getCanDeploy() {
        return canDeploy;
    }

    public String getApplication() {
        return application;
    }

    public String getComponent() {
        return component;
    }

    public void setCanDeploy(Optional<Boolean> canDeploy) {
        this.canDeploy = canDeploy;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ServiceType))
            return false;

        final ServiceType serviceType = (ServiceType) obj;

        return (serviceType.getAsdcResourceId().equals(getAsdcResourceId())
                && serviceType.getAsdcServiceId().equals(getAsdcServiceId())
                && serviceType.getAsdcServiceURL().equals(getAsdcServiceURL())
                && serviceType.getBlueprintTemplate().equals(getBlueprintTemplate())
                && serviceType.getCreated().equals(getCreated())
                && serviceType.getDeactivated().equals(getDeactivated()) && serviceType.getOwner().equals(getOwner())
                && serviceType.getSelfLink().equals(getSelfLink())
                && serviceType.getServiceIds().equals(getServiceIds())
                && serviceType.getServiceLocations().equals(getServiceLocations())
                && serviceType.getTypeId().equals(getTypeId()) && serviceType.getTypeName().equals(getTypeName())
                && serviceType.getTypeVersion().equals(getTypeVersion())
                && serviceType.getVnfTypes().equals(getVnfTypes())
                && serviceType.getApplication().equals(getApplication())
                && serviceType.getComponent().equals(getComponent()));
    }

    // Used for back end search, only searches the fields displayed in the front
    // end.
    public boolean contains(String searchString) {
        if (StringUtils.containsIgnoreCase(this.getOwner(), searchString)
                || StringUtils.containsIgnoreCase(this.getBlueprintDescription(), searchString)
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
}
