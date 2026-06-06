package com.quickshop.common.config;

import com.quickshop.common.utils.Constants;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignAutoConfiguration {

    @Bean
    public RequestInterceptor tokenRelayInterceptor() {

        return requestTemplate -> {

            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            if(authentication instanceof
                    JwtAuthenticationToken jwtToken) {

                String token =
                        jwtToken.getToken()
                                .getTokenValue();

                requestTemplate.header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token);
            }

            // Correlation Id Propagation
            String correlationId =
                    MDC.get(Constants.CORRELATION_ID);

            if(correlationId != null) {

                requestTemplate.header(Constants.CORRELATION_ID,
                        correlationId);
            }
        };
    }
}