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

    /*
    2号机器旋转
     */
    @GetMapping("/action/b")
    public void actionB() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {02, 127, 01, 01, 03,//2号，1类型
                01, 01, 05, 01,
                02, 02, 50, 05,
                01, 01, 05, 04};
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
    3号机器旋转
     */
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
    机器起，延迟20秒
     */
    @GetMapping("/up/a/{id}")
    public void upA(@PathVariable("id") Integer id) throws Exception {
        Packet ms = new Packet();
        byte[] reqData;
        if (id == 1) {
            reqData = new byte[]{1, 9, 1, 0, 1, (byte) 200};//1号，环，1类型
        } else if (id == 2) {
            reqData = new byte[]{2, 9, 1, 0, 1, (byte) 200};//1号，环，1类型
        } else if (id == 3) {
            reqData = new byte[]{3, 9, 1, 0, 1, (byte) 200};//1号，环，1类型
        } else {
            reqData = new byte[]{4, 9, 1, 0, 1, (byte) 200};//1号，环，1类型
        }
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
    靶机起延迟5s
     */
    @GetMapping("/up/b/{id}")
    public void upB(@PathVariable("id") Integer id) throws Exception {
        Packet ms = new Packet();
        byte[] reqData;
        if (id == 1) {
            reqData = new byte[]{1, 9, 1, 0, 1, 50};//1号，环，1类型
        } else if (id == 2) {
            reqData = new byte[]{2, 9, 1, 0, 1, 50};//1号，环，1类型
        } else if (id == 3) {
            reqData = new byte[]{3, 9, 1, 0, 1, 50};//1号，环，1类型
        } else {
            reqData = new byte[]{4, 9, 1, 0, 1, 50};//1号，环，1类型
        }
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
    倒，延迟5s
     */
    @GetMapping("/down/b/{id}")
    public void downA(@PathVariable("id") Integer id) throws Exception {
        Packet ms = new Packet();
        byte[] reqData;
        if (id == 1) {
            reqData = new byte[]{1, 9, 1, 0, 2, 50};//1号，环，1类型
        } else if (id == 2) {
            reqData = new byte[]{2, 9, 1, 0, 2, 50};//1号，环，1类型
        } else if (id == 3) {
            reqData = new byte[]{3, 9, 1, 0, 2, 50};//1号，环，1类型
        } else {
            reqData = new byte[]{4, 9, 1, 0, 2, 50};//1号，环，1类型
        }
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
   倒，延迟20s
    */
    @GetMapping("/down/c/{id}")
    public void downB(@PathVariable("id") Integer id) throws Exception {
        Packet ms = new Packet();
        byte[] reqData;
        if (id == 1) {
            reqData = new byte[]{1, 9, 1, 0, 2, (byte) 200};//1号，环，1类型
        } else if (id == 2) {
            reqData = new byte[]{2, 9, 1, 0, 2, (byte) 200};//2号，环，1类型
        } else if (id == 3) {
            reqData = new byte[]{3, 9, 1, 0, 2, (byte) 200};//3号，环，1类型
        } else {
            reqData = new byte[]{4, 9, 1, 0, 2, (byte) 200};//4号，环，1类型
        }
        ms.setData(reqData);
        ms.setLength((byte) reqData.length);
        ms.setType((byte) 00);
        iSendCommand.exec(ms);
    }

    /*
    显，延迟5s
     */
    @GetMapping("/actionc")
    public void actionc() throws Exception {
        Packet ms = new Packet();
        byte[] reqData = {2, (byte) 254, 01, 00, 04, 50};//2号
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
        byte[] reqData = {2, (byte) 254, 01, 00, 05, 50};//2号
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
        ms.setEquipmentMapKey("2_1");
        iSendCommand.exec(ms);
    }
}
