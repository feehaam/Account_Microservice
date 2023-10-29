package com.prep.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PasswordResetDTO {
    private String username;
    private String email;
    private Integer otp;
    private String newPassword;
}
