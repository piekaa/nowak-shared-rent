package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;

import java.util.Optional;

class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;
    private HashService hashService;
    private JwtService jwtService;

    AuthServiceImpl (AccountRepository accountRepository, HashService hashService, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.hashService = hashService;
        this.jwtService = jwtService;
    }

    @Override
    public boolean createAccount(Account input) {
        if(!accountRepository.existsByEmail(input.getEmail())) {
            String hashPassword = hashService.encryptString(input.getPassword());
            Account account = new Account(input.getEmail(), input.getName(), hashPassword);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public Optional<String> loginUser(Account input) {
        Account account = accountRepository.findByEmail(input.getEmail());
        if (account != null && hashService.compareWithHash(input.getPassword(), account.getPassword()))
        {
            JwtData jwtData = JwtData.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .build();
            return Optional.of(jwtService.generateToken(jwtData));
        }
        return Optional.empty();
    }
}