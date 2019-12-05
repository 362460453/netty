package com.client;

import com.client.service.ISendCommand;
import com.client.utils.NettyClient;
import com.client.utils.Packet;
import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ClientApplication
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/3
 * @Version V1.0
 **/
@SpringBootApplication
@RequestMapping("/send")
@RestController
public class ClientApplication {
    @Autowired
    ISendCommand iSendCommand;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @GetMapping("/score")
    public void score() {
        Packet ms = new Packet();
        byte[] data = {01, 01, (byte) 91};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 01);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }

    @GetMapping("/action")
    public void action() {
        Packet ms = new Packet();
        byte[] data = {01, 39, 16, 01, 03, 01};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 00);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }
}
