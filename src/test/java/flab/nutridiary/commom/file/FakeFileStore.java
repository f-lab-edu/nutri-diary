package flab.nutridiary.commom.file;

import flab.nutridiary.commom.exception.SystemException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class FakeFileStore implements FileStore {

    public String uploadReviewImage(MultipartFile image) {
        String directory = "/nutridiary/test/review/";

        String fileName = directory + generateFileName(image);
        return uploadToLocal(fileName, image);
    }

    public void deleteImageFromS3(String imageAddress){
        File file = new File(imageAddress);
        file.delete();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        return UUID.randomUUID().toString().substring(0, 10) + "_" + LocalDateTime.now() + "." + extension;
    }

    private String uploadToLocal(String fileName, MultipartFile file) {
        try {
            file.transferTo(new File(fileName));
        } catch (IOException e) {
            throw new SystemException("파일 저장 중 오류가 발생했습니다.");
        }
        return fileName;
    }
}
