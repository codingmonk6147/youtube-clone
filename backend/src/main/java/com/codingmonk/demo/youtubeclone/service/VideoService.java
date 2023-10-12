package com.codingmonk.demo.youtubeclone.service;

import com.codingmonk.demo.youtubeclone.model.Video;
import com.codingmonk.demo.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;

    private final VideoRepository videoRepository;

    public VideoService(S3Service s3Service, VideoRepository videoRepository) {
        this.s3Service = s3Service;
        this.videoRepository = videoRepository;
    }

    public void uploadVideo(MultipartFile multipartFile){


            String videoUrl = s3Service.uploadFile(multipartFile);
            var video = new Video();
            video.setVideoUrl(videoUrl);

            videoRepository.save(video);


    }
}
