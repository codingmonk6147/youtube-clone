package com.codingmonk.demo.youtubeclone.repository;

import com.codingmonk.demo.youtubeclone.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
