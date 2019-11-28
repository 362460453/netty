package com.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder_New extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf byteBuf) {


//        // 3. 实际编码过程
        byteBuf.writeByte(0xa5);//同步头
        byteBuf.writeByte(0xa9);
        byteBuf.writeBytes(NumConvertUtil.intToByte(packet.getType()));//类型
        byteBuf.writeBytes(NumConvertUtil.intToByte(packet.getLength()));//长度
        byteBuf.writeBytes(packet.getData());//数据包

    }

    byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }
}
