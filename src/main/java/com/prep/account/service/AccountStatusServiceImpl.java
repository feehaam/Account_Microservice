package com.prep.account.service;

import com.prep.account.entity.Account;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.iservice.AccountStatusService;
import com.prep.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @RequiredArgsConstructor
public class AccountStatusServiceImpl implements AccountStatusService {

    private final AccountRepository accountRepository;

    @Override
    public void toggleTwoFactor(UUID userId, Boolean status) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Toggle the two-factor status
        account.setTwoFactorEnabled(status);
        accountRepository.save(account);
    }

    @Override
    public void toggleDeactivation(UUID userId, Boolean status) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Toggle the deactivation status
        account.setAccountDeactivated(status);
        accountRepository.save(account);
    }


    @Override
    public void toggleLockout(UUID userId, Boolean status) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Toggle the lockout status
        account.setAccountLocked(status);
        accountRepository.save(account);
    }

    @Override
    public void toggleEnabling(UUID userId, Boolean status) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Toggle the enabling status
        account.setAccountEnabled(status);
        accountRepository.save(account);
    }

    @Override
    public void suspendAccount(UUID userId) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Suspend the account
        account.setAccountSuspended(true);
        accountRepository.save(account);
    }

    @Override
    public void addBanHour(UUID userId, Integer hour) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Add the specified number of hours to the ban period
        Long currentBanHour = account.getBannedHour() == null ? 0L : account.getBannedHour();
        account.setBannedHour(currentBanHour + hour);
        accountRepository.save(account);
    }
}
