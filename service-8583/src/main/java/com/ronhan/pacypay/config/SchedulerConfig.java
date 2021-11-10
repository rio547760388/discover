package com.ronhan.pacypay.config;

import com.ronhan.pacypay.scheduler.DailyRecap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
@ConditionalOnProperty(prefix = "discover", value = "scheduler", havingValue = "true")
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public DailyRecap dailyRecap() {
        DailyRecap dailyRecap = new DailyRecap();
        return dailyRecap;
    }
}
