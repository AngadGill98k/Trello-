package com.example.Backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Backend.models.User;

import java.util.Optional;

public interface User_repo extends MongoRepository<User, String> {
    // You can add custom query methods later
    User findByMail(String mail);
    User findByPass(String pass);
    User findByName(String name);
}