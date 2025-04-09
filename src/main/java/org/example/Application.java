package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");


        SpringApplication.run(Application.class, args);
        log.info("==================IrifySecLab启动成功================");
    }
}