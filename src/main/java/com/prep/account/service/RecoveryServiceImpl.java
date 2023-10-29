package com.prep.account.service;

import com.prep.account.entity.Account;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.OTPValidationException;
import com.prep.account.exception.PasswordMismatchException;
import com.prep.account.iservice.RecoveryService;
import com.prep.account.model.PasswordChangeDTO;
import com.prep.account.model.PasswordResetDTO;
import com.prep.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class RecoveryServiceImpl implements RecoveryService {

    private final AccountRepository accountRepository;

    @Override
    public void changePassword(UUID userId, PasswordChangeDTO passwordChangeDTO)
            throws AccountNotFoundException, PasswordMismatchException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Check if the old password matches the existing password
        if (!passwordChangeDTO.getOldPassword().equals(account.getPassword())) {
            throw new PasswordMismatchException();
        }

        // Update the password
        account.setPassword(passwordChangeDTO.getNewPassword());
        accountRepository.save(account);
    }

    @Override
    public void resetPassword(UUID userId, PasswordResetDTO passwordResetDTO) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        if(account.getOtp() != null && account.getOtp() != 0
                && account.getOtp() == passwordResetDTO.getOtp()
                && account.getOtpGenerationTime().isAfter(LocalDateTime.now().minusMinutes(5L))){
            account.setPassword(passwordResetDTO.getNewPassword());
            accountRepository.save(account);
        }
        else throw new OTPValidationException(passwordResetDTO.getOtp());
    }
}
