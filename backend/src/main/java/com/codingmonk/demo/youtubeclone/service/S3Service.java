package com.codingmonk.demo.youtubeclone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService{


    private final AmazonS3Client amazonS3Client;

    public S3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public static final String BUCKET_NAME = "zoutube-clone";

    @Override
    public String uploadFile(MultipartFile file){


        //upload to s3


        //Prepare a unique key
        var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + fileExtension;

        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try{
            amazonS3Client.putObject(BUCKET_NAME ,key,file.getInputStream(),metadata);
        }catch(IOException ioException){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An Exception occured while uploading the file");
        }

        amazonS3Client.setObjectAcl(BUCKET_NAME , key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl(BUCKET_NAME ,key);


    }
}
