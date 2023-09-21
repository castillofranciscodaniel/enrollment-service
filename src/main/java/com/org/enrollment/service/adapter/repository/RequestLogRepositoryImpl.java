package com.org.enrollment.service.adapter.repository;

import com.org.enrollment.service.adapter.repository.model.RequestLogEntity;
import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.SimplePage;
import com.org.enrollment.service.core.port.RequestLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RequestLogRepositoryImpl implements RequestLogRepository {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogRepositoryImpl.class);

    private final R2dbcEntityTemplate template;

    public RequestLogRepositoryImpl(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<RequestLog> save(RequestLog requestLog) {
        return this.template.insert(new RequestLogEntity(requestLog)).map(RequestLogEntity::toModel);
    }

    @Override
    public Mono<SimplePage<RequestLog>> find(Integer page, Integer size) {

        logger.info("find init. page: {}, size: {}", page, size);

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Query query = Query.query(Criteria.empty()).with(pageable);
        Mono<List<RequestLogEntity>> findAll = this.template.select(query, RequestLogEntity.class).collectList();

        Mono<Long> count = this.template.select(RequestLogEntity.class).count();

        return Mono.zip(findAll, count)
                .map(tuple -> this.buildSimplePage(tuple, (int) page, (int) size));
    }

    private SimplePage<RequestLog> buildSimplePage(Tuple2<List<RequestLogEntity>, Long> tuple, Integer page, Integer size) {
        logger.info("buildSimplePage init. page: {}, size: {}", page, size);

        var content = tuple.getT1();
        var count = tuple.getT2();

        var preTotalPage = (count / size);
        var totalPage = (count % size == 0) ? preTotalPage : preTotalPage + 1;

        return new SimplePage<>(
                page,
                size,
                totalPage,
                count,
                content.stream().map(RequestLogEntity::toModel).collect(Collectors.toList())
        );
    }

}
