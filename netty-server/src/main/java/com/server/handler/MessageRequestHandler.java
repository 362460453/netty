package com.server.handler;

import com.server.entity.MessageRequestPacket;
import com.server.entity.MessageResponsePacket;
import io.netty.channel.*;

/**
 * @ClassName FirstServerHandler
 * @Description: 负责读取客户端发来的数据
 * @Author yanlin
 * @Date 2019/11/1
 * @Version V1.0
 **/
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    //构造单例
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 1.拿到消息发送方的会话信息

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromClientId(messageRequestPacket.getToClientId());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        System.out.println("服务端的channelRead0");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }

}
