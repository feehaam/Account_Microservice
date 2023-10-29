package com.prep.account.iservice;

import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.DuplicateEntityException;
import com.prep.account.model.AccountCreateDTO;
import com.prep.account.model.AccountReadDTO;

import java.util.UUID;

public interface AccountManagementService {
    void createAccount(AccountCreateDTO accountCreateDTO) throws DuplicateEntityException;
    void updateUsername(UUID userId, String newUsername) throws AccountNotFoundException;
    void updatePhoneNumber(UUID userId, String newPhoneNumber) throws AccountNotFoundException;
    AccountReadDTO getUserInfo(String identity) throws AccountNotFoundException;
}
