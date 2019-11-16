package com.server.utils;

import com.alibaba.fastjson.annotation.JSONField;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
//    @JSONField(deserialize = false, serialize = false)
//    private Byte version = 1;
    private Integer head = 42409;//同步头
    private Integer type;//类型
    private Integer length;//长度
    private Byte[] data;//数据包
    private Channel channel;
//    @JSONField(serialize = false)
//    public abstract Byte getCommand();
//    public Byte getVersion() {
//        return version;
//    }
//
//    public void setVersion(Byte version) {
//        this.version = version;
//    }
}
