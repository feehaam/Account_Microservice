package com.prep.account.service;

import com.prep.account.entity.Role;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.ActionNotAllowedException;
import com.prep.account.iservice.RoleManagementService;
import com.prep.account.repository.AccountRepository;
import com.prep.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class RoleManagementServiceImpl implements RoleManagementService {

    private final AccountRepository accountRepository;

    @Override
    public void assignRole(UUID userId, String role) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Assign the role is it already not exist.
        List<Role> roles = account.getRoles();
        if(roles.stream().noneMatch(r -> r.getRoleName().equals(role.toUpperCase()))){
            roles.add(new Role(0L, role));
            account.setRoles(roles);
            accountRepository.save(account);
        }
        else throw new ActionNotAllowedException("User already has that role");
    }

    @Override
    public List<String> getRoles(UUID userId) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Retrieve and return the roles
        return account.getRoles().stream().map(Role::getRoleName).toList();
    }

    @Override
    public void removeRole(UUID userId, String role) throws AccountNotFoundException {
        // Check if the account exists
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));

        // Remove the specified role if user has it.
        if(account.getRoles().stream().anyMatch(r -> r.getRoleName().equals(role.toUpperCase()))){
            throw new ActionNotAllowedException("User doesn't have the role.");
        }

        account.setRoles(account.getRoles().stream().filter(r -> !r.getRoleName().equals(role)).collect(Collectors.toList()));
        accountRepository.save(account);
    }
}
