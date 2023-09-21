package com.org.enrollment.service.core.usecase;

import com.org.enrollment.service.core.port.RequestLogRepository;
import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.SimplePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class FindPaginatedRequestLogCU {

    private static final Logger logger = LoggerFactory.getLogger(FindPaginatedRequestLogCU.class);

    private final RequestLogRepository requestLogRepository;

    public FindPaginatedRequestLogCU(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public Mono<SimplePage<RequestLog>> execute(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) page = 10;

        logger.info("execute init. page: {}, size: {}", page, size);
        return this.requestLogRepository.find(page, size);
    }
}
