package com.piekoszek.nowaksharedrent.auth;

public interface AuthService {

    boolean createAccount(Account account);
    Account findAccount(String id);
}
