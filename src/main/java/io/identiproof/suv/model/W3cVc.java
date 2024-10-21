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

import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A W3C VC Skeleton object must contain an id, a @context list, a type list, and a set of subject properties. cardImage is an optional field containing an  URL for the card icon to be presented on the wallet app.
 */
@Schema(description = "A W3C VC Skeleton object must contain an id, a @context list, a type list, and a set of subject properties. cardImage is an optional field containing an  URL for the card icon to be presented on the wallet app.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-30T17:10:13.835711+01:00[Europe/London]")


public class W3cVc {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("issuer")
  private String issuer = null;

  @JsonProperty("issuanceDate")
  private String issuanceDate = null;

  @JsonProperty("expirationDate")
  private String expirationDate = null;

  @JsonProperty("@context")
  @Valid
  private List<String> _atContext = new ArrayList<String>();

  @JsonProperty("type")
  @Valid
  private List<String> type = new ArrayList<String>();

  @JsonProperty("credentialSubject")
  private Object credentialSubject = null;

  @JsonProperty("termsOfUse")
  @Valid
  private List<TermOfUse> termsOfUse = null;

  @JsonProperty("credentialSchema")
  private Map<String, Object> credentialSchema = null;

  public W3cVc id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public W3cVc issuer(String issuer) {
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

  public W3cVc issuanceDate(String issuanceDate) {
    this.issuanceDate = issuanceDate;
    return this;
  }

  /**
   * Get issuanceDate
   * @return issuanceDate
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getIssuanceDate() {
    return issuanceDate;
  }

  public void setIssuanceDate(String issuanceDate) {
    this.issuanceDate = issuanceDate;
  }

  public W3cVc expirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  /**
   * Get expirationDate
   * @return expirationDate
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public W3cVc _atContext(List<String> _atContext) {
    this._atContext = _atContext;
    return this;
  }

  public W3cVc addAtContextItem(String _atContextItem) {
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

  public W3cVc type(List<String> type) {
    this.type = type;
    return this;
  }

  public W3cVc addTypeItem(String typeItem) {
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

  public W3cVc credentialSubject(Object credentialSubject) {
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

  public W3cVc termsOfUse(List<TermOfUse> termsOfUse) {
    this.termsOfUse = termsOfUse;
    return this;
  }

  public W3cVc addTermsOfUseItem(TermOfUse termsOfUseItem) {
    if (this.termsOfUse == null) {
      this.termsOfUse = new ArrayList<TermOfUse>();
    }
    this.termsOfUse.add(termsOfUseItem);
    return this;
  }

  /**
   * Get termsOfUse
   * @return termsOfUse
   **/
  @Schema(description = "")
      @Valid
    public List<TermOfUse> getTermsOfUse() {
    return termsOfUse;
  }

  public void setTermsOfUse(List<TermOfUse> termsOfUse) {
    this.termsOfUse = termsOfUse;
  }

  public W3cVc credentialSchema(Map<String, Object> credentialSchema) {
    this.credentialSchema = credentialSchema;
    return this;
  }

  /**
   * Get credentialSchema
   * @return credentialSchema
   **/
  @Schema(description = "")
  
    @Valid
    public Map<String, Object> getCredentialSchema() {
    return credentialSchema;
  }

  public void setCredentialSchema(Map<String, Object> credentialSchema) {
    this.credentialSchema = credentialSchema;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    W3cVc w3cVc = (W3cVc) o;
    return Objects.equals(this.id, w3cVc.id) &&
        Objects.equals(this.issuer, w3cVc.issuer) &&
        Objects.equals(this.issuanceDate, w3cVc.issuanceDate) &&
        Objects.equals(this.expirationDate, w3cVc.expirationDate) &&
        Objects.equals(this._atContext, w3cVc._atContext) &&
        Objects.equals(this.type, w3cVc.type) &&
        Objects.equals(this.credentialSubject, w3cVc.credentialSubject) &&
        Objects.equals(this.termsOfUse, w3cVc.termsOfUse) &&
        Objects.equals(this.credentialSchema, w3cVc.credentialSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, issuer, issuanceDate, expirationDate, _atContext, type, credentialSubject, termsOfUse, credentialSchema);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class W3cVc {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    issuer: ").append(toIndentedString(issuer)).append("\n");
    sb.append("    issuanceDate: ").append(toIndentedString(issuanceDate)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    _atContext: ").append(toIndentedString(_atContext)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    credentialSubject: ").append(toIndentedString(credentialSubject)).append("\n");
    sb.append("    termsOfUse: ").append(toIndentedString(termsOfUse)).append("\n");
    sb.append("    credentialSchema: ").append(toIndentedString(credentialSchema)).append("\n");
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
