package io.identiproof.verifier.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ValidateResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-30T19:57:55.440700+01:00[Europe/London]")


public class ValidateResponse   {
  @JsonProperty("matched")
  private Boolean matched = null;

  @JsonProperty("reason")
  private String reason = null;

  public ValidateResponse matched(Boolean matched) {
    this.matched = matched;
    return this;
  }

  /**
   * Get matched
   * @return matched
   **/
  @Schema(example = "true", required = true, description = "")
      @NotNull

    public Boolean isMatched() {
    return matched;
  }

  public void setMatched(Boolean matched) {
    this.matched = matched;
  }

  public ValidateResponse reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
   **/
  @Schema(example = "Policy Match failed", description = "")
  
    public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValidateResponse validateResponse = (ValidateResponse) o;
    return Objects.equals(this.matched, validateResponse.matched) &&
        Objects.equals(this.reason, validateResponse.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matched, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValidateResponse {\n");
    
    sb.append("    matched: ").append(toIndentedString(matched)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
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
