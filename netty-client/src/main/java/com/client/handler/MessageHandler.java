package com.client.handler;

import com.client.utils.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        System.out.println("客户端收到消息回调");
        packet.setChannel(ctx.channel());
        //测试收到的是什么东西
        System.out.println(packet.toString()+"  "+packet.getByteBuf().getUnsignedByte(5));
//        byte l = packet.getByteBuf().readByte();
//        short i = packet.getByteBuf().readShort();
//        Thread.sleep(7000);
        if (packet.getType() == 1) {
            log.info("客户端收到靶机编号:{},靶机类型:{}",packet.getByteBuf().getUnsignedByte(4),packet.getByteBuf().getUnsignedByte(5));
            byte[] reqData = {00,00};
            Packet packet1 = new Packet();
            packet1.setData(reqData);
            packet1.setLength((byte) reqData.length);
            packet1.setType((byte) 02);
            ctx.channel().writeAndFlush(packet1);
        }
        if (packet.getType() == 3&& packet.getByteBuf().getUnsignedByte(5) == 255) {
            byte[] reqData = {1, (byte) 255, 1, 1};
            Packet packet1 = new Packet();
            packet1.setData(reqData);
            packet1.setLength((byte) reqData.length);
            packet1.setType((byte) 03);
            ctx.channel().writeAndFlush(packet1);
        }
    }

}
