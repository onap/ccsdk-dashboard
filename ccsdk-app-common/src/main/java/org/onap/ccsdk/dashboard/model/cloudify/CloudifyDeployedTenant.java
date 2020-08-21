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

package org.onap.ccsdk.dashboard.model.cloudify;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudifyDeployedTenant extends ECTransportModel {

    /** A unique identifier for the deployment. */
    public final String id;
    /** tenant where the deployment was done */
    public final String tenant_name;
    public final String created_at;
    public final String updated_at;

    @JsonCreator
    public CloudifyDeployedTenant(@JsonProperty("id") String id,
        @JsonProperty("tenant_name") String tenant_name,
        @JsonProperty("created_at") String created_at,
        @JsonProperty("updated_at") String updated_at) {
        this.id = id;
        this.tenant_name = tenant_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
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
        result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tenant_name == null) ? 0 : tenant_name.hashCode());
        result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CloudifyDeployedTenant other = (CloudifyDeployedTenant) obj;
        if (created_at == null) {
            if (other.created_at != null)
                return false;
        } else if (!created_at.equals(other.created_at))
            return false;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (tenant_name == null) {
            if (other.tenant_name != null) {
                return false;
            }
        } else if (!tenant_name.equals(other.tenant_name)) {
            return false;
        }
        if (updated_at == null) {
            if (other.updated_at != null) {
                return false;
            }
        } else if (!updated_at.equals(other.updated_at)) {
            return false;
        }
        return true;
    }

}
