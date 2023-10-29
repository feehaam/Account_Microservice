package com.prep.account.iservice;

import com.prep.account.exception.AccountNotFoundException;

import java.util.List;
import java.util.UUID;

public interface RoleManagementService {
    void assignRole(UUID userId, String role) throws AccountNotFoundException;
    List<String> getRoles(UUID userId) throws AccountNotFoundException;
    void removeRole(UUID userId, String role) throws AccountNotFoundException;
}
