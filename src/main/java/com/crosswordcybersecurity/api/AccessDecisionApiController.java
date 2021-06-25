package com.crosswordcybersecurity.api;

import com.crosswordcybersecurity.model.*;
import com.crosswordcybersecurity.verifier.ApiException;
import com.crosswordcybersecurity.verifier.api.VpVerificationApi;
import com.crosswordcybersecurity.verifier.model.ValidateRequest;
import com.crosswordcybersecurity.verifier.model.ValidateResponse;
import com.crosswordcybersecurity.verifier.model.VerifyVpRequest;
import com.crosswordcybersecurity.verifier.model.W3cVcSkelsList;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.configuration.VerifierConfiguration;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-25T13:57:03.034599+01:00[Europe/London]")
@RestController
public class AccessDecisionApiController implements AccessDecisionApi {

    @Autowired
    private VerifierConfiguration verifierConfiguration;

    private static final Logger log = LoggerFactory.getLogger(AccessDecisionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccessDecisionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<AccessDecisionResponse> decision(@Parameter(in = ParameterIn.DEFAULT, description = "Verifiable Presentation, RP URL, Challenge, policyMatch and Policy Registry URL", required=true, schema=@Schema()) @Valid @RequestBody AccessDecisionRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            boolean granted = true;
            String reasonCode = null;
//            001 the VCs do not belong to the holder (Proof of possession fails);
//            002 the VCs do not match the RP’s policy;
//            003 one or more of the VC Issuer’s are not trusted;
//            004 the challenge in the VP does not match the input challenge;
//            005 incorrect/unknown schema elements in the VP or a VC
            com.crosswordcybersecurity.model.W3cVcSkelsList atts = new com.crosswordcybersecurity.model.W3cVcSkelsList();
            HttpStatus responseCode = HttpStatus.NOT_IMPLEMENTED;

            log.info("## START DECISION ##");
            log.debug("Request: " + body.toString());

            log.info("Loop Over VPs");
            List<Vp> vps = body.getVps();
            Iterator<Vp> itn = vps.iterator();
            while (itn.hasNext()) {
                Vp vp = itn.next();
                String format = vp.getFormat();
                Verifier verifier = verifierConfiguration.getVerifier(format);

                if (verifier != null) {
                    log.info("Verifier: " + verifier.getDescription());
                    log.info("Format: " + verifier.getFormat());

                    // Verification
                    log.info("Call Verifier API");
                    W3cVcSkelsList w3cVcSkelsList = null;

                    com.crosswordcybersecurity.verifier.model.Vp dbVp = new com.crosswordcybersecurity.verifier.model.Vp();
                    dbVp.setFormat(vp.getFormat());
                    dbVp.setPresentation(vp.getPresentation());

                    VerifyVpRequest verifyVpRequest = new VerifyVpRequest();
                    verifyVpRequest.setChallenge(body.getChallenge());
                    verifyVpRequest.setRpurl(body.getRpUrl());
                    verifyVpRequest.setVp(dbVp);

                    String verifierUrl = verifier.getUrl();

                    VpVerificationApi vpVerificationApi = new VpVerificationApi();
                    vpVerificationApi.getApiClient().setBasePath(verifierUrl);

                    try {
                        w3cVcSkelsList = vpVerificationApi.verifyVp(verifyVpRequest);
                        log.debug("W3C VCs: " + w3cVcSkelsList.toString());

                    } catch (ApiException e) {
                        log.error("Error Accessing Verifier: " + verifier.getUrl());
                        log.error(e.getMessage());

                        int code = e.getCode();
                        if (code != 401 && responseCode != HttpStatus.OK) {
                            responseCode = HttpStatus.SERVICE_UNAVAILABLE;
                        }
                        if (code == 401 && reasonCode != null) {
                            reasonCode = "001";
                        }
                        // Try Next VP
                    }

                    ValidateResponse validateResponse = null;
                    if (w3cVcSkelsList != null || w3cVcSkelsList.isEmpty()) {
                        // Validation
                        log.info("Call Internal Policy Matching API");

                        ValidateRequest validateRequest = new ValidateRequest();
                        validateRequest.setPolicyMatch(body.getPolicyMatch());
                        validateRequest.setPolicyRegistryUrl(body.getPolicyRegistryUrl());
                        validateRequest.setVcs(w3cVcSkelsList);

                        com.crosswordcybersecurity.verifier.api.AccessDecisionApi accessDecisionApi = new com.crosswordcybersecurity.verifier.api.AccessDecisionApi();
                        accessDecisionApi.getApiClient().setBasePath(verifierUrl);
                        try {
                            validateResponse = accessDecisionApi.validate(validateRequest);
                            log.debug("Validate Response: " + validateResponse.toString());

                            if (validateResponse != null) {
                                boolean isMatch = validateResponse.isMatched();
                                granted = granted || isMatch;
                                responseCode = HttpStatus.OK;

                                if (isMatch) {
                                    reasonCode = "000";
                                    // Convert received W3C skeletons to SUV format and save in atts.
                                    Iterator<com.crosswordcybersecurity.verifier.model.W3cVc> vcIterator = w3cVcSkelsList.iterator();
                                    W3cVc w3cVc = new W3cVc();
                                    while (vcIterator.hasNext()) {
                                        com.crosswordcybersecurity.verifier.model.W3cVc w3cVcDb = vcIterator.next();
                                        w3cVc.setId(w3cVcDb.getId());
                                        w3cVc.setIssuer(w3cVcDb.getIssuer());
                                        w3cVc.setIssuanceDate(w3cVcDb.getIssuanceDate());
                                        w3cVc.setExpirationDate(w3cVcDb.getExpirationDate());
                                        w3cVc.setAtContext(w3cVcDb.getAtContext());
                                        w3cVc.setType(w3cVcDb.getType());
                                        w3cVc.setCredentialSubject(w3cVcDb.getCredentialSubject());
                                        atts.add(w3cVc);
                                    }
                                } else {
                                    reasonCode = "002";
                                }
                            }

                        } catch (ApiException e) {
                            log.error("Error Accessing Verifier: " + verifier.getUrl());
                            log.error(e.getMessage());
                            int code = e.getCode();
                            if (code == 500 && responseCode != HttpStatus.OK) {
                                responseCode = HttpStatus.SERVICE_UNAVAILABLE;

                            } else if (! reasonCode.equals("000")) {
                                reasonCode = "002";
                            }
                        }

                    } else {
                        log.error("No VCs Returned from Verifier API");
                        // Try next VP
                    }

                } else {
                    log.error("No Verifiers Compatible with this format: " + format);
                    // Try next VP
                }

            } // while

            if (responseCode == HttpStatus.OK) {
                log.error("Prepare Response Object");
                AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
                accessDecisionResponse.setGranted(granted);
                if (granted) {
                    accessDecisionResponse.setAtts(atts);
                } else {
                    accessDecisionResponse.setReasonCode(reasonCode);
                }
                log.debug("Response: " + accessDecisionResponse.toString());
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);

            } else {
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(responseCode);
            }

        }

        return new ResponseEntity<AccessDecisionResponse>(HttpStatus.BAD_REQUEST);
    }

}
