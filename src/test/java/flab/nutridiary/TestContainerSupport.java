package flab.nutridiary;

import com.amazonaws.services.s3.AmazonS3;
import flab.nutridiary.commom.config.S3Config;
import flab.nutridiary.commom.file.FileStoreService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
@Transactional
@SpringBootTest
public abstract class TestContainerSupport {
    static final MySQLContainer<?> mysqlContainer;

    @MockBean
    protected S3Config s3Config;

    @MockBean
    protected AmazonS3 amazonS3;

    @MockBean
    protected FileStoreService fileStoreService;

    static {
        mysqlContainer = new MySQLContainer<>("mysql:8.0")
                .withDatabaseName("nutridiary");

        mysqlContainer.start();
    }

    @DynamicPropertySource
    public static void init(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
}
