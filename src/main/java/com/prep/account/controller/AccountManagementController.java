package com.prep.account.controller;

import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.iservice.AccountManagementService;
import com.prep.account.model.AccountCreateDTO;
import com.prep.account.model.AccountReadDTO;
import com.prep.account.utilities.token.IDExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountManagementController {

    private final AccountManagementService accountManagementService;

    public AccountManagementController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestBody AccountCreateDTO accountCreateDTO) {
        accountManagementService.createAccount(accountCreateDTO);
        return ResponseEntity.ok("Account created successfully.");
    }

    @PutMapping("/update-username")
    public ResponseEntity<String> updateUsername(@RequestParam String newUsername) throws AccountNotFoundException {
        accountManagementService.updateUsername(IDExtractor.getUserID(), newUsername);
        return ResponseEntity.ok("Username updated successfully.");
    }

    @PutMapping("/update-phone-number")
    public ResponseEntity<String> updatePhoneNumber(@RequestParam String newPhoneNumber) throws AccountNotFoundException {
        accountManagementService.updatePhoneNumber(IDExtractor.getUserID(), newPhoneNumber);
        return ResponseEntity.ok("Phone number updated successfully.");
    }

    @GetMapping("/get-user-info/{identity}")
    public ResponseEntity<AccountReadDTO> getUserInfo(@PathVariable String identity) throws AccountNotFoundException {
        AccountReadDTO userInfo = accountManagementService.getUserInfo(identity);
        return ResponseEntity.ok(userInfo);
    }
}
