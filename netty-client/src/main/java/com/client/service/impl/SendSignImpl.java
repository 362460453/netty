package com.client.service.impl;

import com.client.service.ISendCommand;
import com.client.utils.Packet;
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
    public void exec(Channel channel, Packet packet) {
        System.out.println("客户端执行发送信号");
        channel.writeAndFlush(packet);
    }

}
