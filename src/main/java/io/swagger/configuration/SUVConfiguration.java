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

import io.identiproof.suv.model.Verifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("suv")
public class SUVConfiguration {

    private Map<String, Verifier> verifiers;
    private String trainUrl;
    private String trainType;
    private boolean ignoreUnknownTrustType;
    private List<String> trustedIssuers;
    private String policyMatchUrl;

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

    public String getTrainUrl() {
        return trainUrl;
    }

    public void setTrainUrl(String trainUrl) {
        this.trainUrl = trainUrl;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
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
}
