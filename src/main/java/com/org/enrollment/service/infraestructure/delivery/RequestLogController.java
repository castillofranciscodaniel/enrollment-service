package com.org.enrollment.service.infraestructure.delivery;

import com.org.enrollment.service.core.exeption.RateLimitException;
import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.SimplePage;
import com.org.enrollment.service.core.usecase.FindPaginatedRequestLogCU;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/requestLog")
public class RequestLogController {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogController.class);

    private final FindPaginatedRequestLogCU findPaginatedRequestLogCU;

    private final Bucket bucket;

    public RequestLogController(FindPaginatedRequestLogCU findPaginatedRequestLogCU, Bucket bucket) {
        this.findPaginatedRequestLogCU = findPaginatedRequestLogCU;
        this.bucket = bucket;
    }

    @GetMapping("/history")
    public Mono<ResponseEntity<SimplePage<RequestLog>>> findPaginatedRequestLog(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        logger.info("findPaginatedRequestLog init. page: {}, size: {}", page, size);

        if (!bucket.tryConsume(1)) {
            logger.error("findPaginatedRequestLog end. To many request. page: {}, size: {}", page, size);
            return Mono.error(new RateLimitException());

        }

        return this.findPaginatedRequestLogCU.execute(page, size)
                .map(ResponseEntity::ok);
    }

}
