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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Compliance with the schema spec'd here:
 * http://docs.getcloudify.org/3.4.0/blueprints/spec-inputs/
 */
@JsonInclude(Include.NON_NULL)
public class BlueprintInput {

    private final BlueprintInput.Type type;
    private final Optional<Object> defaultValue;
    private final Optional<String> description;

    public static enum Type {
        @JsonProperty("any")
        ANY,

        @JsonProperty("string")
        STRING,

        @JsonProperty("integer")
        INTEGER,

        @JsonProperty("boolean")
        BOOLEAN
    }

    @JsonCreator
    public BlueprintInput(@JsonProperty("type") String type,
        @JsonProperty("default") Object defaultValue,
        @JsonProperty("description") String description) {

        // Case where there is no default and no type --> Type should be ANY
        if (defaultValue == null && type == null) {
            this.type = BlueprintInput.Type.ANY;
        }

        // Case where there is a default but no type --> Type should be ANY
        else if (defaultValue != null && type == null) {
            this.type = BlueprintInput.Type.ANY;
        }

        // Case where there is a type but no default --> Type should be the specified
        // type.
        else if (defaultValue == null && type != null) {
            this.type = BlueprintInput.Type.valueOf(type.toString().toUpperCase());
        }

        // Cases where there is a default and a type
        else {
            switch (BlueprintInput.Type.valueOf(type.toString().toUpperCase())) {
                case ANY:
                    throw new IllegalArgumentException(
                        "Cannot specify type ANY (leave blank instead to get ANY type)");
                case BOOLEAN:
                    if (defaultValue != null && !(defaultValue instanceof Boolean)) {
                        throw new IllegalArgumentException(
                            "default value does not match specified type");
                    }
                    this.type = BlueprintInput.Type.BOOLEAN;
                    break;
                case INTEGER:
                    if (defaultValue != null && !(defaultValue instanceof Integer)) {
                        throw new IllegalArgumentException(
                            "default value does not match specified type");
                    }
                    this.type = BlueprintInput.Type.INTEGER;
                    break;
                case STRING:
                    if (defaultValue != null && !(defaultValue instanceof String)) {
                        throw new IllegalArgumentException(
                            "default value does not match specified type");
                    }
                    this.type = BlueprintInput.Type.STRING;
                    break;
                default:
                    this.type = Type.ANY;
                    break;
            }
        }

        this.defaultValue = Optional.ofNullable(defaultValue);
        this.description = Optional.ofNullable(description);
    }

    public BlueprintInput.Type getType() {
        return type;
    }

    @JsonIgnore
    public Optional<Object> getDefault() {
        return defaultValue;
    }

    @JsonProperty("defaultValue")
    public Object getDefaultValue() {
        return defaultValue.orElse(null);
    }

    @JsonIgnore
    public Optional<String> getDescription() {
        return description;
    }

    @JsonProperty("description")
    public String getDescriptionValue() {
        return description.orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BlueprintInput) {
            final BlueprintInput obj = (BlueprintInput) o;

            return obj.getDefaultValue().equals(getDefaultValue())
                && obj.getDescriptionValue().equals(getDescriptionValue())
                && obj.getType().equals(getType());
        }

        return false;
    }

    @Override
    public String toString() {
        return "{" + "type: " + getType() + ",default: " + getDefault() + ",description: "
            + getDescription() + "}";
    }
}
