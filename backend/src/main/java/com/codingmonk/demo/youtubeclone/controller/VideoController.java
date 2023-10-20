package com.codingmonk.demo.youtubeclone.controller;

import com.codingmonk.demo.youtubeclone.dto.UploadVideoResponse;
import com.codingmonk.demo.youtubeclone.dto.VideoDto;
import com.codingmonk.demo.youtubeclone.model.Video;
import com.codingmonk.demo.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {


    private final VideoService videoService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file")MultipartFile file){
            return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file")MultipartFile file, @RequestParam("videoId") String videoId){

        return videoService.uploadThumbnail(file,videoId);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto){

        return videoService.editVideo(videoDto);
    }


    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDetails(@PathVariable String videoId) {
        return videoService.getVideoDetails(videoId);
    }


    @PostMapping("{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable String videoId) {
        return videoService.likeVideo(videoId);
    }
}
