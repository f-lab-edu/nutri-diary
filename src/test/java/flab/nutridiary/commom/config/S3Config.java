package flab.nutridiary.commom.config;

import flab.nutridiary.file.FileStore;
import flab.nutridiary.commom.file.FakeFileStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class S3Config {

    @Bean
    public FileStore fileStore() {
        return new FakeFileStore();
    }
}