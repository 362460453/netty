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
            short b = packet.getByteBuf().readByte();//指令id
            byte c = packet.getByteBuf().readByte();//靶机类型
            byte d = packet.getByteBuf().readByte();//命令字,一定是02
            int e = packet.getByteBuf().readByte();//参数
            System.out.println("控制信号。靶机编号：" + a + ",指令id：" + b + ",靶机类型：" + c + ",命令字（一定是2）：" + d + ",参数：" + e);

        } else if (packet.getType() == 1) {
            //成绩
            ctx.channel().writeAndFlush(packetResponse);
            byte a = packet.getByteBuf().readByte();//靶机编号
            int b = packet.getByteBuf().readByte();//靶机类型
            short c = packet.getByteBuf().readUnsignedByte();//部位
            int hight = c >> 4;
            int low = c & 0x0f;
            if (b == 1) {//环靶
                System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + hight + "环 " + low + "点 ");
            } else if (b == 5) {//红外
                System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",触发红外：" + c);
            } else {//分区
                System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + c);
            }

        } else if (packet.getType() == 2) {
            //响应
            short b = packet.getByteBuf().readByte();//结果
            System.out.println("结果：" + b);
        } else if (packet.getType() == 3) {
            //查询
            ctx.channel().writeAndFlush(packetResponse);
            byte a = packet.getByteBuf().readByte();//靶机编号
            byte b = packet.getByteBuf().readByte();//靶请求项
            byte c = packet.getByteBuf().readByte();//状态
            int d = packet.getByteBuf().readUnsignedByte();//电量
            int e = packet.getByteBuf().readUnsignedByte();//信号
            System.out.println("查询。靶机编号：" + a + ",请求项：" + b + ",状态：" + c + ",电量：" + d + ",信号：" + e);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
//        super.channelActive(ctx);
    }

    /**
     * byte数组转换为无符号short整数
     *
     * @param bytes byte数组
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes) {
        return byte2ToUnsignedShort(bytes, 0);
    }

    /**
     * byte数组转换为无符号short整数
     *
     * @param bytes byte数组
     * @param off   开始位置
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes, int off) {
        int high = bytes[off];
        int low = bytes[off + 1];
        return (high << 8 & 0xFF00) | (low & 0xFF);
    }
}
