package com.quickshop.common.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public CorrelationIdFilter correlationIdFilter() {

            return new CorrelationIdFilter();
        }
    }

