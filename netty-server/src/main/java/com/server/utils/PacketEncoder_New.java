package com.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder_New extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf byteBuf) {
        byteBuf.writeByte(0xa5);//同步头
        byteBuf.writeByte(0xa9);
        byteBuf.writeByte(packet.getType());//类型
        byteBuf.writeByte(packet.getLength());//长度
        byteBuf.writeBytes(packet.getData());//数据包
    }
}
