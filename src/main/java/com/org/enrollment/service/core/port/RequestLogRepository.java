package com.org.enrollment.service.core.port;

import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.SimplePage;
import reactor.core.publisher.Mono;

public interface RequestLogRepository {

    Mono<RequestLog> save(RequestLog requestLog);

    Mono<SimplePage<RequestLog>> find(Integer page, Integer size);

}
