package flab.nutridiary.commom.file;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import flab.nutridiary.commom.exception.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
@Service
public class FileService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String uploadImage(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String s3FileName = generateS3FileName(extension);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getBytes())) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(getContentType(extension));
            metadata.setContentLength(image.getSize());

            uploadToS3(s3FileName, byteArrayInputStream, metadata);
            return getS3FileUrl(s3FileName);
        } catch (IOException e) {
            throw new SystemException("이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new SystemException("이미지 삭제 중 오류가 발생했습니다.");
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private String generateS3FileName(String extension) {
        return UUID.randomUUID().toString().substring(0, 10) + "_" + LocalDateTime.now() + "." + extension;
    }

    private String getContentType(String extension) {
        return "image/" + extension;
    }

    private void uploadToS3(String s3FileName, ByteArrayInputStream inputStream, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
    }

    private String getS3FileUrl(String s3FileName) {
        return amazonS3.getUrl(bucketName, s3FileName).toString();
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
