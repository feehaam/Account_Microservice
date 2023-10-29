package com.prep.account.controller;

import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.ActionNotAllowedException;
import com.prep.account.iservice.RoleManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/role")
public class RoleManagementController {

    private final RoleManagementService roleManagementService;

    public RoleManagementController(RoleManagementService roleManagementService) {
        this.roleManagementService = roleManagementService;
    }

    @PutMapping("/assign-role/{userId}")
    public ResponseEntity<String> assignRole(@PathVariable UUID userId, @RequestParam String role)
            throws AccountNotFoundException, ActionNotAllowedException {
        roleManagementService.assignRole(userId, role);
        return ResponseEntity.ok("Role assigned successfully.");
    }

    @GetMapping("/get-roles/{userId}")
    public ResponseEntity<List<String>> getRoles(@PathVariable UUID userId) throws AccountNotFoundException {
        List<String> roles = roleManagementService.getRoles(userId);
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/remove-role/{userId}")
    public ResponseEntity<String> removeRole(@PathVariable UUID userId, @RequestParam String role)
            throws AccountNotFoundException, ActionNotAllowedException {
        roleManagementService.removeRole(userId, role);
        return ResponseEntity.ok("Role removed successfully.");
    }
}