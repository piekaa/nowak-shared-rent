package com.piekoszek.nowaksharedrent.controller;

import com.piekoszek.nowaksharedrent.model.Account;
import com.piekoszek.nowaksharedrent.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {
    AuthService authService;

    @RequestMapping(value = "/auth/singup", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        authService.createAccount(account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }
}