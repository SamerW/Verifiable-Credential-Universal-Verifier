package io.identiproof.verifier.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * CredentialSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-21T16:58:50.301+01:00[Europe/London]")


public class CredentialSchema   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("type")
  private String type = null;

  public CredentialSchema id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The URL of a JSON Schema to validate the Credential Subject
   * @return id
   **/
  @ApiModelProperty(example = "https://idp.verifiablecredentials.net/schema/certificate.json", required = true, value = "The URL of a JSON Schema to validate the Credential Subject")
  @NotNull

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CredentialSchema type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Type of the Validator
   * @return type
   **/
  @ApiModelProperty(example = "JsonSchemaValidator2018", required = true, value = "Type of the Validator")
  @NotNull

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CredentialSchema credentialSchema = (CredentialSchema) o;
    return Objects.equals(this.id, credentialSchema.id) &&
            Objects.equals(this.type, credentialSchema.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CredentialSchema {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
