package com.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.server.handler.MessageHandler;
import com.server.service.ISendCommand;
import com.server.utils.Constants;
import com.server.utils.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName SendSignImpl
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
@Service
@Slf4j
public class SendSignImpl implements ISendCommand {
    @Override
    public void exec(Packet packet) throws Exception {
        String equipmentMapKey;
        if (packet.getType() == 3) {
            equipmentMapKey = packet.getEquipmentMapKey();
        } else {
            String targetType;//1靶机，5红外
            if ((int) packet.getData()[2] == 5) {
                targetType = "5";
            } else {
                targetType = "1";
            }
            equipmentMapKey = (int) packet.getData()[0] + "_" + targetType;
        }
        log.info("SendSignImpl.equipmentMapKey:{}", equipmentMapKey);
        String channelId = Constants.equipmentMap.get(equipmentMapKey);
        if (StrUtil.isEmpty(channelId)){
            throw new Exception("无法发送信号,器材未注册");
        }
        Channel channel = Constants.channelMap.get(channelId);
        if (channel == null) {
            throw new Exception("无法发送信号，管道为空");
        }
        try {
            channel.writeAndFlush(packet);
        } catch (Exception e) {
            throw new Exception("发送信号未知异常:" + e.getMessage());
        }
    }

}
