package com.example.restapi.security;

import java.security.Principal;

public class CustomUserPrincipal implements Principal {

    private final String mid;

    public CustomUserPrincipal(String mid) {
        this.mid = mid;
    }

    @Override
    public String getName() {
        return "";
    }
}
