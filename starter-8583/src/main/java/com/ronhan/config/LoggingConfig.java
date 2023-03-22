package com.ronhan.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2023/3/22
 **/
public class LoggingConfig {
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        return rabbitTemplate;
    }
}
