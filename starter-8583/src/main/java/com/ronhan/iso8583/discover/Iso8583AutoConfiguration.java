package com.ronhan.iso8583.discover;

import com.ronhan.iso8583.discover.netty.ServerAddress;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.TraceNumberGenerator;
import com.solab.iso8583.parse.ConfigParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/27
 **/
@Configuration
@EnableConfigurationProperties(ConnectionProperties.class)
public class Iso8583AutoConfiguration {

    private ConnectionProperties properties;

    @Value("${discover.iic}")
    private String IIC;

    @Value("${discover.iic_dc}")
    private String IIC_DC;

    @Value("${discover.zmk.component1}")
    private String com1;

    @Value("${discover.zmk.component2}")
    private String com2;

    @Value("${discover.zmk.component3}")
    private String com3;

    @Value("${discover.zmk.checkValue1}")
    private String chkV1;

    @Value("${discover.zmk.checkValue2}")
    private String chkV2;

    @Value("${discover.zmk.checkValue3}")
    private String chkV3;

    @Value("${discover.zmk.zmkCheckValue}")
    private String zmkChkV;

    @Value("${discover.generator.pre:0}")
    private int pre;

    public Iso8583AutoConfiguration(ConnectionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ServerAddress address() {
        ServerAddress address = new ServerAddress();
        List<InetSocketAddress> serverList = properties.getServers()
                .stream()
                .map(e -> InetSocketAddress.createUnresolved(e.getHost(), e.getPort()))
                .collect(Collectors.toList());
        address.setAddresses(serverList);
        return address;
    }

    @Order()
    @Bean
    @ConditionalOnProperty(prefix = "iso8583.connection", value = "enabled", havingValue = "true")
    public Iso8583FactoryBean iso8583FactoryBean(@Qualifier("discover8583") MessageFactory<IsoMessage> mf,
                                                 MessagePublisher publisher) {
        Iso8583FactoryBean factoryBean = new Iso8583FactoryBean(properties, IIC, IIC_DC, mf, publisher);
        factoryBean.setBeanName("discoverFactoryBean");
        return factoryBean;
    }

    @Bean(name = "discover8583")
    public MessageFactory<IsoMessage> messageFactory(@Autowired TraceNumberGenerator generator) {
        MessageFactory<IsoMessage> mf = null;
        try {
            mf = ConfigParser.createDefault();
            mf.setBinaryFields(false);
            mf.setBinaryHeader(false);
            mf.setForceSecondaryBitmap(true);
            mf.setVariableLengthFieldsInHex(true);
            mf.setUseBinaryBitmap(true);
            mf.setForceStringEncoding(true);
            mf.setCharacterEncoding(StandardCharsets.US_ASCII.displayName());
            mf.setTraceNumberGenerator(generator);
            mf.setEtx(0x03);
            return mf;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public CustomTraceNumberGenerator traceNumberGenerator() {
        CustomTraceNumberGenerator generator = new CustomTraceNumberGenerator(pre, 1);
        return generator;
    }

    @Bean()
    public KeyExchange keyExchange() {
        return new KeyExchange(com1, com2, com3, chkV1, chkV2, chkV3, zmkChkV);
    }

}
