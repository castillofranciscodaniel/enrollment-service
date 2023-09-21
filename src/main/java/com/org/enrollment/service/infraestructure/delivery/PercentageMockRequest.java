package com.org.enrollment.service.infraestructure.delivery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/percentage")
public class PercentageMockRequest {

    private final Random random;

    public PercentageMockRequest() {
        this.random = new Random();
    }

    @GetMapping
    public Integer findPaginatedRequestLog() {
        var number = random.nextInt(130) + 1;

        if (number > 100) throw new RuntimeException("fake error mock");

        return number;
    }
}
