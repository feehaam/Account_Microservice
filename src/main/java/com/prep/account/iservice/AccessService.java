package com.prep.account.iservice;

import com.prep.account.entity.Account;
import com.prep.account.exception.AccessDeniedException;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.PasswordMismatchException;
import com.prep.account.model.LoginRequestDTO;
import com.prep.account.model.LoginResponseDTO;

import java.util.UUID;

public interface AccessService {
    Account findByIdentity(String identity);
    LoginResponseDTO login(LoginRequestDTO loginDTO) throws AccountNotFoundException, PasswordMismatchException;
    void generateOTP(String identity) throws AccountNotFoundException;
    String generateInternalToken() throws AccessDeniedException;
    Boolean checkEmailAvailability(String email);
    Boolean checkUsernameAvailability(String username);
}
