package com.client;

import com.client.entity.MessageRequestPacket;
import com.client.service.ISendCommand;
import com.client.utils.NettyClient;
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

    @GetMapping
    public void test() {
        MessageRequestPacket ms = new MessageRequestPacket();
        ms.setToClientId("001");
        ms.setMessage("胖虎");
        // 创建登录对象
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }

    }
}
