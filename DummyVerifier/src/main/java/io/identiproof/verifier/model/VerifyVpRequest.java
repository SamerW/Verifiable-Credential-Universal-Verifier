package io.identiproof.verifier.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.identiproof.verifier.model.Vp;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VerifyVpRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-30T19:57:55.440700+01:00[Europe/London]")


public class VerifyVpRequest   {
  @JsonProperty("vp")
  private Vp vp = null;

  @JsonProperty("rpurl")
  private String rpurl = null;

  @JsonProperty("challenge")
  private String challenge = null;

  public VerifyVpRequest vp(Vp vp) {
    this.vp = vp;
    return this;
  }

  /**
   * Get vp
   * @return vp
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Vp getVp() {
    return vp;
  }

  public void setVp(Vp vp) {
    this.vp = vp;
  }

  public VerifyVpRequest rpurl(String rpurl) {
    this.rpurl = rpurl;
    return this;
  }

  /**
   * Get rpurl
   * @return rpurl
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getRpurl() {
    return rpurl;
  }

  public void setRpurl(String rpurl) {
    this.rpurl = rpurl;
  }

  public VerifyVpRequest challenge(String challenge) {
    this.challenge = challenge;
    return this;
  }

  /**
   * Get challenge
   * @return challenge
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getChallenge() {
    return challenge;
  }

  public void setChallenge(String challenge) {
    this.challenge = challenge;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VerifyVpRequest verifyVpRequest = (VerifyVpRequest) o;
    return Objects.equals(this.vp, verifyVpRequest.vp) &&
        Objects.equals(this.rpurl, verifyVpRequest.rpurl) &&
        Objects.equals(this.challenge, verifyVpRequest.challenge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vp, rpurl, challenge);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VerifyVpRequest {\n");
    
    sb.append("    vp: ").append(toIndentedString(vp)).append("\n");
    sb.append("    rpurl: ").append(toIndentedString(rpurl)).append("\n");
    sb.append("    challenge: ").append(toIndentedString(challenge)).append("\n");
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
