package com.prep.account.service;

import com.prep.account.entity.Role;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.ActionNotAllowedException;
import com.prep.account.exception.DuplicateEntityException;
import com.prep.account.iservice.AccessService;
import com.prep.account.iservice.AccountManagementService;
import com.prep.account.model.AccountCreateDTO;
import com.prep.account.entity.Account;
import com.prep.account.model.AccountReadDTO;
import com.prep.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class AccountManagementServiceImpl implements AccountManagementService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessService accessService;

    @Override
    public void createAccount(AccountCreateDTO accountCreateDTO) throws DuplicateEntityException {
        // Check if the email or username already exists to prevent duplicates
        if (accountRepository.findByEmail(accountCreateDTO.getEmail()).isPresent()) {
            throw new DuplicateEntityException("Account", "email", accountCreateDTO.getEmail());
        }
        if (accountRepository.findByUserName(accountCreateDTO.getUsername()).isPresent()) {
            throw new DuplicateEntityException("Account", "username", accountCreateDTO.getUsername());
        }

        // Create a new account entity and save it to the database
        Account newAccount = new Account();
        newAccount.setUserId(UUID.randomUUID());
        newAccount.setUserName(accountCreateDTO.getUsername());
        newAccount.setEmail(accountCreateDTO.getEmail());
        newAccount.setPassword(passwordEncoder.encode(accountCreateDTO.getPassword()));
        newAccount.setPhoneNo(accountCreateDTO.getPhoneNo());
        newAccount.setRegisterDate(LocalDate.now());
        newAccount.setUsernameUpdateDate(LocalDate.now());
        Role role = new Role(0L, "USER");
        newAccount.setRoles(new ArrayList<>());
        newAccount.getRoles().add(role);

        accountRepository.save(newAccount);
    }

    @Override
    public void updateUsername(UUID userId, String newUsername) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Check if the new username already exists to prevent duplicates
        if (accountRepository.findByUserName(newUsername).isPresent()) {
            throw new DuplicateEntityException("Account", "username", newUsername);
        }

        LocalDate possibleUpdateDate = existingAccount.getUsernameUpdateDate().plusYears(1L);

        if (LocalDate.now().isAfter(possibleUpdateDate)) {
            existingAccount.setUserName(newUsername);
            existingAccount.setUsernameUpdateDate(LocalDate.now());
            accountRepository.save(existingAccount);
        } else {
            int remainingDays = -1;
            while(possibleUpdateDate.isAfter(LocalDate.now())){
                remainingDays ++;
                possibleUpdateDate= possibleUpdateDate.minusDays(1L);
            }
            throw new ActionNotAllowedException("Can not change username twice in a year. Try again after " + remainingDays + " days.");
        }
    }

    @Override
    public void updatePhoneNumber(UUID userId, String newPhoneNumber) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        existingAccount.setPhoneNo(newPhoneNumber);
        accountRepository.save(existingAccount);
    }

    @Override
    public AccountReadDTO getUserInfo(String identity) throws AccountNotFoundException {
        Account account = accessService.findByIdentity(identity);

        AccountReadDTO result = new AccountReadDTO();
        result.setEmail(account.getEmail());
        result.setUserId(account.getUserId());
        result.setRoles(account.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        result.setUserName(account.getUserName());
        result.setRegisterDate(account.getRegisterDate());
        result.setPhoneNo(account.getPhoneNo());

        return result;
    }
}
