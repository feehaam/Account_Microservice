package com.prep.account.utilities.token;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class IDExtractor {
    public static UUID getUserID() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
