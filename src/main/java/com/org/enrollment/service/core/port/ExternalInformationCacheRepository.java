package com.org.enrollment.service.core.port;

import com.org.enrollment.service.core.model.ExternalValue;
import reactor.core.publisher.Mono;

public interface ExternalInformationCacheRepository {

    Mono<ExternalValue> findPercentage();

    Mono<Boolean> savePercentage(ExternalValue externalValue);

}
