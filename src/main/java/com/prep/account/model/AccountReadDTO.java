package com.prep.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountReadDTO {
    private UUID userId;
    private String userName;
    private String email;
    private String phoneNo;
    private List<String> roles;
    private LocalDate registerDate;
}
