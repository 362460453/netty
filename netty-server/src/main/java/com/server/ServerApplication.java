package com.server;

import com.server.handler.MessageHandler;
import com.server.service.ISendCommand;
import com.server.utils.Constants;
import com.server.utils.Packet;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /*
    旋转
     */
    @GetMapping("/action/a")
    public void actionA() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {01, 127, 01, 01, 03,//1号，1类型
                01, 01, 05, 00,
                02, 02, 50, 05,
                01, 01, 05, 04};
//        byte[] reqData = {01, (byte) 254, 01, 00, 01, 100};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    @GetMapping("/action/b")
    public void actionB() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {02, 127, 01, 01, 03,//2号，1类型
                01, 01, 05, 00,
                02, 02, 50, 05,
                01, 01, 05, 04};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    @GetMapping("/action/c")
    public void actionC() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {03, 127, 01, 01, 03,//3号，1类型
                01, 01, 05, 00,
                02, 02, 50, 05,
                01, 01, 05, 04};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
    起
     */
    @GetMapping("/up/a")
    public void upA() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {01, 9, 01, 00, 01, 00};//1号，1类型
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);

        iSendCommand.exec(ms);
    }

    @GetMapping("/up/b")
    public void upB() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {02, 9, 01, 00, 01, 00};//2号1类型
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);

        iSendCommand.exec(ms);
    }

    /*
    倒
     */
    @GetMapping("/actionb")
    public void actionb() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {01, (byte) 254, 01, 00, 02, 00};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);

    }

    /*
    显
     */
    @GetMapping("/actionc")
    public void actionc() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {01, (byte) 254, 01, 00, 04, 00};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
    隐
     */
    @GetMapping("/actiond")
    public void actiond() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {01, (byte) 254, 01, 00, 05, 00};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);

        iSendCommand.exec(ms);
    }

    @GetMapping("/query")
    public void query() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {01, 00};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 03);
        iSendCommand.exec(ms);
    }
}
