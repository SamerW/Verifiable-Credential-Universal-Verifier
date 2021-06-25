package io.swagger.configuration;

import com.crosswordcybersecurity.model.Verifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("verifier")
public class VerifierConfiguration {

    private List<Verifier> verifiers;

    public List<Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(List<Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    public Verifier getVerifier(String format) {
        Verifier response = null;
        Iterator<Verifier> itn = verifiers.iterator();
        while (itn.hasNext()) {
            Verifier verifier = itn.next();
            if (verifier.getFormat().equals(format)) {
                response = verifier;
                break;
            }
        }
        return response;
    }

}
