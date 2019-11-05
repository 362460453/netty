package com.server.service.impl;

import com.server.entity.MessageRequestPacket;
import com.server.entity.MessageResponsePacket;
import com.server.service.ISendCommand;
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
    public void exec(Channel channel, MessageResponsePacket ms) {
        System.out.println("服务端执行发送信号");
        channel.writeAndFlush(ms);
    }

}
