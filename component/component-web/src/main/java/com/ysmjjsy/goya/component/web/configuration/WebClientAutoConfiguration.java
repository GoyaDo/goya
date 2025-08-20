package com.ysmjjsy.goya.component.web.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/14 11:34
 */
@Slf4j
@AutoConfiguration(after = {
        RestClientAutoConfiguration.class,
        RestTemplateAutoConfiguration.class}
)
public class WebClientAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebClientAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    RestClient.Builder restClientBuilder(RestClientBuilderConfigurer restClientBuilderConfigurer, RestTemplate restTemplate) {
        return restClientBuilderConfigurer.configure(RestClient.builder(restTemplate));
    }

    @Bean
    @ConditionalOnMissingBean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder.build();
        log.trace("[Goya] |- Bean [RestClient] Configure.");
        return restClient;
    }
}
