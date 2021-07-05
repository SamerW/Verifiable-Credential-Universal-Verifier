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

package io.identiproof.suv.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A VC Skeleton object must contain an id, a @context list, a type list, and a set of subject properties. cardImage is an optional field containing an  URL for the card icon to be presented on the wallet app.
 */
@Schema(description = "A VC Skeleton object must contain an id, a @context list, a type list, and a set of subject properties. cardImage is an optional field containing an  URL for the card icon to be presented on the wallet app.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-25T13:57:03.034599+01:00[Europe/London]")


public class VcObject   {
  @JsonProperty("@context")
  @Valid
  private List<String> _atContext = new ArrayList<String>();

  @JsonProperty("type")
  @Valid
  private List<String> type = new ArrayList<String>();

  @JsonProperty("credentialSubject")
  private Object credentialSubject = null;

  @JsonProperty("issuer")
  private String issuer = null;

  public VcObject _atContext(List<String> _atContext) {
    this._atContext = _atContext;
    return this;
  }

  public VcObject addAtContextItem(String _atContextItem) {
    this._atContext.add(_atContextItem);
    return this;
  }

  /**
   * Get _atContext
   * @return _atContext
   **/
  @Schema(required = true, description = "")
      @NotNull

    public List<String> getAtContext() {
    return _atContext;
  }

  public void setAtContext(List<String> _atContext) {
    this._atContext = _atContext;
  }

  public VcObject type(List<String> type) {
    this.type = type;
    return this;
  }

  public VcObject addTypeItem(String typeItem) {
    this.type.add(typeItem);
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(required = true, description = "")
      @NotNull

    public List<String> getType() {
    return type;
  }

  public void setType(List<String> type) {
    this.type = type;
  }

  public VcObject credentialSubject(Object credentialSubject) {
    this.credentialSubject = credentialSubject;
    return this;
  }

  /**
   * Get credentialSubject
   * @return credentialSubject
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Object getCredentialSubject() {
    return credentialSubject;
  }

  public void setCredentialSubject(Object credentialSubject) {
    this.credentialSubject = credentialSubject;
  }

  public VcObject issuer(String issuer) {
    this.issuer = issuer;
    return this;
  }

  /**
   * Get issuer
   * @return issuer
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VcObject vcObject = (VcObject) o;
    return Objects.equals(this._atContext, vcObject._atContext) &&
        Objects.equals(this.type, vcObject.type) &&
        Objects.equals(this.credentialSubject, vcObject.credentialSubject) &&
        Objects.equals(this.issuer, vcObject.issuer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_atContext, type, credentialSubject, issuer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VcObject {\n");
    
    sb.append("    _atContext: ").append(toIndentedString(_atContext)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    credentialSubject: ").append(toIndentedString(credentialSubject)).append("\n");
    sb.append("    issuer: ").append(toIndentedString(issuer)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
