package io.identiproof.suv.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AccessDecisionResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-25T13:57:03.034599+01:00[Europe/London]")


public class AccessDecisionResponse   {
  @JsonProperty("granted")
  private Boolean granted = null;

  @JsonProperty("atts")
  private W3cVcSkelsList atts = null;

  @JsonProperty("reasonCode")
  private String reasonCode = null;

  public AccessDecisionResponse granted(Boolean granted) {
    this.granted = granted;
    return this;
  }

  /**
   * Get granted
   * @return granted
   **/
  @Schema(example = "true", required = true, description = "")
      @NotNull

    public Boolean isGranted() {
    return granted;
  }

  public void setGranted(Boolean granted) {
    this.granted = granted;
  }

  public AccessDecisionResponse atts(W3cVcSkelsList atts) {
    this.atts = atts;
    return this;
  }

  /**
   * Get atts
   * @return atts
   **/
  @Schema(description = "")

  public W3cVcSkelsList getAtts() {
    return atts;
  }

  public void setAtts(W3cVcSkelsList atts) {
    this.atts = atts;
  }

  public AccessDecisionResponse reasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
    return this;
  }

  /**
   * Get reasonCode
   * @return reasonCode
   **/
  @Schema(example = "true", required = true, description = "")
      @Valid

  public String getReasonCode() {
    return reasonCode;
  }

  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessDecisionResponse accessDecisionResponse = (AccessDecisionResponse) o;
    return Objects.equals(this.granted, accessDecisionResponse.granted) &&
        Objects.equals(this.atts, accessDecisionResponse.atts) &&
        Objects.equals(this.reasonCode, accessDecisionResponse.reasonCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(granted, atts, reasonCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessDecisionResponse {\n");
    
    sb.append("    granted: ").append(toIndentedString(granted)).append("\n");
    sb.append("    atts: ").append(toIndentedString(atts)).append("\n");
    sb.append("    reasonCode: ").append(toIndentedString(reasonCode)).append("\n");
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
