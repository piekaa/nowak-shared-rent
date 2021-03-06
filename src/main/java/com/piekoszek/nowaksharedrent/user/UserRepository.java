package com.piekoszek.nowaksharedrent.user;

import org.springframework.data.repository.Repository;

interface UserRepository extends Repository<User, String> {

    boolean existsByEmail(String email);
    void save(User user);
    User findByEmail(String email);
}