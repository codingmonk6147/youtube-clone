package com.codingmonk.demo.youtubeclone.repository;

import com.codingmonk.demo.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
    void save();
}
