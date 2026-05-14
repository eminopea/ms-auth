package com.template.ms_auth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.template.ms_auth.document.UserDocument;

public interface UserRepository extends MongoRepository<UserDocument, String> { 
    Optional<UserDocument> findByUsername(String username);
}
