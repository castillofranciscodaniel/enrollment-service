package com.org.enrollment.service.core.usecase;

import com.org.enrollment.service.core.model.ExternalValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateCUTest {

    @MockBean
    private CalculateService calculateService;

    @Autowired
    private CalculateCU calculateCU;

    @Test
    public void execute_ok_Test() {
        var numberA = 5.0;
        var numberB = 10.0;

        var result = 16.5;

        var externalValue = new ExternalValue(10.0);


        when(calculateService.findPercentage()).thenReturn(Mono.just(externalValue));

        StepVerifier.create(calculateCU.execute(numberA, numberB))
                .expectNext(result)
                .verifyComplete();

        verify(calculateService, times(1)).findPercentage();
        verify(calculateService, times(1)).saveAsyncRequestLog(any());
    }

}
