package com.org.enrollment.service.core.usecase;

import com.org.enrollment.service.core.exeption.FindingExternalValueException;
import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.RequestLogState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class CalculateCU {

    private static final Logger logger = LoggerFactory.getLogger(CalculateCU.class);

    private final CalculateService calculateService;

    public CalculateCU(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    public Mono<Double> execute(Double numberA, Double numberB) {

        logger.info("execute init. numberA: {}. numberB: {}", numberA, numberB);

        double sum = numberA + numberB;

        return this.calculateService.findPercentage()
                .switchIfEmpty(Mono.error(new FindingExternalValueException("error getting external value")))
                .map(externalValue -> {
                    double result = sum + (sum * externalValue.getPercentage() / 100);

                    // utilizamos subscribeOn(Schedulers.boundedElastic()) para especificar que queremos que esta
                    // tarea se ejecute en un grupo de subprocesos elástico (con capacidad limitada) en segundo plano.

                    RequestLog requestLog = new RequestLog(numberA, numberB, result, RequestLogState.SUCCESSFUL);

                    this.calculateService.saveAsyncRequestLog(requestLog);

                    logger.info("execute end. numberA: {}. numberB: {}. percentage: {}, result: {}", numberA, numberB,
                            externalValue.getPercentage(), result);

                    return result;
                }).doOnError(error -> {
                    logger.error("execute exception", error);

                    RequestLog requestLog = new RequestLog(numberA, numberB, RequestLogState.ERROR);
                    this.calculateService.saveAsyncRequestLog(requestLog);
                });
    }

}

