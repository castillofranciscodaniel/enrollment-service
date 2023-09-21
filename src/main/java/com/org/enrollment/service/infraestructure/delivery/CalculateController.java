package com.org.enrollment.service.infraestructure.delivery;

import com.org.enrollment.service.core.exeption.RateLimitException;
import com.org.enrollment.service.core.usecase.CalculateCU;
import com.org.enrollment.service.infraestructure.delivery.dto.CalculateRequest;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/calculate")
public class CalculateController {

    private static final Logger logger = LoggerFactory.getLogger(CalculateController.class);

    private final CalculateCU calculateCU;

    private final Bucket bucket;

    public CalculateController(CalculateCU calculateCU, Bucket bucket) {
        this.calculateCU = calculateCU;
        this.bucket = bucket;
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Double>>> calculate(@RequestBody CalculateRequest calculateRequest) {

        logger.info("calculate init. calculateRequest: {}", calculateRequest);

        if (!bucket.tryConsume(1)) {
            logger.error("calculate end. To many request. calculateRequest: {}", calculateRequest);
            return Mono.error(new RateLimitException());
        }

        return this.calculateCU.execute(calculateRequest.getNumberA(), calculateRequest.getNumberB()).map(result ->
                ResponseEntity.ok(Map.of("result", result))
        );
    }

}
