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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlueprintResponse {

    public BlueprintResponse() {
    }

    /** Name of the ServiceType */
    private String typeName;

    /** Version number for this ServiceType */
    private Integer typeVersion;

    /** Unique identifier for this ServiceType */
    private String typeId;

    @JsonCreator
    public BlueprintResponse(@JsonProperty("typeName") String typeName,
        @JsonProperty("typeVersion") Integer typeVersion, @JsonProperty("typeId") String typeId) {

        this.typeName = typeName;
        this.typeVersion = typeVersion;
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public Integer getTypeVersion() {
        return typeVersion;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTypeVersion(Integer typeVersion) {
        this.typeVersion = typeVersion;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "BlueprintResponse [typeName=" + typeName + ", typeVersion=" + typeVersion
            + ", typeId=" + typeId + "]";
    }
}
