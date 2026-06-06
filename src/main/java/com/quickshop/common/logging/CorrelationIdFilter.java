package com.quickshop.common.logging;

import com.quickshop.common.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException  {


        String correlationId=request.getHeader(Constants.CORRELATION_ID);

        if(correlationId==null) {
            correlationId= UUID.randomUUID().toString();
        }
        // Added the correlation/Span ID  in the log
        MDC.put(Constants.CORRELATION_ID,correlationId);

        response.setHeader(Constants.CORRELATION_ID,correlationId);

        try {
            filterChain.doFilter(request,response);
        }  finally {
            MDC.clear();
        }

    }
}
