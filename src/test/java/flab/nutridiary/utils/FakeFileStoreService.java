package flab.nutridiary.utils;

import flab.nutridiary.commom.file.FileStoreService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("FileStoreService")
@Profile("test")
public class FakeFileStoreService implements FileStoreService {
    @Override
    public String uploadReviewImage(MultipartFile image) {
        return "url";
    }

    @Override
    public void deleteImageFromS3(String imageAddress) {
        // do nothing
    }
}
