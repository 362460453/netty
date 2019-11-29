package com.client.handler;

import com.client.utils.Packet;
import com.client.utils.TypeEnum;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * @ClassName FirstServerHandler
 * @Description: 负责读取客户端发来的数据
 * @Author yanlin
 * @Date 2019/11/1
 * @Version V1.0
 **/
@ChannelHandler.Sharable
@Component
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        System.out.println("客户端收到消息回调");
        packet.setChannel(ctx.channel());
        //测试收到的是什么东西
        System.out.println(packet.toString());
//        byte l = packet.getByteBuf().readByte();
//        short i = packet.getByteBuf().readShort();
    }

}
