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

    @GetMapping
    public void test() {

        Packet ms = new Packet();

        // 创建登录对象
        if (null != NettyServer.channel) {
            iSendCommand.exec(NettyServer.channel, ms);
        }

    }
}
