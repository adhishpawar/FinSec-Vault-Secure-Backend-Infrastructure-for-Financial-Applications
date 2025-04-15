package com.adhish.FinSec.Service;

import com.adhish.FinSec.Model.CustomerDetails;
import com.adhish.FinSec.Model.User;
import com.adhish.FinSec.Repo.CustomerDetailsRepository;
import com.adhish.FinSec.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    public User saveUserWithDetails(User user, CustomerDetails details){

        user.setCustomerDetails(details);
        details.setUser(user);

        return userRepository.save(user);
    }

    public User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}
