package com.server.handler;

import com.server.utils.Constants;
import com.server.utils.Packet;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

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
            log.info("code_type:{}", equipmentMapKey);
            Constants.equipmentMap.put(equipmentMapKey, ctx.channel().id().asShortText());//设备和channelId对应关系
            log.info("equipmentMap:{}", Constants.equipmentMap.toString());
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
        log.info("channel_Id : " + ctx.channel().id().asShortText());
        String channelId=ctx.channel().id().asShortText();
        Constants.channelMap.put(channelId, ctx);//channelId和channel对应关系
        Packet packetResponse = new Packet();
        packetResponse.setType((byte) 3);
        byte[] data = {1, (byte) 255};
        packetResponse.setData(data);
        packetResponse.setLength((byte) packetResponse.getData().length);
        Thread.sleep(3000);
        ctx.channel().writeAndFlush(packetResponse);
        log.info("channelMap:{}", Constants.channelMap.toString());
        super.channelActive(ctx);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!(Constants.equipmentMap.containsValue(channelId))){
//                    log.info("while循环发送channelId:{}", channelId);
//                    ctx.channel().writeAndFlush(packetResponse);
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

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
        super.channelInactive(ctx);
        String channelId = ctx.channel().id().asShortText();
        String equipmentMapKey = null;
        for (Map.Entry<String, String> str : Constants.equipmentMap.entrySet()) {
            if (channelId.equals(str.getValue())) {
                equipmentMapKey = str.getKey();
            }
        }
        if (equipmentMapKey != null) {
            // 清理缓存
            log.info("设备 :" + equipmentMapKey + " 主 动 离 线");
            Constants.channelMap.remove(channelId);
            Constants.equipmentMap.remove(equipmentMapKey);
        }
        ctx.close();
        log.info("地址 :" + ctx.channel().remoteAddress() + " 的设备离 线");
    }
}
