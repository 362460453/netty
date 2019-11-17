package com.server.utils;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Packet {

    //private Integer head = 42409;//同步头
    private Integer type;//类型
    private Integer length;//长度
    private Byte[] data;//数据包
    private Channel channel;

}
