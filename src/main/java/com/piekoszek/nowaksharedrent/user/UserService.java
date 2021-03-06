package com.piekoszek.nowaksharedrent.user;

public interface UserService {

    void createUser (User user);
    boolean isAccountExists (String email);
    User getUser (String email);
    void addApartmentToUser (UserApartment userApartment, User user);
}
