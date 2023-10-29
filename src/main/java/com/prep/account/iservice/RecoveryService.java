package com.prep.account.iservice;

import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.PasswordMismatchException;
import com.prep.account.model.PasswordChangeDTO;
import com.prep.account.model.PasswordResetDTO;

import java.util.UUID;

public interface RecoveryService {
    void changePassword(UUID userId, PasswordChangeDTO passwordChangeDTO) throws AccountNotFoundException, PasswordMismatchException;
    void resetPassword(UUID userId, PasswordResetDTO passwordResetDTO) throws AccountNotFoundException;
}
