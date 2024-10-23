//    Copyright (2021) Crossword Cybersecurity Plc
//
//    This file is part of the Simple Universal Verifier (SUV). 
// 
//    SUV is licensed free of charge. You can redistribute it and/or modify it under the 
//    terms of the GNU Lesser General Public License as published by the Free 
//    Software Foundation, either version 3 of the License, or (at your 
//    option) any later version. 
// 
//    SUV is distributed in the hope that it will be useful,but WITHOUT ANY 
//    WARRANTY; without even the implied warranty of MERCHANTABILITY or 
//    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
//    License for more details. 
// 
//    You should have received a copy of the GNU Lesser General Public License 
//    along with SUV.  If not, see <https://www.gnu.org/licenses/>. 

// Copyright (2021) Verifiable Credentials Ltd
package io.identiproof.suv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Term of Use
 */
@ApiModel(description = "Term of Use")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-15T14:19:17.745Z[Europe/London]")


public class TermOfUse {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("trustScheme")
    private List<String> trustScheme = null;

    public TermOfUse id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TermOfUse type(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     * @return type
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TermOfUse trustScheme(List<String> trustScheme) {
        this.trustScheme = trustScheme;
        return this;
    }

    /**
     * Get trustScheme
     * @return trustScheme
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public List<String> getTrustScheme() {
        return trustScheme;
    }

    public void setTrustScheme(List<String> trustScheme) {
        this.trustScheme = trustScheme;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TermOfUse vcObject = (TermOfUse) o;
        return Objects.equals(this.type, vcObject.type) &&
                Objects.equals(this.trustScheme, vcObject.trustScheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, trustScheme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VcObject {\n");

        sb.append("    id: ").append(toIndentedString(type)).append("\n");
        sb.append("    issuer: ").append(toIndentedString(trustScheme)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
