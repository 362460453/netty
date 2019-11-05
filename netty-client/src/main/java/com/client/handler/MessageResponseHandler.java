package com.client.handler;

import com.client.entity.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * @ClassName FirstClientHandler
 * @Description: ok
 * @Author yanlin
 * @Date 2019/11/1
 * @Version V1.0
 **/
@Component
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        System.out.println("收到服务端回调channelRead0==========="+messageResponsePacket.getFromClientId() + messageResponsePacket.getMessage());
    }
}

