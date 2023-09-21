package com.org.enrollment.service.adapter.repository;

import com.org.enrollment.service.core.model.ExternalValue;
import com.org.enrollment.service.core.port.ExternalInformationCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ExternalInformationCacheRepositoryImpl implements ExternalInformationCacheRepository {

    private static final Logger logger = LoggerFactory.getLogger(ExternalInformationCacheRepositoryImpl.class);
    private final ReactiveRedisOperations<String, ExternalValue> redisOperations;

    private static final String REDIS_VALUE = "external_value";

    public ExternalInformationCacheRepositoryImpl(ReactiveRedisConnectionFactory factory) {
        factory.getReactiveConnection().serverCommands().flushAll().block();

        Jackson2JsonRedisSerializer<ExternalValue> serializer = new Jackson2JsonRedisSerializer<>(ExternalValue.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, ExternalValue> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, ExternalValue> context = builder.value(serializer).build();

        this.redisOperations = new ReactiveRedisTemplate<>(factory, context);
    }

    @Override
    public Mono<ExternalValue> findPercentage() {
        logger.info("findPercentage init.");

        return redisOperations.opsForValue().get(REDIS_VALUE).
                doOnEach(x -> logger.info("findPercentage end. externalValue: {}", x));
    }

    @Override
    public Mono<Boolean> savePercentage(ExternalValue externalValue) {
        logger.info("savePercentage init.");

        return redisOperations.opsForValue().set(REDIS_VALUE, externalValue)
                .doOnEach(x -> logger.info("savePercentage end. isSaved: {}", x));
    }
}




