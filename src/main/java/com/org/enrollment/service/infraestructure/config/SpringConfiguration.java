package com.org.enrollment.service.infraestructure.config;

import com.org.enrollment.service.core.port.ExternalInformationRepository;
import com.org.enrollment.service.core.port.ExternalInformationCacheRepository;
import com.org.enrollment.service.core.port.RequestLogRepository;
import com.org.enrollment.service.core.usecase.CalculateCU;
import com.org.enrollment.service.core.usecase.CalculateService;
import com.org.enrollment.service.core.usecase.FindPaginatedRequestLogCU;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
@EnableAutoConfiguration
public class SpringConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public FindPaginatedRequestLogCU newFindPaginatedRequestLogCU(RequestLogRepository requestLogRepository) {
        return new FindPaginatedRequestLogCU(requestLogRepository);
    }

    @Bean
    public CalculateService newCalculateService(ExternalInformationRepository externalInformationRepository,
                                                ExternalInformationCacheRepository externalInformationCacheRepository,
                                                RequestLogRepository requestLogRepository) {
        return new CalculateService(externalInformationRepository,
                externalInformationCacheRepository,
                requestLogRepository
        );
    }

    @Bean
    public CalculateCU newCalculateCU(CalculateService calculateService) {
        return new CalculateCU(calculateService);
    }

    @Bean
    public LocalBucket rateLimitConfig() {
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components());
    }
}
