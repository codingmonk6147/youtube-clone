package com.codingmonk.demo.youtubeclone.service;


import com.codingmonk.demo.youtubeclone.dto.UserInfoDTO;
import com.codingmonk.demo.youtubeclone.model.User;
import com.codingmonk.demo.youtubeclone.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private  String userinfoEndpoint;

    private final UserRepository userRepository;
    public void registerUser(String tokenValue){


      HttpRequest httpRequest=  HttpRequest.newBuilder().GET().uri(URI.create(userinfoEndpoint)).setHeader("Authorization", String.format("Bearer %s", tokenValue)).build();

        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        try {
            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = responseString.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            UserInfoDTO userInfoDTO = objectMapper.readValue(body, UserInfoDTO.class);

            User user = new User();
            user.setFirstName(userInfoDTO.getGivenName());
            user.setLastName(userInfoDTO.getFamilyName());
            user.setEmailAddress(userInfoDTO.getEmail());
            user.setSub(userInfoDTO.getSub());

            userRepository.save(user);


        }catch (Exception exception){
            throw  new RuntimeException("Exception while registrating user ", exception);

        }

    }
}
