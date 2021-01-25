package com.app.blog.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.blog.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByUserName(String username);
}
