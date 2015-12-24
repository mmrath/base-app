package com.mmrath.sapp.config;

import com.mmrath.sapp.security.xauth.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures x-auth-token security.
 */
@Configuration
public class XAuthConfiguration {

    @Bean
    public TokenProvider tokenProvider(ApplicationProperties applicationProperties) {
        String secret = applicationProperties.getSecurity().getAuthentication().getXauth().getSecret();
        int validityInSeconds = applicationProperties.getSecurity().getAuthentication().getXauth().getTokenValidityInSeconds();
        return new TokenProvider(secret, validityInSeconds);
    }
}
