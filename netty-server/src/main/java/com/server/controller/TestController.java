package com.server.controller;

import com.server.utils.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Author yanlin
 * @Date 2020/2/26
 * @Version V1.0
 **/
@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping("/channelMap")
    public void channelMap() {
        System.out.println("channelMap----" + Constants.channelMap.toString());
    }

    @GetMapping("/equipmentMap")
    public void equipmentMap() {
        System.out.println("equipmentMap----" + Constants.equipmentMap.toString());
    }

    public static void main(String[] args) {
        System.err.println(Constants.equipmentMap.keySet().size());
    }
}
