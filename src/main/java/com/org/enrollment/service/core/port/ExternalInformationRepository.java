package com.org.enrollment.service.core.port;

import com.org.enrollment.service.core.model.ExternalValue;
import reactor.core.publisher.Mono;

public interface ExternalInformationRepository {

    Mono<ExternalValue> findPercentage();

}
