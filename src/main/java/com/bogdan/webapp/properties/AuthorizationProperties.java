package com.bogdan.webapp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authorization")
public class AuthorizationProperties {

    private Integer hours;

    private String algorithmSecret;

    private String issuer;

    public Integer getHours() {
        return hours;
    }

    public String getAlgorithmSecret() {
        return algorithmSecret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public void setAlgorithmSecret(String algorithmSecret) {
        this.algorithmSecret = algorithmSecret;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
