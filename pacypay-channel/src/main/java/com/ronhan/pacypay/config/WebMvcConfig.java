//package com.ronhan.pacypay.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.filter.CharacterEncodingFilter;
////import org.springframework.web.servlet.config.annotation.EnableWebMvc;
////import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
///**
// * @author Mloong
// * @version 0.0.1
// * <p></p>
// * @since 2021/4/9
// **/
////@EnableWebMvc
//@Configuration
//public class WebMvcConfig extends WebMvcConfigurationSupport {
//    /*@Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
//        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
//    }*/
//
//    @Bean
//    FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean() {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(filter);
//        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//
//}
