package com.platformnexus.enterprise.notification.config;

import com.platformnexus.enterprise.notification.util.TokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Felix Chiu on 2/22/18.
 */
@Configuration
public class AppConfig {

    @Bean
    public TokenGenerator tokenGenerator() {
        return new TokenGenerator(10);
    }
}
