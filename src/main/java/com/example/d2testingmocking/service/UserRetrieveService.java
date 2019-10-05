package com.example.d2testingmocking.service;

import com.example.d2testingmocking.domain.User;
import com.example.d2testingmocking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRetrieveService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " was not found"));
    }
}
