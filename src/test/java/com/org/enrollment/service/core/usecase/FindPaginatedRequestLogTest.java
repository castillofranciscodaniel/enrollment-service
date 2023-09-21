package com.org.enrollment.service.core.usecase;

import com.org.enrollment.service.core.port.RequestLogRepository;
import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.SimplePage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FindPaginatedRequestLogTest {

    @MockBean
    private RequestLogRepository requestLogRepository;

    @Autowired
    private FindPaginatedRequestLogCU findPaginatedRequestLogCU;

    @Test
    public void execute_ok_Test() {

        var simplePage = new SimplePage<RequestLog>(
                0,
                10,
                10L,
                100L,
                List.of(new RequestLog(), new RequestLog())
        );

        when(requestLogRepository.find(0, 10)).thenReturn(Mono.just(simplePage));

        StepVerifier.create(findPaginatedRequestLogCU.execute(0, 10))
                .expectNext(simplePage)
                .verifyComplete();

        verify(requestLogRepository, times(1)).find(0, 10);
    }
}
