package com.ronhan.config;

import com.ronhan.iso8583.discover.MessagePublisher;
import lombok.Data;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/20
 **/
@ConditionalOnProperty(prefix = "iso8583.connection", value = "enabled", havingValue = "true")
@ConfigurationProperties(prefix = "iso8583.message")
@Configuration(proxyBeanMethods = false)
@Data
public class RabbitConfig {

    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;
    private int size;

    @Value("${discover.exchange}")
    private String exchange;

    @Value("${discover.queue}")
    private String discoverQueue;

    @Bean(name = "rabbitFactory-8583")
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setChannelCacheSize(size);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "rabbit-8583")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());

        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean(name = "rabbitAdmin-8583")
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    @Order(HIGHEST_PRECEDENCE)
    public MessagePublisher messagePublisher(RabbitTemplate rabbitTemplate) {
        return new MessagePublisher(rabbitTemplate);
    }
}
