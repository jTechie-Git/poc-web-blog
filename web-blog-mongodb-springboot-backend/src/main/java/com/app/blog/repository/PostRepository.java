package com.app.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.blog.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
}
