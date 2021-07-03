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

package io.swagger.configuration;

import io.identiproof.suv.model.Verifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("verifier")
public class VerifierConfiguration {

    private Map<String, Verifier> verifiers;
    private String trainUrl;
    private List<String> trustedIssuers;

    public Map<String, Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(Map<String, Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    public Verifier getVerifier(String type) {
        Verifier response = null;

        if (type != null && verifiers.containsKey(type)) {
            response = verifiers.get(type);
        } else if (verifiers.containsKey("default")) {
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

    public List<String> getTrustedIssuers() {
        return trustedIssuers;
    }

    public void setTrustedIssuers(List<String> trustedIssuers) {
        this.trustedIssuers = trustedIssuers;
    }
}
