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
 * AccessDecisionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-25T13:57:03.034599+01:00[Europe/London]")


public class AccessDecisionRequest   {
  @JsonProperty("vps")
  @Valid
  private List<Vp> vps = null;

  @JsonProperty("rpUrl")
  private String rpUrl = null;

  @JsonProperty("challenge")
  private String challenge = null;

  @JsonProperty("policyRegistryUrl")
  private String policyRegistryUrl = null;

  @JsonProperty("policyMatch")
  private Object policyMatch = null;

  public AccessDecisionRequest vps(List<Vp> vps) {
    this.vps = vps;
    return this;
  }

  public AccessDecisionRequest addVpsItem(Vp vpsItem) {
    if (this.vps == null) {
      this.vps = new ArrayList<Vp>();
    }
    this.vps.add(vpsItem);
    return this;
  }

  /**
   * Get vps
   * @return vps
   **/
  @Schema(example = "[{\"format\":\"jwt_vp\",\"presentation\":\"eyJ1c2VybmFtZSI6ImRnMSIsImF0dHNldCI6W3siQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3d3dy5kdmxhLmdvdi51ay9WQ2NvbnRleHQvdjEiXSwidHlwZSI6WyJWZXJpZmlhYmxlQ3JlZGVudGlhbCIsIkRyaXZpbmdMaWNlbnNlQ3JlZGVudGlhbCJdLCJuYW1lIjoiZGFpbmlzIiwic3VybmFtZSI6ImdyaW5iZXJncyIsImRyaXZpbmdMaWNlbnNlIjp7Im5hbWUiOiJkYWluaXMgZ3JpbmJlcmdzIiwiRE9CIjoiMDEvMDEvMjAwMCIsInZlaGljbGVzIjoiY2FyLCBtb3BlZCwgYmlrZSJ9fSx7IkBjb250ZXh0IjpbImh0dHBzOi8vd3d3LnczLm9yZy8yMDE4L2NyZWRlbnRpYWxzL3YxIiwiaHR0cHM6Ly93d3cua2VudC5hYy51ay9WQ2NvbnRleHQvdjEiXSwidHlwZSI6WyJWZXJpZmlhYmxlQ3JlZGVudGlhbCIsIkRlZ3JlZUNyZWRlbnRpYWwiXSwicHJvcDEiOiJ2YWwxIiwicHJvcDIiOiJ2YWwyIiwiZGVncmVlIjp7InR5cGUiOiJCYWNoZWxvckRlZ3JlZSIsIm5hbWUiOiJCYWNoZWxvciBvZiBTY2llbmNlIGFuZCBBcnRzIn19XSwiYXV0aG5DcmVkcyI6eyJ1c2VybmFtZSI6ImRnMSIsIm90cCI6ImFiY2RlZiJ9fQ==\"},{\"format\":\"ldp_vp\",\"presentation\":{\"@context\":[\"https://www.w3.org/2018/credentials/v1\"],\"type\":[\"VerifiablePresentation\",\"WalletXXX\"],\"verifiableCredential\":[{\"@context\":[\"https://www.w3.org/2018/credentials/v1\",\"https://www.w3.org/2018/credentials/examples/v1\"],\"id\":\"https://example.com/credentials/1872\",\"type\":[\"VerifiableCredential\",\"IDCardCredential\"],\"issuer\":{\"id\":\"did:example:issuer\"},\"issuanceDate\":\"2010-01-01T19:23:24Z\",\"credentialSubject\":{\"given_name\":\"Fredrik\",\"family_name\":\"Strömberg\",\"birthdate\":\"1949-01-22\"},\"proof\":{\"type\":\"Ed25519Signature2018\",\"created\":\"2021-03-19T15:30:15Z\",\"jws\":\"eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..PT8yCqVjj5ZHD0W36zsBQ47oc3El07WGPWaLUuBTOT48IgKI5HDoiFUt9idChT_Zh5s8cF_2cSRWELuD8JQdBw\",\"proofPurpose\":\"assertionMethod\",\"verificationMethod\":\"did:example:issuer#keys-1\"}}],\"id\":\"ebc6f1c2\",\"holder\":\"did:example:holder\",\"proof\":{\"type\":\"Ed25519Signature2018\",\"created\":\"2021-03-19T15:30:15Z\",\"challenge\":\"()&)()0__sdf\",\"jws\":\"yJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..GF5Z6TamgNE8QjE3RbiDOj3n_t25_1K7NVWMUASe_OEzQV63GaKdu235MCS3hIYvepcNdQ_ZOKpGNCf0vIAoDA\",\"proofPurpose\":\"authentication\",\"verificationMethod\":\"did:example:holder#key-1\"}}}]", required = true, description = "")
      @NotNull
    public List<Vp> getVps() {
    return vps;
  }

  public void setVps(List<Vp> vps) {
    this.vps = vps;
  }

  public AccessDecisionRequest rpUrl(String rpUrl) {
    this.rpUrl = rpUrl;
    return this;
  }

  /**
   * The URL for the Relying Party
   * @return rpUrl
   **/
  @Schema(example = "https://rp.verifiablecredentials.uk", required = true, description = "The URL for the Relying Party")
      @NotNull

    public String getRpUrl() {
    return rpUrl;
  }

  public void setRpUrl(String rpUrl) {
    this.rpUrl = rpUrl;
  }

  public AccessDecisionRequest challenge(String challenge) {
    this.challenge = challenge;
    return this;
  }

  /**
   * Get challenge
   * @return challenge
   **/
  @Schema(example = "ABCDEF123456!@#$%", required = true, description = "")
      @NotNull

    public String getChallenge() {
    return challenge;
  }

  public void setChallenge(String challenge) {
    this.challenge = challenge;
  }

  public AccessDecisionRequest policyRegistryUrl(String policyRegistryUrl) {
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

  public AccessDecisionRequest policyMatch(Object policyMatch) {
    this.policyMatch = policyMatch;
    return this;
  }

  /**
   * Get policyMatch
   * @return policyMatch
   **/
  @Schema(example = "{\"type\":\"Over 18\"}", required = true, description = "")
      @NotNull

    public Object getPolicyMatch() {
    return policyMatch;
  }

  public void setPolicyMatch(Object policyMatch) {
    this.policyMatch = policyMatch;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessDecisionRequest accessDecisionRequest = (AccessDecisionRequest) o;
    return Objects.equals(this.vps, accessDecisionRequest.vps) &&
        Objects.equals(this.rpUrl, accessDecisionRequest.rpUrl) &&
        Objects.equals(this.challenge, accessDecisionRequest.challenge) &&
        Objects.equals(this.policyRegistryUrl, accessDecisionRequest.policyRegistryUrl) &&
        Objects.equals(this.policyMatch, accessDecisionRequest.policyMatch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vps, rpUrl, challenge, policyRegistryUrl, policyMatch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessDecisionRequest {\n");
    
    sb.append("    vps: ").append(toIndentedString(vps)).append("\n");
    sb.append("    rpUrl: ").append(toIndentedString(rpUrl)).append("\n");
    sb.append("    challenge: ").append(toIndentedString(challenge)).append("\n");
    sb.append("    policyRegistryUrl: ").append(toIndentedString(policyRegistryUrl)).append("\n");
    sb.append("    policyMatch: ").append(toIndentedString(policyMatch)).append("\n");
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
