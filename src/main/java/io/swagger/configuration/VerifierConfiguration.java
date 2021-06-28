package io.swagger.configuration;

import com.crosswordcybersecurity.model.Verifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("verifier")
public class VerifierConfiguration {

    private Map<String, Verifier> verifiers;

    public Map<String, Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(Map<String, Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    public Verifier getVerifier(String type) {
        Verifier response = null;
        Iterator<String> itn = verifiers.keySet().iterator();

        if (type != null && verifiers.containsKey(type)) {
            response = verifiers.get(type);
        } else if (verifiers.containsKey("default")) {
            response = verifiers.get("default");
        }

        return response;
    }

}
