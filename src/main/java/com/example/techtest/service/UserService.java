package com.example.techtest.service;

import com.example.techtest.entity.User;
import com.example.techtest.exception.ConflictException;
import com.example.techtest.exception.UnauthorizedException;
import com.example.techtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String username, String password) {
        if (userRepository.findByUsernameAndPassword(username, password).isPresent()) {
            throw new ConflictException();
        }

        userRepository.save(new User(username, password, UUID.randomUUID()));
    }

    public UUID retrieveToken(String username, String password) {
        return userRepository
                .findByUsernameAndPassword(username, password)
                .orElseThrow(UnauthorizedException::new)
                .getToken();
    }
}
