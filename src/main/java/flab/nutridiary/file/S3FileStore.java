package flab.nutridiary.file;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import flab.nutridiary.commom.exception.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class S3FileStore implements FileStore {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String uploadReviewImage(MultipartFile image) {
        String directory = "review/";

        String s3FileName = directory + generateS3FileName(image);
        return uploadToS3(s3FileName, image);
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new SystemException("이미지 삭제 중 오류가 발생했습니다.");
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private String generateS3FileName(MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        return UUID.randomUUID().toString().substring(0, 10) + "_" + LocalDateTime.now() + "." + extension;
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file, String extension) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    private String getS3FileUrl(String s3FileName) {
        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    private String uploadToS3(String s3FileName, MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(file.getBytes());
        } catch (IOException e) {
            throw new SystemException("이미지처리 중 오류가 발생했습니다.");
        }
        ObjectMetadata metadata = getObjectMetadata(file, extension);
        amazonS3.putObject(new PutObjectRequest(bucketName, s3FileName, inputStream, metadata));
        return getS3FileUrl(s3FileName);
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException e){
            throw new SystemException("이미지 주소가 올바르지 않습니다.");
        }
    }
}
