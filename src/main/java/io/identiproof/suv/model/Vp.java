//    This file is part of the Simple Universal Verifier (SUV). 
// 
//    SUV is free software: you can redistribute it and/or modify it under the 
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
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * Vp
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-25T13:57:03.034599+01:00[Europe/London]")


public class Vp   {
  @JsonProperty("format")
  private String format = null;

  @JsonProperty("presentation")
  private Object presentation = null;

  public Vp format(String format) {
    this.format = format;
    return this;
  }

  /**
   * Get format
   * @return format
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Vp presentation(Object presentation) {
    this.presentation = presentation;
    return this;
  }

  /**
   * Get presentation
   * @return presentation
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Object getPresentation() {
    return presentation;
  }

  public void setPresentation(Object presentation) {
    this.presentation = presentation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vp vp = (Vp) o;
    return Objects.equals(this.format, vp.format) &&
        Objects.equals(this.presentation, vp.presentation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(format, presentation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vp {\n");
    
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    presentation: ").append(toIndentedString(presentation)).append("\n");
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
