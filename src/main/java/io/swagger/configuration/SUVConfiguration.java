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

package io.swagger.configuration;

import io.identiproof.suv.model.TermOfUse;
import io.identiproof.suv.model.Verifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("suv")
public class SUVConfiguration {

    private Map<String, Verifier> verifiers;
    private List<TermOfUse> train;
    private List<String> trustedIssuers;
    private String policyMatchUrl;
    private String policyFormat;

    public Map<String, Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(Map<String, Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    public Verifier getVerifier(String longFormType, String didType, String algorithm) {
        Verifier response = null;

        if (longFormType != null && verifiers.containsKey(longFormType)) {
            response = verifiers.get(longFormType);
        }

        // Fallback (if no Verifier has the long form type)
        if (response == null){
            Verifier candidate = null;
            for (String verifierKey : verifiers.keySet()) {
                Verifier verifier = verifiers.get(verifierKey);
                String verifierDidType = verifier.getDidMethod();
                List<String> verifierAlgorithm = verifier.getSignatureAlgorithm();
                Boolean didMatch = null;
                if (verifierDidType != null && didType != null) {
                    didMatch = verifierDidType.equals(didType);
                }
                Boolean algorithmMatch = null;
                if (verifierAlgorithm != null && algorithm != null) {
                    algorithmMatch = verifierAlgorithm.contains(algorithm);
                }
                boolean bothMatch = (didMatch != null && algorithmMatch != null && didMatch && algorithmMatch);
                boolean anyMatch = (didMatch != null && algorithmMatch == null && didMatch) ||
                                   (algorithmMatch != null && didMatch == null && algorithmMatch);
                if (bothMatch) {
                    response = verifier;
                } else if (anyMatch && response == null) {
                    candidate = verifier;
                }
            }
            if (response == null && candidate != null ) {
                response = candidate;
            }
        }

        // Final Fallback (use default aka "nullVerifier") if no match
        if (response == null && verifiers.containsKey("default")) {
            response = verifiers.get("default");
        }

        return response;
    }

    public List<TermOfUse> getTrain() {
        return train;
    }

    public void setTrain(List<TermOfUse> train) {
        this.train = train;
    }

    public List<io.identiproof.verifier.model.TermOfUse> filterToUs(List<io.identiproof.verifier.model.TermOfUse> termsOfUse) {
        List<io.identiproof.verifier.model.TermOfUse> respToUs = new ArrayList<>();

        Map<String, Map<String, List<String>>> trustedTrain = new HashMap<>();
        for (TermOfUse touFromConfig: train) {
            String id = touFromConfig.getId();
            String type = touFromConfig.getType();
            List<String> schemes = touFromConfig.getTrustScheme();
            Map<String, List<String>> intHm;
            if (trustedTrain.containsKey(id)) {
                intHm = trustedTrain.get(id);
            } else {
                intHm = new HashMap<>();
            }
            intHm.put(type, schemes);
            trustedTrain.put(id, intHm);
        }
        for (io.identiproof.verifier.model.TermOfUse touFromIssuer: termsOfUse) {
            String id = touFromIssuer.getId();
            String type = touFromIssuer.getType();
            List<String> schemes = touFromIssuer.getTrustScheme();
            if (trustedTrain.containsKey(id)) {
                Map<String, List<String>> trustedTypes = trustedTrain.get(id);
                if (trustedTypes.containsKey(type)) {
                    List<String> respSchemes = new ArrayList<>();
                    List<String> trustedSchemes = trustedTypes.get(type);
                    for (String scheme: schemes) {
                        if (trustedSchemes.contains(scheme)) {
                            respSchemes.add(scheme);
                        }
                    }
                    io.identiproof.verifier.model.TermOfUse respToU = new io.identiproof.verifier.model.TermOfUse();
                    respToU.setId(id);
                    respToU.setType(type);
                    respToU.setTrustScheme(respSchemes);
                    respToUs.add(respToU);
                }
            }
        }
        return respToUs;
    }

    public List<String> getTrustedIssuers() {
        return trustedIssuers;
    }

    public void setTrustedIssuers(List<String> trustedIssuers) {
        this.trustedIssuers = trustedIssuers;
    }

    public String getPolicyMatchUrl() {
        return policyMatchUrl;
    }

    public void setPolicyMatchUrl(String policyMatchUrl) {
        this.policyMatchUrl = policyMatchUrl;
    }

    public String getPolicyFormat() {
        return policyFormat;
    }

    public void setPolicyFormat(String policyFormat) {
        this.policyFormat = policyFormat;
    }
}
