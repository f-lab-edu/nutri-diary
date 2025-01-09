package flab.nutridiary.commom.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "flab.nutridiary.search")
@Configuration
public class RestClientConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.url}")
    private String url;

    @Value("${spring.elasticsearch.port}")
    private int port;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url + ":" + port)
                .usingSsl()
                .withBasicAuth(username, password)
                .build();
    }
}