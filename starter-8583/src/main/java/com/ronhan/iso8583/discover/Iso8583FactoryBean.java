package com.ronhan.iso8583.discover;

import com.ronhan.iso8583.discover.netty.Iso8583PoolHandler;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/28
 **/
@Slf4j
public class Iso8583FactoryBean extends AbstractFactoryBean<AbstractChannelPoolMap<InetSocketAddress, ChannelPool>> implements BeanNameAware {

    private ConnectionProperties properties;

    private String IIC;

    private String IIC_DC;

    private String beanName;

    private MessageFactory<IsoMessage> mf;

    private MessagePublisher publisher;

    public Iso8583FactoryBean(ConnectionProperties properties, String IIC, String IIC_DC, MessageFactory<IsoMessage> mf, MessagePublisher publisher) {
        super();
        this.properties = properties;
        this.IIC = IIC;
        this.IIC_DC = IIC_DC;
        this.mf = mf;
        this.publisher = publisher;
    }

    @Override
    public Class<?> getObjectType() {
        return AbstractChannelPoolMap.class;
    }

    @Override
    protected AbstractChannelPoolMap<InetSocketAddress, ChannelPool> createInstance() throws Exception {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup);
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.AUTO_READ, true);

        return new AbstractChannelPoolMap<InetSocketAddress, ChannelPool>() {
            @Override
            protected ChannelPool newPool(InetSocketAddress address) {
                return new FixedChannelPool(bootstrap.remoteAddress(address),
                        new Iso8583PoolHandler(IIC, IIC_DC, mf, publisher),
                        ChannelHealthChecker.ACTIVE,
                        FixedChannelPool.AcquireTimeoutAction.FAIL,
                        5000,
                        properties.getMax(),
                        3,
                        true,
                        true
                );
            }
        };
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    @Override
    protected void destroyInstance(AbstractChannelPoolMap<InetSocketAddress, ChannelPool> instance) throws Exception {
        if (instance != null) {
            instance.close();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        List<InetSocketAddress> serverList = properties.getServers()
                .stream()
                .map(e -> InetSocketAddress.createUnresolved(e.getHost(), e.getPort()))
                .collect(Collectors.toList());
        serverList.forEach(address -> {
            try {
                AbstractChannelPoolMap<InetSocketAddress, ChannelPool> instance = super.getObject();
                if (instance != null) {
                    ChannelPool pool = instance.get(address);
                    pool.acquire().addListener(f -> {
                        if (f.isSuccess()) {
                            Channel channel = (Channel) f.get();
                            pool.release(channel);
                        } else {
                            log.error("获取连接失败{}", address);
                        }
                    });
                }
            } catch (Exception e) {
                log.error("init err", e);
            }
        });
    }

}
