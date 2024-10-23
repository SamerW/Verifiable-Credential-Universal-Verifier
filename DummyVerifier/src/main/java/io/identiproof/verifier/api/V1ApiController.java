package io.identiproof.verifier.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.identiproof.verifier.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-30T19:57:55.440700+01:00[Europe/London]")
@RestController
public class V1ApiController implements V1Api {

    private static final Logger log = LoggerFactory.getLogger(V1ApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ValidateResponse> validate(@Parameter(in = ParameterIn.DEFAULT, description = "Policy Match, Policy Registry URL, set of VCs", required=true, schema=@Schema()) @Valid @RequestBody ValidateRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            ValidateResponse response = new ValidateResponse();
            response.setMatched(true);
            response.setReason("Policy Matched.");
            return new ResponseEntity<ValidateResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<ValidateResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<W3cVcSkelsList> verifyVp(@Parameter(in = ParameterIn.DEFAULT, description = "Verifiable Presentation, RP URL, and Challenge", required=true, schema=@Schema()) @Valid @RequestBody VerifyVpRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            Vp vp = body.getVp();
            if (vp != null && vp.getFormat() != null && vp.getPresentation() != null) {
                W3cVcSkelsList w3cVcSkelsList = new W3cVcSkelsList();
                // VP JWT
                if (vp.getFormat().equals("jwt_vp")) {
                    String vpjwt = (String) vp.getPresentation();
                    Map<String, Object> vpPayload = decodeJwtPayload(vpjwt);
                    if (vpPayload.containsKey("vp") && vpPayload.get("vp") != null && Map.class.isAssignableFrom(vpPayload.get("vp").getClass())) {
                        Map<String, Object> hmVp = (Map) vpPayload.get("vp");
                        if (hmVp.containsKey("verifiableCredential") && hmVp.get("verifiableCredential") != null && List.class.isAssignableFrom(hmVp.get("verifiableCredential").getClass())) {
                            List<String> hmVcJwts = (List) hmVp.get("verifiableCredential");
                            for (String sVcJwt : hmVcJwts) {
                                Map<String, Object> vcPayload = decodeJwtPayload(sVcJwt);
                                if (vcPayload.containsKey("vc") && vcPayload.get("vc") != null) {
                                    Map<String, Object> hmVc = (Map) vcPayload.get("vc");
                                    W3cVc w3cVc = new W3cVc();
                                    if (hmVc.containsKey("@context") &&
                                            hmVc.get("@context") != null &&
                                            List.class.isAssignableFrom(hmVc.get("@context").getClass())) {
                                        w3cVc.setAtContext((List<String>) hmVc.get("@context"));
                                    }
                                    if (hmVc.containsKey("type") &&
                                            hmVc.get("type") != null &&
                                            List.class.isAssignableFrom(hmVc.get("type").getClass())) {
                                        w3cVc.setType((List<String>) hmVc.get("type"));
                                    }
                                    if (hmVc.containsKey("credentialSubject") &&
                                            hmVc.get("credentialSubject") != null &&
                                            Map.class.isAssignableFrom(hmVc.get("credentialSubject").getClass())) {

                                        Map<String, Object> hmCredentialSubject = (Map<String, Object>) hmVc.get("credentialSubject");

                                        if (vcPayload.containsKey("sub") &&
                                                vcPayload.get("sub") != null &&
                                                String.class.isAssignableFrom(vcPayload.get("sub").getClass())
                                        ) {
                                            hmCredentialSubject.put("id", vcPayload.get("sub"));
                                        }

                                        w3cVc.setCredentialSubject(hmCredentialSubject);
                                    }
                                    if (hmVc.containsKey("termsOfUse") &&
                                            hmVc.get("termsOfUse") != null &&
                                            List.class.isAssignableFrom(hmVc.get("termsOfUse").getClass())) {
                                        List<TermOfUse> termsOfUse = new ArrayList<>();
                                        List<Map<String, Object>> alTermsOfUse = (List) hmVc.get("termsOfUse");
                                        for (Map<String, Object> hmTermOfUse : alTermsOfUse) {
                                            TermOfUse termOfUse = new TermOfUse();
                                            if (hmTermOfUse.containsKey("id") &&
                                                    hmTermOfUse.get("id") != null &&
                                                    String.class.isAssignableFrom(hmTermOfUse.get("id").getClass())
                                            ) {
                                                termOfUse.setId((String) hmTermOfUse.get("id"));
                                            } else {
                                                break;
                                            }
                                            if (hmTermOfUse.containsKey("type") &&
                                                    hmTermOfUse.get("type") != null &&
                                                    String.class.isAssignableFrom(hmTermOfUse.get("type").getClass())
                                            ) {
                                                termOfUse.setType((String) hmTermOfUse.get("type"));
                                            } else {
                                                break;
                                            }
                                            if (hmTermOfUse.containsKey("trustScheme") &&
                                                    hmTermOfUse.get("trustScheme") != null &&
                                                    List.class.isAssignableFrom(hmTermOfUse.get("trustScheme").getClass())
                                            ) {
                                                termOfUse.setTrustScheme((List<String>) hmTermOfUse.get("trustScheme"));
                                            } else {
                                                break;
                                            }
                                            termsOfUse.add(termOfUse);
                                        }
                                        w3cVc.setTermsOfUse(termsOfUse);
                                    }

                                    if (hmVc.containsKey("credentialSchema") &&
                                            Map.class.isAssignableFrom(hmVc.get("credentialSchema").getClass())) {
                                        Map<String, String> hmCredentialSchema = (Map) hmVc.get("credentialSchema");
                                        CredentialSchema credentialSchema = new CredentialSchema();
                                        boolean credSchemaFail = false;
                                        if (hmCredentialSchema.containsKey("id") &&
                                                hmCredentialSchema.get("id") != null &&
                                                String.class.isAssignableFrom(hmCredentialSchema.get("id").getClass())
                                        ) {
                                            credentialSchema.setId((String) hmCredentialSchema.get("id"));
                                        } else {
                                            credSchemaFail = true;
                                        }
                                        if (hmCredentialSchema.containsKey("type") &&
                                                hmCredentialSchema.get("type") != null &&
                                                String.class.isAssignableFrom(hmCredentialSchema.get("type").getClass())
                                        ) {
                                            credentialSchema.setType((String) hmCredentialSchema.get("type"));
                                        } else {
                                            credSchemaFail = true;
                                        }
                                        if (!credSchemaFail) {
                                            w3cVc.setCredentialSchema(credentialSchema);
                                        }
                                    }

                                    if (vcPayload.containsKey("jti") &&
                                            vcPayload.get("jti") != null &&
                                            String.class.isAssignableFrom(vcPayload.get("jti").getClass())) {
                                        w3cVc.setId((String) vcPayload.get("jti"));
                                    }

                                    if (vcPayload.containsKey("iss") &&
                                            vcPayload.get("iss") != null &&
                                            String.class.isAssignableFrom(vcPayload.get("iss").getClass())) {
                                        w3cVc.setIssuer((String) vcPayload.get("iss"));
                                    }

                                    if (vcPayload.containsKey("iat") &&
                                            vcPayload.get("iat") != null &&
                                            Integer.class.isAssignableFrom(vcPayload.get("iat").getClass())) {
                                        Long iat = new Long(vcPayload.get("iat").toString()) * 1000;
                                        java.sql.Timestamp tsIat = new java.sql.Timestamp(iat);
                                        Instant iIat = tsIat.toInstant();
                                        w3cVc.setIssuanceDate(iIat.toString());
                                    }

                                    if (vcPayload.containsKey("exp") &&
                                            vcPayload.get("exp") != null &&
                                            Integer.class.isAssignableFrom(vcPayload.get("exp").getClass())) {
                                        Long exp = new Long(vcPayload.get("exp").toString()) * 1000;
                                        java.sql.Timestamp tsExp = new java.sql.Timestamp(exp);
                                        Instant iExp = tsExp.toInstant();
                                        w3cVc.setExpirationDate(iExp.toString());
                                    }

                                    w3cVcSkelsList.add(w3cVc);
                                }
                            } // for
                        } // if
                    } // VP JWT

                    // VP JSON-LD
                } else if (vp.getFormat().equals("ldp_vp")) {
                    Map<String, Object> hmVp = (Map) vp.getPresentation();
                    if (hmVp.containsKey("verifiableCredential") && hmVp.get("verifiableCredential") != null && List.class.isAssignableFrom(hmVp.get("verifiableCredential").getClass())) {
                        List<Map> hmVcList = (List) hmVp.get("verifiableCredential");
                        for (Map hmVc : hmVcList) {
                            W3cVc w3cVc = new W3cVc();
                            if (hmVc.containsKey("@context") &&
                                    hmVc.get("@context") != null &&
                                    List.class.isAssignableFrom(hmVc.get("@context").getClass())) {
                                w3cVc.setAtContext((List<String>) hmVc.get("@context"));
                            }
                            if (hmVc.containsKey("type") &&
                                    hmVc.get("type") != null &&
                                    List.class.isAssignableFrom(hmVc.get("type").getClass())) {
                                w3cVc.setType((List<String>) hmVc.get("type"));
                            }
                            if (hmVc.containsKey("credentialSubject") &&
                                    hmVc.get("credentialSubject") != null &&
                                    Map.class.isAssignableFrom(hmVc.get("credentialSubject").getClass())) {
                                w3cVc.setCredentialSubject((Map<String, Object>) hmVc.get("credentialSubject"));
                            }
                            if (hmVc.containsKey("termsOfUse") &&
                                    hmVc.get("termsOfUse") != null &&
                                    List.class.isAssignableFrom(hmVc.get("termsOfUse").getClass())) {
                                List<TermOfUse> termsOfUse = new ArrayList<>();
                                List<Map<String, Object>> alTermsOfUse = (List) hmVc.get("termsOfUse");
                                for (Map<String, Object> hmTermOfUse : alTermsOfUse) {
                                    TermOfUse termOfUse = new TermOfUse();
                                    if (hmTermOfUse.containsKey("id") &&
                                            hmTermOfUse.get("id") != null &&
                                            String.class.isAssignableFrom(hmTermOfUse.get("id").getClass())
                                    ) {
                                        termOfUse.setId((String) hmTermOfUse.get("id"));
                                    } else {
                                        break;
                                    }
                                    if (hmTermOfUse.containsKey("type") &&
                                            hmTermOfUse.get("type") != null &&
                                            String.class.isAssignableFrom(hmTermOfUse.get("type").getClass())
                                    ) {
                                        termOfUse.setType((String) hmTermOfUse.get("type"));
                                    } else {
                                        break;
                                    }
                                    if (hmTermOfUse.containsKey("trustScheme") &&
                                            hmTermOfUse.get("trustScheme") != null &&
                                            List.class.isAssignableFrom(hmTermOfUse.get("trustScheme").getClass())
                                    ) {
                                        termOfUse.setTrustScheme((List<String>) hmTermOfUse.get("trustScheme"));
                                    } else {
                                        break;
                                    }
                                    termsOfUse.add(termOfUse);
                                }
                                w3cVc.setTermsOfUse(termsOfUse);
                            }

                            if (hmVc.containsKey("credentialSchema") &&
                                    Map.class.isAssignableFrom(hmVc.get("credentialSchema").getClass())) {
                                Map<String, String> hmCredentialSchema = (Map) hmVc.get("credentialSchema");
                                CredentialSchema credentialSchema = new CredentialSchema();
                                boolean credSchemaFail = false;
                                if (hmCredentialSchema.containsKey("id") &&
                                        hmCredentialSchema.get("id") != null &&
                                        String.class.isAssignableFrom(hmCredentialSchema.get("id").getClass())
                                ) {
                                    credentialSchema.setId((String) hmCredentialSchema.get("id"));
                                } else {
                                    credSchemaFail = true;
                                }
                                if (hmCredentialSchema.containsKey("type") &&
                                        hmCredentialSchema.get("type") != null &&
                                        String.class.isAssignableFrom(hmCredentialSchema.get("type").getClass())
                                ) {
                                    credentialSchema.setType((String) hmCredentialSchema.get("type"));
                                } else {
                                    credSchemaFail = true;
                                }
                                if (!credSchemaFail) {
                                    w3cVc.setCredentialSchema(credentialSchema);
                                }
                            }

                            if (hmVc.containsKey("id") &&
                                    hmVc.get("id") != null &&
                                    String.class.isAssignableFrom(hmVc.get("id").getClass())) {
                                w3cVc.setId((String) hmVc.get("id"));
                            }

                            if (hmVc.containsKey("issuer") &&
                                    hmVc.get("issuer") != null &&
                                    String.class.isAssignableFrom(hmVc.get("issuer").getClass())) {
                                w3cVc.setIssuer((String) hmVc.get("issuer"));
                            }

                            if (hmVc.containsKey("issuanceDate") &&
                                    hmVc.get("issuanceDate") != null &&
                                    String.class.isAssignableFrom(hmVc.get("issuanceDate").getClass())) {
                                w3cVc.setIssuanceDate((String) hmVc.get("issuanceDate"));
                            }

                            if (hmVc.containsKey("expirationDate") &&
                                    hmVc.get("expirationDate") != null &&
                                    String.class.isAssignableFrom(hmVc.get("expirationDate").getClass())) {
                                w3cVc.setExpirationDate((String) hmVc.get("expirationDate"));
                            }

                            w3cVcSkelsList.add(w3cVc);
                        } // for
                    } // if
                } // VP JSON-LD

                String sW3cVcSkelsList;
                try {
                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    sW3cVcSkelsList = objectMapper.writeValueAsString(w3cVcSkelsList);
                } catch (IOException e) {
                    sW3cVcSkelsList = w3cVcSkelsList.toString();
                }
                log.info("Sent Message: " + sW3cVcSkelsList);

                return new ResponseEntity<W3cVcSkelsList>(w3cVcSkelsList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<W3cVcSkelsList>(HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> decodeJwtPayload (String jwt) {
        String[] vpParts = jwt.split("\\.");
        if (vpParts.length == 3) {
            try {
                String sPayload = new String ( Base64.decodeBase64(vpParts[1]) );
                Map<String, Object> hmPayload = objectMapper.readValue(sPayload, Map.class);
                return hmPayload;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

}


