package org.dbclient.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    public static final int TIMEOUT = 1000;
    private static final String BASE_URL = "http://localhost:8080";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}
