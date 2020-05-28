package com.server.utils;

import com.server.handler.IMIdleStateHandler;
import com.server.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @ClassName NettyServer
 * @Description:
 * @Author yanlin
 * @Date 2019/11/1
 * @Version V1.0
 **/
@Component
public class NettyServer {
    private static final int PORT = 8084;
    @Autowired
    private MessageHandler messageHandler;

    @PostConstruct
    public void run() {
        NioEventLoopGroup boss = new NioEventLoopGroup();//表示监听端口,接受新连接线程，主要负责创建新连接
        NioEventLoopGroup worker = new NioEventLoopGroup();//负责读取每条数据的线程，主要用于读取数据以及业务逻辑处理
        final ServerBootstrap serverBootstrap = new ServerBootstrap();//创建了一个引导类，进行服务端启动工作
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast("decode", new PacketDecoder_New());
                        ch.pipeline().addLast("encode",new PacketEncoder_New());
                        ch.pipeline().addLast("handler", messageHandler);
                    }
                });
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}

