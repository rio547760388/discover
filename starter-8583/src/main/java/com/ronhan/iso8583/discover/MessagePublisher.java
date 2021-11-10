package com.ronhan.iso8583.discover;

import com.ronhan.iso8583.MessageUtil;
import com.solab.iso8583.IsoMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/21
 **/
public class MessagePublisher {
    private RabbitTemplate rabbitTemplate;

    @Value("${discover.exchange}")
    @Setter
    @Getter
    private String exchange;

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMsg(String channel, IsoMessage message) {
        rabbitTemplate.convertAndSend(exchange, null, MessageUtil.toMap(channel, message));
    }

}
