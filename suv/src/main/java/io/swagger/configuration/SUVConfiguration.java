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

    private int defaultVerifier;
    private List<Verifier> verifiers;
    private List<TermOfUse> train;
    private List<String> trustedIssuers;
    private String policyMatchUrl;
    private String policyFormat;

    public int getDefaultVerifier() {
        return defaultVerifier;
    }

    public void setDefaultVerifier(int defaultVerifier) {
        this.defaultVerifier = defaultVerifier;
    }

    public List<Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(List<Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    public Verifier getVerifier(List<String> atContextList, List<String> typeList, String didType, String algorithm) {

        if (atContextList != null && typeList != null && didType != null && algorithm != null &&
                ! atContextList.isEmpty() && ! typeList.isEmpty() && ! didType.isEmpty() && ! algorithm.isEmpty()) {

            for (Verifier verifier: verifiers) {
                String confAtContext = verifier.getAtContext();
                String confType = verifier.getType();
                String confDidMethod = verifier.getDidMethod();
                List<String> confAlgorithm = verifier.getSignatureAlgorithm();

                // if all matches, return the first occurrence in the Verifiers list.
                if (atContextList.contains(confAtContext) &&
                        typeList.contains(confType) &&
                        confDidMethod.equals(didType) &&
                        confAlgorithm.contains(algorithm)) {
                    return verifier;
                }
            }
        }

        // Final Fallback (use default) if no match or null if default is invalid
        if (defaultVerifier < verifiers.size()) {
            return verifiers.get(defaultVerifier);
        } else {
            return null;
        }
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
