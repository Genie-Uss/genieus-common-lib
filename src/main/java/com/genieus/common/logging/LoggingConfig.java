package com.genieus.common.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
    @Bean
    public LogExecutionAspect logExecutionAspect() {
        return new LogExecutionAspect();
    }
}
