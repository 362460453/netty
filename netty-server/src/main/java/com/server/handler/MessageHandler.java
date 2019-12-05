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
    public static Channel channel = null;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        channel = ctx.channel();
        System.out.println("服务端收到消息");
        Packet packetResponse = new Packet();
        packetResponse.setType((byte) 2);
        byte[] data = {00, 00};
        packetResponse.setData(data);
        packet.setChannel(ctx.channel());
        packetResponse.setLength((byte) packetResponse.getData().length);
        System.out.println("收到客户端内容：" + packet.toString());
        if (packet.getType() == 0) {
            //控制
            ctx.channel().writeAndFlush(packetResponse);
            byte a = packet.getByteBuf().readByte();//靶机编号
            short b = packet.getByteBuf().readShort();//指令id
            byte c = packet.getByteBuf().readByte();//靶机类型
            byte d = packet.getByteBuf().readByte();//命令字,一定是03
            int e = packet.getByteBuf().readByte();//参数
            System.out.println("控制信号。靶机编号：" + a + ",指令id：" + b + ",靶机类型：" + c + ",命令字（一定是3）：" + d + ",参数：" + e);


        } else if (packet.getType() == 1) {
            //成绩
            ctx.channel().writeAndFlush(packetResponse);
            byte a = packet.getByteBuf().readByte();//靶机编号
            byte b = packet.getByteBuf().readByte();//靶机类型
            byte c = packet.getByteBuf().readByte();//部位
            System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",部位：" + c);
        } else if (packet.getType() == 2) {
            //响应
            short b = packet.getByteBuf().readShort();//结果
            System.out.println("结果：" + b);
        } else if (packet.getType() == 3) {
            //查询
            ctx.channel().writeAndFlush(packetResponse);
            byte a = packet.getByteBuf().readByte();//靶机编号
            byte b = packet.getByteBuf().readByte();//靶请求项
            byte c = packet.getByteBuf().readByte();//状态
            byte d = packet.getByteBuf().readByte();//电量
            byte e = packet.getByteBuf().readByte();//信号
            System.out.println("查询。靶机编号：" + a + ",请求项：" + b + ",状态：" + c + ",电量：" + d + ",信号：" + e);

        }


    }

}
