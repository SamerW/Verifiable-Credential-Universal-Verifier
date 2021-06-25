// Copyright (2021) Verifiable Credentials Ltd
package com.crosswordcybersecurity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A VC Skeleton object must contain an id, a @context list, a type list, and a set of subject properties. cardImage is an optional field containing an  URL for the card icon to be presented on the wallet app.
 */
@ApiModel(description = "A VC Skeleton object must contain an id, a @context list, a type list, and a set of subject properties. cardImage is an optional field containing an  URL for the card icon to be presented on the wallet app.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-15T14:19:17.745Z[Europe/London]")


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

  public W3cVc id(String id) {
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

  public W3cVc issuer(String issuer) {
    this.issuer = issuer;
    return this;
  }

  /**
   * Get issuer
   * @return issuer
   **/
  @ApiModelProperty(required = true, value = "")
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
  @ApiModelProperty(required = true, value = "")
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
  @ApiModelProperty(required = true, value = "")
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
  @ApiModelProperty(required = true, value = "")
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
  @ApiModelProperty(required = true, value = "")
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
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Object getCredentialSubject() {
    return credentialSubject;
  }

  public void setCredentialSubject(Object credentialSubject) {
    this.credentialSubject = credentialSubject;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    W3cVc vcObject = (W3cVc) o;
    return Objects.equals(this.id, vcObject.id) &&
        Objects.equals(this.issuer, vcObject.issuer) &&
        Objects.equals(this.issuanceDate, vcObject.issuanceDate) &&
        Objects.equals(this.expirationDate, vcObject.expirationDate) &&
        Objects.equals(this._atContext, vcObject._atContext) &&
        Objects.equals(this.type, vcObject.type) &&
        Objects.equals(this.credentialSubject, vcObject.credentialSubject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, issuer, issuanceDate, expirationDate, _atContext, type, credentialSubject);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VcObject {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    issuer: ").append(toIndentedString(issuer)).append("\n");
    sb.append("    issuanceDate: ").append(toIndentedString(issuanceDate)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    @context: ").append(toIndentedString(_atContext)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    credentialSubject: ").append(toIndentedString(credentialSubject)).append("\n");
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
