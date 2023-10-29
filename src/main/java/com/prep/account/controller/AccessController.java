package com.prep.account.controller;

import com.prep.account.exception.AccessDeniedException;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.PasswordMismatchException;
import com.prep.account.iservice.AccessService;
import com.prep.account.model.LoginRequestDTO;
import com.prep.account.model.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/access")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
            throws AccountNotFoundException, PasswordMismatchException{
        return ResponseEntity.ok(accessService.login(loginRequestDTO));
    }

    @PostMapping("/generate-otp/{identity}")
    public ResponseEntity<String> generateOTP(@PathVariable String identity) throws AccountNotFoundException {
        accessService.generateOTP(identity);
        return ResponseEntity.ok("OTP generated successfully.");
    }

    @GetMapping("/generate-internal-token")
    public ResponseEntity<String> generateInternalToken() throws AccessDeniedException {
        String internalToken = accessService.generateInternalToken();
        return ResponseEntity.ok(internalToken);
    }

    @GetMapping("/check-email-availability/{email}")
    public ResponseEntity<String> checkEmailAvailability(@PathVariable String email) {
        boolean isAvailable = accessService.checkEmailAvailability(email);
        return ResponseEntity.ok(String.valueOf(isAvailable));
    }

    @GetMapping("/check-username-availability/{username}")
    public ResponseEntity<String> checkUsernameAvailability(@PathVariable String username) {
        boolean isAvailable = accessService.checkUsernameAvailability(username);
        return ResponseEntity.ok(String.valueOf(isAvailable));
    }
}
