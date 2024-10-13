package flab.nutridiary.commom.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {
    public String uploadReviewImage(MultipartFile image);
    public void deleteImageFromS3(String imageAddress);
}
