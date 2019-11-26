package com.client;

import com.client.service.ISendCommand;
import com.client.utils.NettyClient;
import com.client.utils.Packet;
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
        Packet ms = new Packet();
        Byte[] data={12,34,01,01,00,14,00,64};
        ms.setData(data);
        ms.setLength(01);
        ms.setType(00);
        // 创建登录对象
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }

    }
}
