package com.example.Backend.repository;

import com.example.Backend.models.Projects;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Projects_repo extends MongoRepository<Projects,String> {

}
