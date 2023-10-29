package com.prep.account.repository;

import com.prep.account.entity.Account;
import com.prep.account.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    // Basic CRUD operations
    Optional<Account> findByUserId(UUID userId);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUserName(String userName);
    Optional<Account> findByPhoneNo(String phoneNo);
}
