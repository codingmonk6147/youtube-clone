package com.codingmonk.demo.youtubeclone.dto;


import com.codingmonk.demo.youtubeclone.model.Comment;
import com.codingmonk.demo.youtubeclone.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoDto {


    private String id;
    private String title;
    private String description;

    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;

    private String thumbnailUrl;

    private Integer likeCount;
    private Integer dislikeCount;
    private Integer viewCount;

}
