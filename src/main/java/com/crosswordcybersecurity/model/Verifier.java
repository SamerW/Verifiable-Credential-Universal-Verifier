package com.crosswordcybersecurity.model;

public class Verifier {
    private String url;
    private String description;
    private String didMethodOfIssuers;
    private String cryptoAlgorithm;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDidMethodOfIssuers() {
        return didMethodOfIssuers;
    }

    public void setDidMethodOfIssuers(String didMethodOfIssuers) {
        this.didMethodOfIssuers = didMethodOfIssuers;
    }

    public String getCryptoAlgorithm() {
        return cryptoAlgorithm;
    }

    public void setCryptoAlgorithm(String cryptoAlgorithm) {
        this.cryptoAlgorithm = cryptoAlgorithm;
    }
}
