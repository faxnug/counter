package com.why.counter;

import com.why.counter.config.CounterConfig;
import com.why.counter.thirdpart.uuid.WhyUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CounterApplication {

    @Autowired
    private CounterConfig counterConfig;

    @PostConstruct
    private void init(){
        WhyUuid.getInstance().init(counterConfig.getDataCenterId(), counterConfig.getWorkerId());
    }

    public static void main(String[] args) {
        SpringApplication.run(CounterApplication.class, args);
    }

}
