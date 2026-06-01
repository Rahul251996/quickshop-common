package com.quickshop.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal {

    private String userId;

    private String username;

    private String email;

    private List<String> roles;
}
