package com.techbellys.authenticationservice.controller;

import com.techbellys.authenticationservice.model.AuthUser;
import com.techbellys.authenticationservice.repository.AuthUserRepository;
import com.techbellys.authenticationservice.service.AuthUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AuthUserManagementController {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthUserRoleService authUserRoleService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        List<AuthUser> users = authUserRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        AuthUser user = authUserRepository.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    @PostMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createRole(@RequestParam String roleName) {
        if (authUserRoleService.createRole(roleName)) {
            return ResponseEntity.ok("Role created successfully");
        }
        return ResponseEntity.badRequest().body("Role already exists");
    }

    @PostMapping("/user/{username}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> assignRoleToUser(@PathVariable String username, @RequestParam String roleName) {
        if (authUserRoleService.assignRoleToUser(username, roleName)) {
            return ResponseEntity.ok("Role assigned to user successfully");
        }
        return ResponseEntity.badRequest().body("User or Role not found");
    }

    @DeleteMapping("/user/{username}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> removeRoleFromUser(@PathVariable String username, @RequestParam String roleName) {
        if (authUserRoleService.removeRoleFromUser(username, roleName)) {
            return ResponseEntity.ok("Role removed from user successfully");
        }
        return ResponseEntity.badRequest().body("User or Role not found");
    }
}
