package com.codingmonk.demo.youtubeclone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service implements FileService{

    public static final String BUCKET_NAME = "zoutube-clone";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";


    private S3Client getClient() {

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create("AefafafV7SQWOefawefaqewfTSRAOE", "2aclfaepodNgueaflf8yq")))
                .region(Region.AP_SOUTH_1)
                .build();
    }




    @Override
    public String uploadFile(MultipartFile file)  {


        //upload to s3

        S3Client s3 = getClient();
        //Prepare a unique key
        var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString()+"."+ fileExtension;

//        var metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        metadata.setContentType(file.getContentType());

        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type",file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));



        try {

            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();

            } catch (IOException e) {
                e.printStackTrace();
            }

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .metadata(metadata)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();


            s3.putObject(putOb, RequestBody.fromBytes(bytes));
        }catch(S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }



//        amazonS3Client.setObjectAcl(BUCKET_NAME , key, CannedAccessControlList.PublicRead);
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();


        return s3.utilities().getUrl(request).toString();


    }
}
