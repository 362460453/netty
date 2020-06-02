package com.server.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Packet {
    private short head;//同步头
    private byte type;//类型
    private byte length;//长度
    private byte[] data;//数据包
    private String equipmentMapKey;//查询状态时set/发送动作信号时获取管道

    public Packet() {
    }

    public Packet(byte type, byte[] data) {
        this.type = (type);
        this.data = (data);
        this.length = ((byte) data.length);
    }
}
