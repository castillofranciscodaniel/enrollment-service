package com.org.enrollment.service.core.usecase;

import com.org.enrollment.service.core.port.ExternalInformationRepository;
import com.org.enrollment.service.core.model.ExternalValue;
import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.port.ExternalInformationCacheRepository;
import com.org.enrollment.service.core.port.RequestLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Date;

public class CalculateService {

    private static final Logger logger = LoggerFactory.getLogger(CalculateService.class);

    private final ExternalInformationRepository externalInformationRepository;

    private final ExternalInformationCacheRepository externalInformationCacheRepository;

    private final RequestLogRepository requestLogRepository;

    private static final int MAX_RETRIES = 3;

    public CalculateService(ExternalInformationRepository externalInformationRepository,
                            ExternalInformationCacheRepository externalInformationCacheRepository, RequestLogRepository requestLogRepository) {
        this.externalInformationRepository = externalInformationRepository;
        this.externalInformationCacheRepository = externalInformationCacheRepository;
        this.requestLogRepository = requestLogRepository;
    }

    public Mono<ExternalValue> findPercentage() {
        logger.info("findPercentage init.");

        return this.externalInformationCacheRepository.findPercentage()
                .switchIfEmpty(this.findExternalAndSaveInCache())
                .flatMap(externalValue -> {

                    Mono<ExternalValue> defaultReturn = Mono.just(externalValue);

                    if (this.wasSavedMoreThanThirtyMinutosAgo(externalValue.getCreatedAt())) {
                        return this.findExternalAndSaveInCache().onErrorResume(error -> {
                            logger.warn("Max retries reached or unrecoverable error: " + error.getMessage());
                            return defaultReturn;  // Valor de fallback en caso de error
                        });
                    }

                    return defaultReturn;
                });
    }

    private Mono<ExternalValue> findExternalAndSaveInCache() {
        logger.info("findExternalAndSaveInCache init.");

        return this.externalInformationRepository.findPercentage()
                .flatMap(percentage -> this.externalInformationCacheRepository.savePercentage(percentage)
                        .map(x -> percentage))
                .retryWhen(Retry.fixedDelay(MAX_RETRIES, Duration.ofSeconds(1)));  // Reintentar 3 veces con intervalo de 1 segundo
    }

    private boolean wasSavedMoreThanThirtyMinutosAgo(Date dateSaved) {
        logger.info("wasSavedMoreThanThirtyMinutosAgo init. dateSaved: {}", dateSaved);

        long millisecondsDifference = new Date().getTime() - dateSaved.getTime();
        long minutesDifference = millisecondsDifference / (60 * 1000);
        return minutesDifference > 30;
    }

    public void saveAsyncRequestLog(RequestLog requestLog) {
        logger.info("saveAsyncRequestLog init. requestLog: {}", requestLog);

        this.requestLogRepository.save(requestLog).subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

}
