package com.server.handler;

import com.server.service.IClientManage;
import com.server.utils.Packet;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    IClientManage iClientManage;

    /*
    当前channel从远端读取到数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Packet packetResponse = new Packet();
        packetResponse.setType((byte) 2);
        byte[] data = {00, 00};
        packetResponse.setData(data);
        packet.setChannel(ctx.channel());
        packetResponse.setLength((byte) packetResponse.getData().length);
        log.info("收到客户端内容：" + packet.toString());
        //收到查询注册信息信号
        if (packet.getType() == 3 && packet.getByteBuf().getUnsignedByte(5) == 255) { //code_type
            String equipmentMapKey = packet.getByteBuf().getUnsignedByte(6) +
                    "_" + packet.getByteBuf().getUnsignedByte(7);
//            IClientManage iClientManage = SpringContextHolder.getBean("clientManageImpl");
            iClientManage.clientRegisterCallback(ctx.channel(), equipmentMapKey);
        } else {
            if (packet.getType() == 0) {
                //控制
                ctx.channel().writeAndFlush(packetResponse);
                byte a = packet.getByteBuf().readByte();//靶机编号
                short b = packet.getByteBuf().readUnsignedByte();//指令id
                byte c = packet.getByteBuf().readByte();//靶机类型
                byte d = packet.getByteBuf().readByte();//命令字,一定是02
                int e = packet.getByteBuf().readByte();//参数
                System.out.println("控制信号。靶机编号：" + a + ",指令id：" + b + ",靶机类型：" + c + ",命令字（一定是2）：" + d + ",参数：" + e);
                log.info("控制信号。靶机编号：" + a + ",指令id：" + b + ",靶机类型：" + c + ",命令字（一定是2）：" + d + ",参数：" + e);
            } else if (packet.getType() == 1) {
                //成绩
                ctx.channel().writeAndFlush(packetResponse);
                byte a = packet.getByteBuf().readByte();//靶机编号
                int b = packet.getByteBuf().readByte();//靶机类型
                short c = packet.getByteBuf().readUnsignedByte();//部位
                int hight = c >> 4;
                int low = c & 0x0f;
                if (b == 1) {//环靶
                    log.info("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + hight + "环 " + low + "点 ");
                    System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + hight + "环 " + low + "点 ");
                } else if (b == 5) {//红外
                    log.info("成绩。靶机编号：" + a + ",靶机类型：" + b + ",触发红外：" + c);

                    System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",触发红外：" + c);
                } else {//分区
                    log.info("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + c);
                    System.out.println("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + c);
                }
            } else if (packet.getType() == 2) {
                //响应
                short b = packet.getByteBuf().readByte();//结果
                log.info("结果：" + b);
            } else if (packet.getType() == 3) {
                //查询
                ctx.channel().writeAndFlush(packetResponse);
                byte a = packet.getByteBuf().readByte();//靶机编号
                byte b = packet.getByteBuf().readByte();//靶请求项
                byte c = packet.getByteBuf().readByte();//状态
                int d = packet.getByteBuf().readUnsignedByte();//电量
                int e = packet.getByteBuf().readUnsignedByte();//信号
                log.info("查询。靶机编号：" + a + ",请求项：" + b + ",状态：" + c + ",电量：" + d + ",信号：" + e);
            }
        }
    }

    /*
    当前channel激活的时候
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        iClientManage.clientChannelActive(ctx.channel());
    }

    /*
    channel出现异常会触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /*
     * 当前channel不活跃的时候，也就是当前channel到了它生命周期末
     * 断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        iClientManage.kickOutClient(ctx.channel());
    }
}
