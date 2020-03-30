package com.client.utils;

import com.client.handler.MessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyClient
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/1
 * @Version V1.0
 **/
@Component
public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "112.126.99.144";
    private static final int PORT = 8084;
    public static Channel channel = null;
    @PostConstruct
    public void run() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("decode", new PacketDecoder_New());
                        ch.pipeline().addLast("encode",new PacketEncoder_New());
                        ch.pipeline().addLast("handler", new MessageHandler());

                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
//            future.addListener(new ChannelFutureListener(){
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                    boolean successed=channelFuture.isSuccess();
//                    if (!successed){
//                        final EventLoop loop=channelFuture.channel().eventLoop();
//                        loop.schedule(new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.println("服务器连接不上，开始重连……");
//                            }
//                        })
//                    }
//                }
//            })

            if (future.isSuccess()) {
                channel = ((ChannelFuture) future).channel();
                System.out.println(new Date() + ": 连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }
}

