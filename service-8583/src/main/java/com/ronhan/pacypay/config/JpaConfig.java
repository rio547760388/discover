package com.ronhan.pacypay.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/7
 **/
@Configuration(proxyBeanMethods = false)
public class JpaConfig {

    @Bean
    public JPAQueryFactory queryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
