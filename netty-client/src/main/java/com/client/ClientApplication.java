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


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ClientApplication.class, args);
    }

    private static final Object obj = new Object();
    /*
      心跳
         */
    @GetMapping("/imidle")
    public void imidle() {
        Packet ms = new Packet();
        byte[] data = {00};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 2);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }
    /*
    注册a
     */
    @GetMapping("/score/a")
    public void scoreA() {
        Packet ms = new Packet();
        byte[] data = {1, 1, (byte) 255};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 1);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }

    /*
        注册b
         */
    @GetMapping("/score/b")
    public void scoreB() {
        Packet ms = new Packet();
        byte[] data = {2, 1, (byte) 255};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 1);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }

    @GetMapping("/action")
    public void action() {
        Packet ms = new Packet();
        byte[] data = {(byte) 200, (byte) 255, 1, 2, 1};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 00);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }
    @GetMapping("/interrupt")
    public void interrupt() {
        Packet ms = new Packet();
        byte[] data = {1, 1, (byte) 145};
        ms.setData(data);
        ms.setLength((byte) data.length);
        ms.setType((byte) 1);
        if (null != NettyClient.channel) {
            iSendCommand.exec(NettyClient.channel, ms);
        }
    }
}
