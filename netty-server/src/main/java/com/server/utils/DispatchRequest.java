package com.server.utils;

import com.server.entity.ReqBO;
import com.server.service.IClientManage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 做请求转发
 * @Author yanlin
 * @Date 2020/6/1
 * @Version V1.0
 **/
//做请求转发
@Component
@Slf4j
public class DispatchRequest {
    @Autowired
    PacketToPOJOUtil packetToPOJOUtil;
    @Autowired
    IClientManage iClientManage;

    public static void dispatchRequest(ChannelHandlerContext ctx, Packet packet) {
        SpringContextHolder.getBean(DispatchRequest.class).dispatch(ctx, packet);
    }

    /**
     * @return void
     * @Author yanlin
     * @Description 根据type及转成pojo的部分参数进行分发
     * @Date 11:40 上午 2020/6/1
     * @Param [ctx, packet]
     **/
    @Async
    public void dispatch(ChannelHandlerContext ctx, Packet packet) {
        ReqBO reqBO = packetToPOJOUtil.convert(packet);
        log.info("服务端接收信号reqBO:{}", reqBO.toString());
        boolean isDrill = false;
        //收到查询注册信息信号
        if (packet.getType() == 3 && reqBO.getCommand() == 255) { //code_type
            String equipmentMapKey = reqBO.getEquipmentCode() + "_" + reqBO.getTargetType();
            iClientManage.clientRegisterCallback(ctx.channel(), equipmentMapKey);
        } else {
            if (packet.getType() == 0) {
                //控制
                int a = reqBO.getEquipmentCode();//靶机编号
                int b = reqBO.getDirectiveId();//指令id
                int c = reqBO.getTargetType();//靶机类型
                int d = reqBO.getCommand();//命令字,一定是02
                int e = reqBO.getAction();//参数
                log.info("控制信号。靶机编号：" + a + ",指令id：" + b + ",靶机类型：" + c + ",命令字（一定是2）：" + d + ",参数：" + e);
            } else if (packet.getType() == 1) {
                //成绩
                int a = reqBO.getEquipmentCode();//靶机编号
                int b = reqBO.getTargetType();//靶机类型
                short c = reqBO.getParts();//部位
                int hight = c >> 4;
                int low = c & 0x0f;
                if (b == 1) {//环靶
                    log.info("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + hight + "环 " + low + "点 ");
                } else if (b == 5) {//红外
                    log.info("成绩。靶机编号：" + a + ",靶机类型：" + b + ",触发红外：" + c);
                } else {//分区
                    log.info("成绩。靶机编号：" + a + ",靶机类型：" + b + ",命中：" + c);
                }
            } else if (packet.getType() == 2) {
                //响应
            } else if (packet.getType() == 3) {
                //查询
                int c = reqBO.getState();//状态
                int d = reqBO.getElectric();//电量
                int e = reqBO.getDBm();//信号
                List<String> list= Constants.getKey(Constants.equipmentMap,ctx.channel().id().asShortText());
                log.info("查询器材list:{}。状态：" + c + ",电量：" + d + ",信号：" + e,list.toString());
            }
        }

    }

}
