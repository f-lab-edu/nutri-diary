package flab.nutridiary;

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
    protected FileStoreService fileStoreService;
}
