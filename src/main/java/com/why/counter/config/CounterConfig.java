package com.why.counter.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author WHY
 * @Date 2021-01-15
 * @Version 1.0
 */
@Getter
@Component
public class CounterConfig {
    @Value("${counter.dataCenterId}")
    private long dataCenterId;

    @Value("${counter.workerId}")
    private long workerId;
}
