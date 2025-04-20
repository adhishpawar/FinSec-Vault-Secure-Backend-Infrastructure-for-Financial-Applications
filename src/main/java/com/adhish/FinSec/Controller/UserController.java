package com.adhish.FinSec.Controller;

import com.adhish.FinSec.DTO.UserRegistrationRequest;
import com.adhish.FinSec.Entity.User;
import com.adhish.FinSec.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Save user + encrypted details
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationRequest request) {
        User savedUser = userService.saveUser(request);
        return ResponseEntity.ok(savedUser);
    }

    // Get user with decrypted PAN/account
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return Optional.ofNullable(userService.getUserOrThrow(id));
    }
}
