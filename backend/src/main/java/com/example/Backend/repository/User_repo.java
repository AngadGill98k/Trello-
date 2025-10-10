package com.example.Backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Backend.models.User;
public interface User_repo extends MongoRepository<User, String> {
    // You can add custom query methods later
    User findByMail(String mail);
    User findByPass(String name);
    User findByName(String pass);
}