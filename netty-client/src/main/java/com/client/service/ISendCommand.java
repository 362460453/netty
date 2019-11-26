package com.client.service;

import com.client.utils.Packet;
import io.netty.channel.Channel;

/**
 * @ClassName SendCommand
 * @Description: 发送消息公共接口
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
public interface ISendCommand {
    void exec(Channel channel, Packet packet);
}
