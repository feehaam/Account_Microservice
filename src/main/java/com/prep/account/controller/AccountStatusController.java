package com.prep.account.controller;

import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.iservice.AccountStatusService;
import com.prep.account.utilities.token.IDExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/status")
public class AccountStatusController {

    private final AccountStatusService accountStatusService;

    public AccountStatusController(AccountStatusService accountStatusService) {
        this.accountStatusService = accountStatusService;
    }

    // User controlled
    @PutMapping("/toggle-two-factor/{status}")
    public ResponseEntity<String> toggleTwoFactor(@PathVariable Boolean status) throws AccountNotFoundException {
        accountStatusService.toggleTwoFactor(IDExtractor.getUserID(), status);
        return ResponseEntity.ok("Two-factor authentication status updated successfully.");
    }

    @PutMapping("/toggle-deactivation/{status}")
    public ResponseEntity<String> toggleDeactivation(@PathVariable Boolean status) throws AccountNotFoundException {
        accountStatusService.toggleDeactivation(IDExtractor.getUserID(), status);
        return ResponseEntity.ok("Account deactivation status updated successfully.");
    }

    // Server controlled
    @PutMapping("/toggle-lockout/{userId}/{status}")
    public ResponseEntity<String> toggleLockout(@PathVariable UUID userId,
                                                @PathVariable Boolean status) throws AccountNotFoundException {
        accountStatusService.toggleLockout(userId, status);
        return ResponseEntity.ok("Lockout status updated successfully.");
    }

    @PutMapping("/toggle-enabling/{userId}/{status}")
    public ResponseEntity<String> toggleEnabling(@PathVariable UUID userId,
                                                 @PathVariable Boolean status) throws AccountNotFoundException {
        accountStatusService.toggleEnabling(userId, status);
        return ResponseEntity.ok("Account enabling status updated successfully.");
    }

    @PutMapping("/suspend-account/{userId}")
    public ResponseEntity<String> suspendAccount(@PathVariable UUID userId) throws AccountNotFoundException {
        accountStatusService.suspendAccount(userId);
        return ResponseEntity.ok("Account suspended successfully.");
    }

    @PutMapping("/add-ban-hour/{userId}")
    public ResponseEntity<String> addBanHour(@PathVariable UUID userId, @RequestParam Integer hour)
            throws AccountNotFoundException {
        accountStatusService.addBanHour(userId, hour);
        return ResponseEntity.ok("Ban hours added successfully.");
    }
}