package com.ronhan.pacypay.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/7
 **/
@RestController
public class MainEndpoint {

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "main")
    public String main() {
        //String msg = req.getParameter("msg");
        //System.out.println(msg);
        String result = cbFactory.create("hello").run(() ->

                        restTemplate.getForObject("https://www.baidu.com", String.class)

                ,
                ex -> {
                    ex.printStackTrace();
                    return null;
                }
        );
        System.out.println(result);
        return result;
    }
}
