package com.ronhan.iso8583.discover;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/27
 **/
@ConfigurationProperties(prefix = "iso8583.connection")
@Data
public class ConnectionProperties {

    /**
     * Max connection per route.
     */
    private int max = 5;

    /**
     * Server host and port list.
     */
    private List<Server> servers = new ArrayList<>();

    private boolean enabled = false;

    @Data
    public static class Server {
        /**
         * server host.
         */
        private String host;
        /**
         * server port.
         */
        private int port;
    }
}
