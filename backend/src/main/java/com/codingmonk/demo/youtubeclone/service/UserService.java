package com.codingmonk.demo.youtubeclone.service;

import com.codingmonk.demo.youtubeclone.model.User;
import com.codingmonk.demo.youtubeclone.model.Video;
import com.codingmonk.demo.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {


        private final UserRepository userRepository;


        public User getCurrentUser(){
            String sub =((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");


             return  userRepository.findBySub(sub).orElseThrow(() -> new IllegalArgumentException(("Cannot find with - " + sub)));
        }


    public void addToLikedVideos(String videoId) {

            User currentUser = getCurrentUser();

            currentUser.addToLikeVideos(videoId);

            userRepository.save(currentUser);

    }

    public boolean ifLikedVideo(String videoId){
          return  getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean ifDisLikedVideo(String videoId){
        return  getCurrentUser().getDisLikedVideos().stream().anyMatch(dislikedVideo -> dislikedVideo.equals(videoId));
    }

    public void removeFromLikedVideos(String videoId) {
        User currentUser = getCurrentUser();

        currentUser.removeFromLikedVideos(videoId);

        userRepository.save(currentUser);
    }

    public void removeFromDisLikedVideos(String videoId) {
        User currentUser = getCurrentUser();

        currentUser.removeFromDisLikedVideos(videoId);

        userRepository.save(currentUser);
    }

    public void addToDislikedVideos(String videoId) {


        User currentUser = getCurrentUser();

        currentUser.addToDislikeVideos(videoId);

        userRepository.save(currentUser);

    }

    public void addVideoToHistory(String videoId) {

        User currentUser = getCurrentUser();

        currentUser.addToVideoHistory(videoId);
        userRepository.save(currentUser);
    }

    public void subscribeUser(String userId) {

        User currentUser = getCurrentUser();

        currentUser.addToSubscribedUsers(userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid User - " + userId));

        user.addToSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(user);

        }

    public void unSubscribeUser(String userId) {

        User currentUser = getCurrentUser();

        currentUser.removeFromSubscribedUsers(userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid User - " + userId));

        user.removeFromSubscribers(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(user);

    }

    public Set<String> userHistory(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid User - " + userId));
        return user.getVideoHistory();

    }
}
