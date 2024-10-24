package flab.nutridiary.commom.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import flab.nutridiary.file.S3FileStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.s3.endpoint}")
    private String endPoint;

    @Value("${cloud.aws.s3.regionName}")
    private String regionName;

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3Client.builder()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .build();
    }

    @Bean
    public S3FileStore s3FileStore() {
        return new S3FileStore(amazonS3());
    }
}
