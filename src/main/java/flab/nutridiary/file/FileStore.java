package flab.nutridiary.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStore {
    public String uploadReviewImage(MultipartFile image);
    public void deleteImageFromS3(String imageAddress);
}
