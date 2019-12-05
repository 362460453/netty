package com.server;

import com.server.handler.MessageHandler;
import com.server.service.ISendCommand;
import com.server.utils.NettyServer;
import com.server.utils.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ServerApplication
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/3
 * @Version V1.0
 **/
@SpringBootApplication
@RequestMapping("/sendcli")
@RestController
public class ServerApplication {
    @Autowired
    ISendCommand iSendCommand;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @GetMapping("/action")
    public void action() {
        Packet ms = new Packet();
        byte[] reqData = {01, 39, 16, 01, 02, 01, 01, 00, 14, 00, 64};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        if (null != MessageHandler.channel) {
            ms.setChannel(MessageHandler.channel);
            iSendCommand.exec(ms.getChannel(), ms);
        }
    }

    @GetMapping("/query")
    public void query() {
        Packet ms = new Packet();
        byte[] reqData = {01, 00};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 03);
        if (null != MessageHandler.channel) {
            ms.setChannel(MessageHandler.channel);
            iSendCommand.exec(ms.getChannel(), ms);
        }
    }
}
