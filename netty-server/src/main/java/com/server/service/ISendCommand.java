package com.server.service;

import com.server.entity.MessageRequestPacket;
import com.server.entity.MessageResponsePacket;
import io.netty.channel.Channel;

/**
 * @ClassName SendCommand
 * @Description: 发送消息公共接口
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
public interface ISendCommand {
    void exec(Channel channel, MessageResponsePacket resp);
}
