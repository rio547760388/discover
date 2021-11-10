package com.ronhan.pacypay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author Mloong
 */
@SpringBootApplication
@EnableRetry()
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
