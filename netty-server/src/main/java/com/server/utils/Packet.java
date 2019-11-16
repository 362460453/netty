package com.server.utils;

import io.netty.channel.Channel;
import lombok.Data;

@Data
public class Packet {

    private final Integer head = 42409;//同步头
    private Integer type;//类型
    private Integer length;//长度
    private Byte[] data;//数据包
    private Channel channel;

}
