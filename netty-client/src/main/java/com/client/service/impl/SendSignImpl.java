package com.client.service.impl;

import com.client.entity.MessageRequestPacket;
import com.client.handler.MessageResponseHandler;
import com.client.service.ISendCommand;
import com.client.utils.PacketCodeC;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @ClassName SendSignImpl
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
@Service
public class SendSignImpl implements ISendCommand {
    @Override
    public void exec(Channel channel, MessageRequestPacket ms) {
        System.out.println("执行发送信号");
        channel.writeAndFlush(ms);
    }

}
