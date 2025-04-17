package com.adhish.FinSec.Service;

import com.adhish.FinSec.DTO.UserRegistrationRequest;
import com.adhish.FinSec.Enum.Role;
import com.adhish.FinSec.Model.CustomerDetails;
import com.adhish.FinSec.Model.User;
import com.adhish.FinSec.Repo.CustomerDetailsRepository;
import com.adhish.FinSec.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User saveUser(UserRegistrationRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        User savedUser = userRepository.save(user);

        if(request.getRole() == Role.CUSTOMER)
        {
            CustomerDetails details = new CustomerDetails();

            System.out.println(request.getAccountNumber());
            System.out.println(request.getPanNumber());

            details.setAccountNumber(request.getAccountNumber());
            details.setPanNumber(request.getPanNumber());
            details.setUser(savedUser);  // Link user

            customerDetailsRepository.save(details);
        }

        return savedUser;
    }

    public User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }


}
