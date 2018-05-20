package com.platformnexus.enterprise.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by Felix Chiu on 2/23/18.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "messageLogExecutor")
    public Executor messageLogExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean(name = "eventExecutor")
    public Executor eventExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean(name = "restExecutor")
    public Executor restExecutor() {
        return new ThreadPoolTaskExecutor();
    }

}