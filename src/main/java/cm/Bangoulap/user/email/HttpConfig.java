package cm.Bangoulap.user.email;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfig {
    public static final String BASE_URL = "https://api.orange.com";

    /*@Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .basicAuthentication("username", "password")
                .rootUri(BASE_URL)
    }*/
}