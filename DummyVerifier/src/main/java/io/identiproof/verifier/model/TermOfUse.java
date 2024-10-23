package io.identiproof.verifier.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TermOfUse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-30T19:57:55.440700+01:00[Europe/London]")


public class TermOfUse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("trustScheme")
  @Valid
  private List<String> trustScheme = new ArrayList<String>();

  public TermOfUse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "https://essif.trust-scheme.de", description = "")
  
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
  @Schema(example = "https://train.trust-scheme.de/info/", required = true, description = "")
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

  public TermOfUse addTrustSchemeItem(String trustSchemeItem) {
    this.trustScheme.add(trustSchemeItem);
    return this;
  }

  /**
   * Get trustScheme
   * @return trustScheme
   **/
  @Schema(example = "[\"<member of trustscheme1>\",\"<member of trustscheme2>\"]", required = true, description = "")
      @NotNull

    public List<String> getTrustScheme() {
    return trustScheme;
  }

  public void setTrustScheme(List<String> trustScheme) {
    this.trustScheme = trustScheme;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TermOfUse termOfUse = (TermOfUse) o;
    return Objects.equals(this.id, termOfUse.id) &&
        Objects.equals(this.type, termOfUse.type) &&
        Objects.equals(this.trustScheme, termOfUse.trustScheme);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, trustScheme);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TermOfUse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    trustScheme: ").append(toIndentedString(trustScheme)).append("\n");
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
