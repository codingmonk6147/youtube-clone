package com.codingmonk.demo.youtubeclone.service;

import com.codingmonk.demo.youtubeclone.dto.CommentDto;
import com.codingmonk.demo.youtubeclone.dto.UploadVideoResponse;
import com.codingmonk.demo.youtubeclone.dto.VideoDto;
import com.codingmonk.demo.youtubeclone.model.Comment;
import com.codingmonk.demo.youtubeclone.model.Video;
import com.codingmonk.demo.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {


    private final S3Service s3Service;


    private final VideoRepository videoRepository;

    private final UserService userService;



    public UploadVideoResponse uploadVideo(MultipartFile multipartFile){


            String videoUrl = s3Service.uploadFile(multipartFile);
            var video = new Video();
            video.setVideoUrl(videoUrl);

            Video savedVideo = videoRepository.save(video);

            return new UploadVideoResponse(savedVideo.getId(),savedVideo.getVideoUrl());

    }

    public VideoDto editVideo(VideoDto videoDto) {

        Video savedVideo = getVideoById(videoDto.getId());

        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());



        videoRepository.save(savedVideo);

        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {

        Video savedVideo = getVideoById(videoId);

       String thumbnailUrl =  s3Service.uploadFile(file);

       savedVideo.setThumbnailUrl(thumbnailUrl);

       return thumbnailUrl;
    }


    Video getVideoById(String videoId){
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {

        Video savedVideo = getVideoById(videoId);

        increaseVideoCount(savedVideo);

        userService.addVideoToHistory(videoId);


        VideoDto videoDto = new VideoDto();

        videoDto.setVideoUrl(savedVideo.getVideoUrl());
        videoDto.setThumbnailUrl(savedVideo.getThumbnailUrl());
        videoDto.setId(savedVideo.getId());
        videoDto.setTitle(savedVideo.getTitle());
        videoDto.setDescription(savedVideo.getDescription());
        videoDto.setTags(savedVideo.getTags());
        videoDto.setVideoStatus(savedVideo.getVideoStatus());
        videoDto.setDislikeCount(savedVideo.getDislikes().get());
        videoDto.setLikeCount(savedVideo.getLikes().get());
        videoDto.setViewCount(savedVideo.getViewCount().get());


        return videoDto;
    }

    private void increaseVideoCount(Video savedVideo) {

        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {
        Video videoById = getVideoById(videoId);


        if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        }
        else if (userService.ifDisLikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDisLikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);

        }
        else{
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        VideoDto videoDto = new VideoDto();

        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());

        return  videoDto;

    }

    public VideoDto dislikeVideo(String videoId) {
        Video videoById = getVideoById(videoId);


        if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDisLikedVideos(videoId);
        }
        else if (userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDislikes();
            userService.addToDislikedVideos(videoId);

        }
        else{
            videoById.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        }
        VideoDto videoDto = new VideoDto();

        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return  videoDto;
    }

    public void addComment(String videoId, CommentDto commentDto) {

        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentDto.getCommentText());
        comment.setAuthorId(commentDto.getAuthorId());


        video.addComment(comment);
        videoRepository.save(video);
    }

    public List<CommentDto> getAllComments(String videoId) {
        Video video = getVideoById(videoId);
        
        List<Comment> commentList = video.getCommentList();
        
        return commentList.stream().map(this::mapToCommentDto).toList();

       
    }

    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .commentText(comment.getText())
                .authorId(comment.getAuthorId())
                .build();
    }

    public List<VideoDto> getAllVideos() {
 
            return videoRepository.findAll().stream().map(this::mapToVideoDto).toList();
    }

    private VideoDto mapToVideoDto(Video video) {
        return VideoDto.builder()
                .id(video.getId())
                .videoUrl(video.getVideoUrl())
                .description(video.getDescription())
                .tags(video.getTags())
                .title(video.getTitle())
                .videoStatus(video.getVideoStatus())
                .thumbnailUrl(video.getThumbnailUrl())
                .likeCount(video.getLikes().get())
                .dislikeCount(video.getDislikes().get())
                .build();
    }
}
