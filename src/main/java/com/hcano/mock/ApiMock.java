package com.hcano.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApiMock {

    public Integer mockPost(Integer num) {
        if (num % 2 == 0) {
            log.error("Error, for num {}", num);
            throw new RuntimeException("Error calling post API");
        }

        return num;
    }

    public Integer mockPostSlow(Integer num) {
        try {
            Thread.sleep(num * 1000);
            return num;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
