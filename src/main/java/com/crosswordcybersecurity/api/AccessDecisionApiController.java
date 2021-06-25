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
import java.util.*;

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

            log.info("## START DECISION ##");
            log.debug("Request: " + body.toString());

            log.info("* Read input parameters");
            String challenge = body.getChallenge();
            String rpUrl = body.getRpUrl();
            List<Vp> vps = body.getVps();
            Object policyMatch = body.getPolicyMatch();
            String policyRegistryUrl = body.getPolicyRegistryUrl();

            log.info("* VPs Verification");
            Map<Vp, W3cVcSkelsList> verifiedVps = new HashMap<Vp, W3cVcSkelsList>(); // VPs that passed verification

            // Set response variables
            HttpStatus lastCode = HttpStatus.NOT_IMPLEMENTED;
            String lastReason = null;
//            001 the VCs do not belong to the holder (Proof of possession fails);
//            002 the VCs do not match the RP’s policy;
//            003 one or more of the VC Issuer’s are not trusted;
//            004 the challenge in the VP does not match the input challenge;
//            005 incorrect/unknown schema elements in the VP or a VC

            log.info("Loop Over VPs");
            Iterator<Vp> itn = vps.iterator();
            while (itn.hasNext()) {
                Vp vp = itn.next();
                String format = vp.getFormat();
                Verifier verifier = verifierConfiguration.getVerifier(format);

                if (verifier != null) {
                    log.info("Verifier: " + verifier.getDescription());
                    log.info("Format: " + verifier.getFormat());

                    String verifierUrl = verifier.getUrl();

                    // Verification
                    log.info("Call Verifier API");
                    W3cVcSkelsList w3cVcSkelsList = null;

                    com.crosswordcybersecurity.verifier.model.Vp dbVp = new com.crosswordcybersecurity.verifier.model.Vp();
                    dbVp.setFormat(vp.getFormat());
                    dbVp.setPresentation(vp.getPresentation());

                    VerifyVpRequest verifyVpRequest = new VerifyVpRequest();
                    verifyVpRequest.setChallenge(challenge);
                    verifyVpRequest.setRpurl(rpUrl);
                    verifyVpRequest.setVp(dbVp);

                    VpVerificationApi vpVerificationApi = new VpVerificationApi();
                    vpVerificationApi.getApiClient().setBasePath(verifierUrl);

                    try {
                        w3cVcSkelsList = vpVerificationApi.verifyVp(verifyVpRequest);
                        log.debug("W3C VCs: " + w3cVcSkelsList.toString());

                        if (w3cVcSkelsList != null && !w3cVcSkelsList.isEmpty()) {
                            verifiedVps.put(vp, w3cVcSkelsList);
                            lastCode = HttpStatus.OK;
                            lastReason = "000";

                        } else {
                            if (lastCode != HttpStatus.OK) {
                                lastCode = HttpStatus.BAD_REQUEST;
                            }
                        }

                    } catch (ApiException e) {
                        log.error("Error Accessing Verifier: " + verifier.getUrl());
                        log.error(e.getMessage());

                        int code = e.getCode();
                        if (code != 401 && lastCode != HttpStatus.OK) {
                            lastCode = HttpStatus.SERVICE_UNAVAILABLE;
                        }
                        if (code == 401 && !lastReason.equals("000")) {
                            lastReason = "001";
                        }
                        // Try Next VP
                    }
                } // if verifier is known
            } // while

            // If none of the VP verification succeeds, exit.
            if (! lastReason.equals("000")) {
                AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
                accessDecisionResponse.setReasonCode(lastReason);
                accessDecisionResponse.setGranted(false);
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);
            }
            if (lastCode != HttpStatus.OK) {
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(lastCode);
            }

            log.info("* Trust Check");
            Map<Vp, W3cVcSkelsList> trustedVps = verifiedVps;

            log.info("* Policy Match");

            // Set response variables
            lastCode = HttpStatus.NOT_IMPLEMENTED;
            lastReason = null;
            boolean granted = false;
            com.crosswordcybersecurity.model.W3cVcSkelsList atts = new com.crosswordcybersecurity.model.W3cVcSkelsList();

            log.info("Loop Over VPs");
            Iterator<Vp> itn3 = trustedVps.keySet().iterator();
            while (itn3.hasNext()) {
                Vp vp = itn3.next();
                W3cVcSkelsList w3cVcSkelsList = trustedVps.get(vp);
                String format = vp.getFormat();
                Verifier verifier = verifierConfiguration.getVerifier(format);

                if (verifier != null) {
                    log.info("Verifier: " + verifier.getDescription());
                    log.info("Format: " + verifier.getFormat());

                    String verifierUrl = verifier.getUrl();

                    // Validation
                    log.info("Call Internal Policy Match API");

                    ValidateRequest validateRequest = new ValidateRequest();
                    validateRequest.setPolicyMatch(body.getPolicyMatch());
                    validateRequest.setPolicyRegistryUrl(body.getPolicyRegistryUrl());
                    validateRequest.setVcs(w3cVcSkelsList);

                    com.crosswordcybersecurity.verifier.api.AccessDecisionApi accessDecisionApi = new com.crosswordcybersecurity.verifier.api.AccessDecisionApi();
                    accessDecisionApi.getApiClient().setBasePath(verifierUrl);
                    try {
                        ValidateResponse validateResponse = accessDecisionApi.validate(validateRequest);
                        log.debug("Validate Response: " + validateResponse.toString());

                        if (validateResponse != null) {
                            boolean isMatch = validateResponse.isMatched();
                            granted = granted || isMatch; // true if any VP matches
                            lastCode = HttpStatus.OK;

                            if (isMatch) {
                                lastReason = "000";
                                lastCode = HttpStatus.OK;

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

                            } else if (! lastReason.equals("000")) {
                                lastReason = "002";
                            }
                        }

                    } catch (ApiException e) {
                        log.error("Error Accessing Verifier: " + verifier.getUrl());
                        log.error(e.getMessage());
                        int code = e.getCode();
                        if (code == 500 && lastCode != HttpStatus.OK) {
                            lastCode = HttpStatus.SERVICE_UNAVAILABLE;

                        } else if (! lastReason.equals("000")) {
                            lastReason = "002";
                        }
                        // Try Next VP
                    }
                } // if verifier is known
            } // while

            // If none of the VP verification succeeds, exit.
            if (! lastReason.equals("000")) {
                AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
                accessDecisionResponse.setReasonCode(lastReason);
                accessDecisionResponse.setGranted(false);
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);
            }
            if (lastCode != HttpStatus.OK) {
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(lastCode);
            }

            log.error("Prepare Response Object");
            AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
            accessDecisionResponse.setGranted(granted);
            if (granted) {
                accessDecisionResponse.setAtts(atts);
            } else {
                accessDecisionResponse.setReasonCode(lastReason);
            }
            log.debug("Response: " + accessDecisionResponse.toString());
            log.info("## END DECISION ##");
            return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);
        }

        return new ResponseEntity<AccessDecisionResponse>(HttpStatus.BAD_REQUEST);
    }

}
