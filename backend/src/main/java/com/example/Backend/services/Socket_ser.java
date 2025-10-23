package com.example.Backend.services;

import com.example.Backend.repository.Projects_repo;
import com.example.Backend.repository.User_repo;
import org.springframework.stereotype.Service;

@Service
public class Socket_ser {
    private User_repo user_repo;
    private Projects_repo projects_repo;
    public Socket_ser(User_repo user_repo, Projects_repo projects_repo) {
        this.user_repo = user_repo;
        this.projects_repo = projects_repo;
    }
}
