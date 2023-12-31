package com.codingmonk.demo.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0) ;
    private AtomicInteger dislikes =  new AtomicInteger(0);
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus  videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<Comment> commentList = new CopyOnWriteArrayList<>();

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }


    public void incrementLikes(){
        likes.incrementAndGet();
    }

    public void decrementLikes(){
        likes.decrementAndGet();
    }

    public  void incrementDislikes(){
        dislikes.incrementAndGet();
    }

    public void decrementDislikes(){
        dislikes.decrementAndGet();
    }


    public void incrementViewCount() {
        viewCount.incrementAndGet();
    }

    public void addComment(Comment comment) {

        commentList.add(comment);
    }
}
