package io.identiproof.verifier.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.identiproof.verifier.model.W3cVcSkelsList;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ValidateRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-30T19:57:55.440700+01:00[Europe/London]")


public class ValidateRequest   {
  @JsonProperty("policyMatch")
  private Object policyMatch = null;

  @JsonProperty("policyFormat")
  private String policyFormat = null;

  @JsonProperty("policyRegistryUrl")
  private String policyRegistryUrl = null;

  @JsonProperty("vcs")
  private W3cVcSkelsList vcs = null;

  public ValidateRequest policyMatch(Object policyMatch) {
    this.policyMatch = policyMatch;
    return this;
  }

  /**
   * Get policyMatch
   * @return policyMatch
   **/
  @Schema(example = "{\"action\":\"GET\",\"target\":\"https://rp.verifiablecredentials.uk/resource\"}", required = true, description = "")
      @NotNull

    public Object getPolicyMatch() {
    return policyMatch;
  }

  public void setPolicyMatch(Object policyMatch) {
    this.policyMatch = policyMatch;
  }

  public ValidateRequest policyFormat(String policyFormat) {
    this.policyFormat = policyFormat;
    return this;
  }

  /**
   * Get policyFormat
   * @return policyFormat
   **/
  @Schema(example = "Identiproof", description = "")
  
    public String getPolicyFormat() {
    return policyFormat;
  }

  public void setPolicyFormat(String policyFormat) {
    this.policyFormat = policyFormat;
  }

  public ValidateRequest policyRegistryUrl(String policyRegistryUrl) {
    this.policyRegistryUrl = policyRegistryUrl;
    return this;
  }

  /**
   * Get policyRegistryUrl
   * @return policyRegistryUrl
   **/
  @Schema(example = "https://registry.verifiablecredentials.uk", required = true, description = "")
      @NotNull

    public String getPolicyRegistryUrl() {
    return policyRegistryUrl;
  }

  public void setPolicyRegistryUrl(String policyRegistryUrl) {
    this.policyRegistryUrl = policyRegistryUrl;
  }

  public ValidateRequest vcs(W3cVcSkelsList vcs) {
    this.vcs = vcs;
    return this;
  }

  /**
   * Get vcs
   * @return vcs
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public W3cVcSkelsList getVcs() {
    return vcs;
  }

  public void setVcs(W3cVcSkelsList vcs) {
    this.vcs = vcs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValidateRequest validateRequest = (ValidateRequest) o;
    return Objects.equals(this.policyMatch, validateRequest.policyMatch) &&
        Objects.equals(this.policyFormat, validateRequest.policyFormat) &&
        Objects.equals(this.policyRegistryUrl, validateRequest.policyRegistryUrl) &&
        Objects.equals(this.vcs, validateRequest.vcs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policyMatch, policyFormat, policyRegistryUrl, vcs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValidateRequest {\n");
    
    sb.append("    policyMatch: ").append(toIndentedString(policyMatch)).append("\n");
    sb.append("    policyFormat: ").append(toIndentedString(policyFormat)).append("\n");
    sb.append("    policyRegistryUrl: ").append(toIndentedString(policyRegistryUrl)).append("\n");
    sb.append("    vcs: ").append(toIndentedString(vcs)).append("\n");
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
