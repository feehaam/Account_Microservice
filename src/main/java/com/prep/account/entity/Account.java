package com.prep.account.entity;

import com.prep.account.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    // User info
    private String userName;
    private String email;
    private String password;
    private String phoneNo;
    @OneToMany (cascade = CascadeType.ALL)
    private List<Role> roles;

    // Account info
    private LocalDate registerDate;
    private LocalDate usernameUpdateDate;
    private Integer otp;
    private LocalDateTime otpGenerationTime;
    @Column(columnDefinition = "boolean default false")
    private boolean accountLocked;
    @Column(columnDefinition = "boolean default true")
    private boolean accountEnabled;
    @Column(columnDefinition = "boolean default false")
    private boolean twoFactorEnabled;
    @Column(columnDefinition = "boolean default false")
    private boolean accountDeactivated;
    @Column(columnDefinition = "boolean default false")
    private boolean accountSuspended;
    @Column(columnDefinition = "boolean default false")
    private boolean accountBanned;
    @Column(columnDefinition = "bigint default 0")
    private Long bannedHour;
}
