package com.server.handler;

import com.server.utils.Packet;
import com.server.utils.TypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
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
        System.out.println("服务端收到消息");
        //1.先发送成功,成功的信号是packet对象，只有一个字节 type是响应 ，只不过data是一个字节的回复
        Packet packetResponse = new Packet();
        packetResponse.setType((byte) 2);

//        byte[] data = {01, 39, 16, 01, 02, 01, 01, 00, 14, 00, 64};
        byte[] data = {0xa,0x14};
        packetResponse.setData(data);
        packetResponse.setLength((byte) packetResponse.getData().length);
        ctx.channel().writeAndFlush(packetResponse);
        //2.根据packet 里面的type用枚举分发不同处理器
        //测试收到的是什么东西
        packet.setChannel(ctx.channel());
        System.out.println(packet.toString());

        byte l = packet.getByteBuf().readByte();
        short i = packet.getByteBuf().readShort();
        byte j = packet.getByteBuf().readByte();
        byte q = packet.getByteBuf().readByte();
        int k = packet.getByteBuf().readByte();
        System.out.println(l);
        System.out.println(i);
        System.out.println(j);
        System.out.println(q);
        System.out.println(k);


    }

}
