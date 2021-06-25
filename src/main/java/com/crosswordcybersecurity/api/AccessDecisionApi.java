/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.26).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.crosswordcybersecurity.api;

import com.crosswordcybersecurity.model.AccessDecisionRequest;
import com.crosswordcybersecurity.model.AccessDecisionResponse;
import com.crosswordcybersecurity.model.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-25T13:57:03.034599+01:00[Europe/London]")
@Validated
public interface AccessDecisionApi {

    @Operation(summary = "Access Decision for a VP", description = "Request access decision for a VP", tags={ "Access Decision" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Request Access Decision Response Object", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccessDecisionResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "The VP is badly formatted, or a request parameter is missing or badly formed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerResponse.class))),
        
        @ApiResponse(responseCode = "403", description = "The VP audience does not match the RP URL.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerResponse.class))),
        
        @ApiResponse(responseCode = "404", description = "The policy cannot be found in the policy registry, or a Verifier cannot be found to match the VP type.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerResponse.class))),
        
        @ApiResponse(responseCode = "500", description = "Something went wrong with the SUV e.g. database error or other bug.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerResponse.class))),
        
        @ApiResponse(responseCode = "503", description = "If a Verifier or Policy Registry or Internal DB are currently not available.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerResponse.class))) })
    @RequestMapping(value = "/v1/decision",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<AccessDecisionResponse> decision(@Parameter(in = ParameterIn.DEFAULT, description = "Verifiable Presentation, RP URL, and Challenge", required=true, schema=@Schema()) @Valid @RequestBody AccessDecisionRequest body);

}

