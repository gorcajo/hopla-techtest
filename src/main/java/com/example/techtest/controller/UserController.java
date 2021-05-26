package com.example.techtest.controller;

import com.example.techtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody CredentialsDto body) {
        userService.signUp(body.getUsername(), body.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(@RequestBody CredentialsDto body) {
        return ResponseEntity.ok(new TokenDto(userService.retrieveToken(body.getUsername(), body.getPassword())));
    }
}
