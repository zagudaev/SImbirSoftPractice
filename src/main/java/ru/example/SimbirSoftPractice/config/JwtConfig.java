package ru.example.SimbirSoftPractice.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
    @Value("/auth")
    private String Uri;

    @Value("Authorization")
    private String header;

    @Value("Bearer ")
    private String prefix;

    @Value("#{24*60*60}")
    private int expiration;

    @Value("EXCLUSIVE_KEY")
    private String secret;


    public JwtConfig() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

}