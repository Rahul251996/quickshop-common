package com.quickshop.common.utils;

import com.quickshop.common.entity.UserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public final class SecurityUtils {
    private SecurityUtils() {}

    public static Jwt getJwt() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Jwt) auth.getPrincipal();
    }

    public static UserPrincipal getCurrentUser() {

        Jwt jwt = getJwt();

        List<String> roles =
                jwt.getClaim("realm_access") == null
                        ? List.of()
                        : (List<String>)
                        ((Map<String,Object>)
                                jwt.getClaim("realm_access"))
                                .get("roles");

        return UserPrincipal.builder()
                .userId(jwt.getSubject())
                .username(
                        jwt.getClaimAsString(
                                "preferred_username"))
                .email(
                        jwt.getClaimAsString(
                                "email"))
                .roles(roles)
                .build();
    }

}
