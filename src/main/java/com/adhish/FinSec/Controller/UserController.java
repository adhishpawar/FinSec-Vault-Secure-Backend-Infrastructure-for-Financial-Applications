package com.adhish.FinSec.Controller;

import com.adhish.FinSec.Model.CustomerDetails;
import com.adhish.FinSec.Model.User;
import com.adhish.FinSec.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Save user + encrypted details
    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        CustomerDetails details = user.getCustomerDetails();
        return userService.saveUserWithDetails(user, details);
    }

    // Get user with decrypted PAN/account
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return Optional.ofNullable(userService.getUserOrThrow(id));
    }
}
