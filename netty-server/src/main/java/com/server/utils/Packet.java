package com.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Packet {

    //    private Integer head = 42409;//同步头
    private byte type;//类型
    private byte length;//长度
    private byte[] data;//数据包
    private ByteBuf byteBuf;//byteBuf
    private Channel channel;
    private String equipmentMapKey;//查询状态时set

    public Packet() {
    }

    public Packet(byte type, byte[] data) {
        this.type = (type);
        this.data = (data);
        this.length = ((byte) data.length);
    }
}
