package com.ronhan.pacypay.netty;

import com.ronhan.pacypay.handler.ClientHandler;
import com.ronhan.pacypay.handler.ClientWriter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/21
 **/
@Slf4j
public class ChannelPoolImpl implements ChannelPool {

    /**
     * 0-new, 1-initiating, 2-available, 3-stopping，4-stopped
     */
    private volatile byte status = 0;

    private ArrayBlockingQueue<Channel> idleChannels = new ArrayBlockingQueue<>(100);

    private ArrayBlockingQueue<Channel> workingChannels = new ArrayBlockingQueue<>(100);

    private ReentrantLock lock = new ReentrantLock();
    private NioEventLoopGroup loop = new NioEventLoopGroup();

    private ServerConfig config;

    public ChannelPoolImpl(ServerConfig config) {
        this.config = config;
    }

    @Override
    public int status() {
        return 0;
    }

    @Override
    public Channel get() throws TimeoutException {
        lock.lock();
        try {
            Channel channel = idleChannels.poll(3000, TimeUnit.MILLISECONDS);
            workingChannels.put(channel);
            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new TimeoutException("获取channel超时");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean start() {
        lock.lock();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loop);
            bootstrap.channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ClientHandler())
                            .addLast(new ClientWriter())
                            ;
                }
            });
            for (int i = 0; i < config.getMin(); i++) {
                Channel channel = bootstrap.connect(config.getHost(), config.getPort()).sync().channel();
                idleChannels.put(channel);
            }
            status = 1;

            return check() || destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean release(Channel channel) {
        lock.lock();
        try {
            workingChannels.remove(channel);
            idleChannels.put(channel);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    private boolean check() {
        lock.lock();
        try {
            if (status == 1) {
                if (!idleChannels.isEmpty()) {
                    status = 2;
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    private boolean destroy() {
        lock.lock();
        try {
            status = 3;
            loop.shutdownGracefully().addListener(f -> {
                log.info("shutdown netty eventLoop");
            }).sync();
            idleChannels.forEach(Channel::close);
            workingChannels.forEach(c -> {
                NioSocketChannel sc = (NioSocketChannel) c;
                sc.shutdown();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        //always false
        return false;
    }
}
