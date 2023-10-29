package com.prep.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LoginResponseDTO {
    private String userId;
    private String username;
    private String bearerToken;
    private List<String> roles;
}
