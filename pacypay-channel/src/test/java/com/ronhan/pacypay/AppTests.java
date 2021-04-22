package com.ronhan.pacypay;


import com.ronhan.pacypay.handler.ClientHandler;
import com.ronhan.pacypay.handler.ServerHandler;
import com.ronhan.pacypay.handler.ServerReader;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.stream.Stream;

//@SpringBootTest
class AppTests {

    //@Test
    void contextLoads() {
    }

    @Test
    void nettyServer() {
        MessageFactory<IsoMessage> messageFactory = new MessageFactory<>();
        messageFactory.setCharacterEncoding("UTF-8");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel serverSocketChannel) throws Exception {
                            serverSocketChannel.pipeline()
                                    .addLast(
                                            //new ServerReader()
                                            new StringDecoder(StandardCharsets.UTF_8),
                                            new ServerReader(),
                                            new ServerHandler()

                                            //,
                                            //new ServerHandler()
                                            //new Iso8583Decoder(messageFactory)
                                    );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .bind(7000)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Test
    void netty() {
        MessageFactory<IsoMessage> messageFactory = new MessageFactory<>();
        messageFactory.setCharacterEncoding("UTF-8");

        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(
                                            new ClientHandler()
                                            //new ClientEncoder(messageFactory)//,
                                            //new Iso8583Decoder(messageFactory),
                                            //new Iso8583Encoder()
                                    );
                        }
                    })
                    //.option(ChannelOption.AUTO_READ, true)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture cf = bootstrap.connect("localhost", 3001)
                    .sync();

            //cf.channel().pipeline().writeAndFlush("33233aaaafefgegaw");

            cf.channel()
                    .closeFuture().addListener(f -> {
                        System.out.println("close");
            })
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    @Test
    public void test() throws InterruptedException {
        IsoMessage isoMessage = new IsoMessage();
        isoMessage.setType(1804);
        isoMessage.setBinaryHeader(false);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        isoMessage.setValue(7, DateFormatUtils.format(cal.getTime(), "MMddhhmmss"), IsoType.NUMERIC, 10);
        isoMessage.setValue(11, System.currentTimeMillis() % 99999 + 100000, IsoType.NUMERIC, 6);
        isoMessage.setValue(12, DateFormatUtils.format(cal.getTime(), "MMddhhmmss"), IsoType.NUMERIC, 12);
        isoMessage.setValue(24, 831, IsoType.NUMERIC, 3);
        isoMessage.setValue(93, "00000367561", IsoType.LLLVAR, 11);
        isoMessage.setValue(94, "00000367561", IsoType.LLLVAR, 11);
        byte[] bfs = isoMessage.writeData();
        Integer a;
        for (byte b : bfs) {
            System.out.print(b);
        }

        System.out.println("---");
        for (byte b : bfs) {
            System.out.print(Integer.toHexString(b));
        }

        System.out.println("---");
        for (byte b : bfs) {
            System.out.print(new String(new byte[]{b}, StandardCharsets.US_ASCII));
        }

    }
}
