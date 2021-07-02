package io.identiproof.suv.api;

import io.identiproof.suv.exceptions.BadVpJwtException;
import io.identiproof.suv.exceptions.PolicyMatchingException;
import io.identiproof.suv.exceptions.VerificationException;
import io.identiproof.suv.model.*;
import io.identiproof.suv.model.AccessDecisionResponse;
import io.identiproof.suv.model.Verifier;
import io.identiproof.train.api.TrainAtvApi;
import io.identiproof.train.model.TRAINATVRequestParams;
import io.identiproof.train.model.TRAINATVResult;
import io.identiproof.verifier.ApiException;
import io.identiproof.verifier.api.VpVerificationApi;
import io.identiproof.verifier.model.*;
import io.identiproof.verifier.model.TermOfUse;
import io.identiproof.verifier.model.W3cVc;
import io.identiproof.verifier.model.W3cVcSkelsList;
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
import io.identiproof.suv.model.Vp;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.Timestamp;
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

    private Map<String, Object> decodeJwtVp(String vpjwt) throws BadVpJwtException, IOException {
        Map<String, Object> unsignedVp = null;
        String[] vpParts = vpjwt.split("\\.");
        String sHeader = new String(Base64.getDecoder().decode(vpParts[0]));
        String sPayload = new String(Base64.getDecoder().decode(vpParts[1]));
        Map<String, Object> header = objectMapper.readValue(sHeader, Map.class);
        Map<String, Object> payload = objectMapper.readValue(sPayload, Map.class);
        if (payload.containsKey("vp")) {
            unsignedVp = (Map<String, Object>) payload.get("vp");
            if (payload.containsKey("jti")) {
                String jti = (String) payload.get("jti");
                unsignedVp.put("id", jti);
            }
            if (payload.containsKey("exp")) {
                Long exp = Long.valueOf( ((Integer) payload.get("exp")) * 1000 );
                String sExp = (new Timestamp(exp)).toInstant().toString();
                unsignedVp.put("expirationDate", sExp);
            }
            if (payload.containsKey("iat")) {
                Long exp = Long.valueOf( ((Integer) payload.get("iat")) * 1000 );
                String sIat = (new Timestamp(exp)).toInstant().toString();
                unsignedVp.put("issuanceDate", sIat);
            }
        } else {
            throw (new BadVpJwtException("VP JWT doesn't contain a vp property."));
        }
        return unsignedVp;
    }

    private String getVerifier(String requestVpFormat, Object requestVpPresentation) throws BadVpJwtException, IOException {
        // Decode VP if in JWT format.
        Map<String, Object> requestVp = null;
        if (requestVpFormat.equals("jwt_vp")) {
                requestVp = decodeJwtVp((String) requestVpPresentation);
        } else {
            requestVp = (Map<String, Object>) requestVpPresentation;
        }

        // Find VP type and get Verifier details
        String longFormType = null;
        if (requestVp.containsKey("@context") && requestVp.containsKey("type")) {
            List<String> atContextList = (List<String>) requestVp.get("@context");
            List<String> typeList = (List<String>) requestVp.get("type");
            if (! atContextList.isEmpty() && ! typeList.isEmpty()) {
                String atContext = atContextList.size() > 1 ? atContextList.get(1) : "";
                String type = typeList.size() > 1 ? typeList.get(1) : "";
                if (! type.equals("")) {
                    longFormType = atContext + "#" + type;
                }
            }
        }

        return longFormType;
    }

    private Map<Vp, W3cVcSkelsList> verify(String challenge, String rpUrl, List<Vp> vps) throws VerificationException, BadVpJwtException, IOException {
        Map<Vp, W3cVcSkelsList> verifiedVps = new HashMap<Vp, W3cVcSkelsList>(); // VPs that passed verification

        // Set response variables
        HttpStatus lastCode = HttpStatus.NOT_IMPLEMENTED;
        String lastReason = "";
//            001 the VCs do not belong to the holder (Proof of possession fails);
//            002 the VCs do not match the RP’s policy;
//            003 one or more of the VC Issuer’s are not trusted;
//            004 the challenge in the VP does not match the input challenge;
//            005 incorrect/unknown schema elements in the VP or a VC

        log.info("Loop Over VPs");
        Iterator<Vp> itn = vps.iterator();
        while (itn.hasNext()) {
            Vp vp = itn.next();

            String requestVpFormat = vp.getFormat();
            Object requestVpPresentation = vp.getPresentation();

            String longFormType = null;
            longFormType = getVerifier(requestVpFormat, requestVpPresentation);

            Verifier verifier = verifierConfiguration.getVerifier(longFormType);

            if (verifier != null) {
                log.info("Verifier: " + verifier.getDescription());

                // Verification
                log.info("Call Verifier API");
                W3cVcSkelsList w3cVcSkelsList = null;

                io.identiproof.verifier.model.Vp verifierVp = new io.identiproof.verifier.model.Vp();
                verifierVp.setFormat(requestVpFormat);
                verifierVp.setPresentation(requestVpPresentation);

                VerifyVpRequest verifyVpRequest = new VerifyVpRequest();
                verifyVpRequest.setChallenge(challenge);
                verifyVpRequest.setRpurl(rpUrl);
                verifyVpRequest.setVp(verifierVp);

                VpVerificationApi vpVerificationApi = new VpVerificationApi();
                vpVerificationApi.getApiClient().setBasePath(verifier.getUrl());

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

                    if (code == 404 || (code == 0 && e.getCause().getClass().isAssignableFrom(ConnectException.class))) {
                        lastCode = HttpStatus.SERVICE_UNAVAILABLE;

                    } else {
                        if (code != 401 && lastCode != HttpStatus.OK) {
                            lastCode = HttpStatus.resolve(code);
                        }
                        if (code == 401 && !lastReason.equals("000")) {
                            lastCode = HttpStatus.OK;
                            lastReason = "001";
                        }
                    }

                    // Try Next VP
                }
            } // if verifier is known
        } // while

        // If none of the VP verification succeeds, exit.
        if (lastCode == HttpStatus.OK && ! lastReason.equals("000")) {
            VerificationException verificationException = new VerificationException();
            verificationException.setReason(lastReason);
            verificationException.setErrorCode(lastCode);
            throw verificationException;
        }
        if (lastCode != HttpStatus.OK) {
            VerificationException verificationException = new VerificationException();
            verificationException.setErrorCode(lastCode);
            throw verificationException;
        }

        return verifiedVps;
    }

    private Map<Vp, Boolean> trustCheck(Map<Vp, W3cVcSkelsList> verifiedVps) {
        String trainUrl = verifierConfiguration.getTrainUrl();
        List<String> trustedIssuers = verifierConfiguration.getTrustedIssuers();

        Map<Vp, Boolean> trustedVps = new HashMap<Vp, Boolean>();

        log.info("Loop Over VPs");
        Iterator<Vp> itn = verifiedVps.keySet().iterator();
        while (itn.hasNext()) {
            Vp vp = itn.next();
            boolean trustIssuersResult = true;

            W3cVcSkelsList w3cVcs = verifiedVps.get(vp);
            Iterator<W3cVc> itnVc = w3cVcs.iterator();
            boolean trainApiIsInaccessible = false;
            while (itnVc.hasNext()) {
                W3cVc w3cVc = itnVc.next();
                boolean vcContainsToU = w3cVc.getTermsOfUse() != null && ! w3cVc.getTermsOfUse().isEmpty();
                String issuer = w3cVc.getIssuer();

                log.info("Trust " + issuer + "?");

                // Call TRAIN API
                if (vcContainsToU) {
                    List<TermOfUse> termsOfUse = w3cVc.getTermsOfUse();
                    Iterator<TermOfUse> itnToU = termsOfUse.iterator();
                    while (itnToU.hasNext()) {
                        TermOfUse termOfUse = itnToU.next();
                        Iterator<String> itnSchemes = termOfUse.getTrustScheme().iterator();
                        while (itnSchemes.hasNext()) {
                            String trustScheme = itnSchemes.next();

                            TrainAtvApi trainAtvApi = new TrainAtvApi();
                            trainAtvApi.getApiClient().setBasePath(trainUrl);
                            TRAINATVRequestParams trainAtvRequestParams = new TRAINATVRequestParams();
                            trainAtvRequestParams.setIssuer(issuer);
                            trainAtvRequestParams.setTrustSchemePointer(trustScheme);
                            TRAINATVResult trainAtvResult = null;
                            try {
                                trainAtvResult = trainAtvApi.atvtrainApiV1SsiPost(trainAtvRequestParams);

                                log.debug("Train API request: " + trainAtvRequestParams.toString());
                                log.debug("Train API response: " + trainAtvResult.toString());

                                boolean trustIssuerResult = (trainAtvResult.getVerificationStatus() == TRAINATVResult.VerificationStatusEnum.OK);
                                log.info(trustIssuerResult ? "yes" : "no");
                                trustIssuersResult = trustIssuersResult && trustIssuerResult;

                            } catch (io.identiproof.train.ApiException e) {
                                log.error("Could not communicate with TRAIN API: " + e.getMessage());
                                int code = e.getCode();
                                if (code == 404 || (code == 0 && e.getCause().getClass().isAssignableFrom(ConnectException.class))) {
                                    log.error("Train API is inaccesible.");
                                    trainApiIsInaccessible = true;
                                    break;
                                }

                            }
                            if (trainApiIsInaccessible) {
                                break;
                            }
                        } // loop over trustScheme
                    } // loop over ToU
                } // if VP contains ToU

                // Fallback: Check if Issuer is in a Table.
                if (! vcContainsToU || trainApiIsInaccessible) {
                    boolean trustIssuerResult = trustedIssuers.contains(issuer);
                    log.info(trustIssuerResult ? "yes" : "no");
                    trustIssuersResult = trustIssuersResult && trustIssuerResult;
                }

            } // loop over VCs

            trustedVps.put(vp, trustIssuersResult);

        } // loop over VPs

        return trustedVps;
    }

    private io.identiproof.suv.model.W3cVcSkelsList policyMatch(Map<Vp, W3cVcSkelsList> trustedVps, Object policyMatch, String policyRegistryUrl) throws BadVpJwtException, IOException, PolicyMatchingException {
        // Set response variables
        HttpStatus lastCode = HttpStatus.NOT_IMPLEMENTED;
        String lastReason = "";
        boolean granted = false;
        io.identiproof.suv.model.W3cVcSkelsList atts = new io.identiproof.suv.model.W3cVcSkelsList();

        log.info("Loop Over VPs");
        Iterator<Vp> itn = trustedVps.keySet().iterator();
        while (itn.hasNext()) {
            Vp vp = itn.next();
            W3cVcSkelsList w3cVcSkelsList = trustedVps.get(vp);

            String requestVpFormat = vp.getFormat();
            Object requestVpPresentation = vp.getPresentation();

            String longFormType = null;
            longFormType = getVerifier(requestVpFormat, requestVpPresentation);


            Verifier verifier = verifierConfiguration.getVerifier(longFormType);

            if (verifier != null) {
                log.info("Verifier: " + verifier.getDescription());

                // Validation
                log.info("Call Internal Policy Match API");

                ValidateRequest validateRequest = new ValidateRequest();
                validateRequest.setPolicyMatch(policyMatch);
                validateRequest.setPolicyRegistryUrl(policyRegistryUrl);
                validateRequest.setVcs(w3cVcSkelsList);

                io.identiproof.verifier.api.AccessDecisionApi accessDecisionApi = new io.identiproof.verifier.api.AccessDecisionApi();
                accessDecisionApi.getApiClient().setBasePath(verifier.getUrl());
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
                            Iterator<io.identiproof.verifier.model.W3cVc> vcIterator = w3cVcSkelsList.iterator();
                            io.identiproof.suv.model.W3cVc w3cVc = new io.identiproof.suv.model.W3cVc();
                            while (vcIterator.hasNext()) {
                                io.identiproof.verifier.model.W3cVc w3cVcDb = vcIterator.next();
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

                    if (code == 404 || (code == 0 && e.getCause().getClass().isAssignableFrom(ConnectException.class))) {
                        lastCode = HttpStatus.SERVICE_UNAVAILABLE;

                    } else if (lastCode != HttpStatus.OK) {
                        lastCode = HttpStatus.resolve(code);
                    }

                    // Try Next VP
                }
            } // if verifier is known
        } // while

        // If none of the VP verification succeeds, exit.
        if (lastCode == HttpStatus.OK) {
            if (!granted) {
                PolicyMatchingException policyMatchingException = new PolicyMatchingException();
                policyMatchingException.setErrorCode(lastCode);
                policyMatchingException.setReason(lastReason);
                throw policyMatchingException;
            }
        } else {
            PolicyMatchingException policyMatchingException = new PolicyMatchingException();
            policyMatchingException.setErrorCode(lastCode);
            throw policyMatchingException;
        }

        return atts;
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

            Map<Vp, W3cVcSkelsList> verifiedVps = null;

            try {
                verifiedVps = verify(challenge, rpUrl, vps);

            } catch (VerificationException e) {
                // If none of the VP verification succeeds, exit.

                HttpStatus lastCode = e.getErrorCode();
                String lastReason = e.getReason();

                if (lastCode == HttpStatus.OK && ! lastReason.equals("000")) {
                    AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
                    accessDecisionResponse.setReasonCode(lastReason);
                    accessDecisionResponse.setGranted(false);
                    log.debug("Response: " + accessDecisionResponse.toString());
                    log.info("## END DECISION ##");
                    return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);
                }
                if (lastCode != HttpStatus.OK) {
                    log.info("## END DECISION ##");
                    return new ResponseEntity<AccessDecisionResponse>(lastCode);
                }

            } catch (BadVpJwtException | IOException e) {
                log.error("Could not decode input VP JWT: " + e.getMessage());
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(HttpStatus.BAD_REQUEST);
            }

            log.info("* Trust Check");
            Map<Vp, Boolean> trustedVps = trustCheck(verifiedVps);
            Iterator<Vp> trustedItn = trustedVps.keySet().iterator();
            boolean trustCheck = true;
            while (trustedItn.hasNext()) {
                Vp vp = trustedItn.next();
                trustCheck = trustCheck && trustedVps.get(vp);
            }
            if (! trustCheck) {
                AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
                accessDecisionResponse.setReasonCode("003");
                accessDecisionResponse.setGranted(false);
                log.debug("Response: " + accessDecisionResponse.toString());
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);
            }

            log.info("* Policy Match");

            io.identiproof.suv.model.W3cVcSkelsList atts = null;
            try {
                atts = policyMatch(verifiedVps, policyMatch, policyRegistryUrl);

            } catch (PolicyMatchingException e) {
                if (e.getErrorCode() == HttpStatus.OK) {
                    log.info("* Prepare Response Object");
                    AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
                    accessDecisionResponse.setReasonCode(e.getReason());
                    accessDecisionResponse.setGranted(false);
                    log.debug("Response: " + accessDecisionResponse.toString());
                    log.info("## END DECISION ##");
                    return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);
                } else {
                    log.info("## END DECISION ##");
                    return new ResponseEntity<AccessDecisionResponse>(e.getErrorCode());
                }

            } catch (BadVpJwtException | IOException e) {
                log.error("Could not decode input VP JWT: " + e.getMessage());
                log.info("## END DECISION ##");
                return new ResponseEntity<AccessDecisionResponse>(HttpStatus.BAD_REQUEST);
            }

            log.info("* Prepare Response Object");
            AccessDecisionResponse accessDecisionResponse = new AccessDecisionResponse();
            accessDecisionResponse.setGranted(true);
            accessDecisionResponse.setAtts(atts);
            log.debug("Response: " + accessDecisionResponse.toString());
            log.info("## END DECISION ##");
            return new ResponseEntity<AccessDecisionResponse>(accessDecisionResponse, HttpStatus.OK);

        }

        return new ResponseEntity<AccessDecisionResponse>(HttpStatus.BAD_REQUEST);
    }

}
