package flab.nutridiary;

import com.amazonaws.services.s3.AmazonS3;
import flab.nutridiary.commom.config.S3Config;
import flab.nutridiary.commom.file.FileStoreService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public abstract class SpringBootTestSupport {

    @MockBean
    protected S3Config s3Config;

    @MockBean
    protected AmazonS3 amazonS3;

    @MockBean
    protected FileStoreService fileStoreService;
}
