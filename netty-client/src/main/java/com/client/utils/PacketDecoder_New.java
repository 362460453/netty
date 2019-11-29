package com.client.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class PacketDecoder_New extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 3;//表示数据长度字段开始的偏移量
    private static final int LENGTH_FIELD_LENGTH = 1;//数据长度字段的所占的字节数

    public PacketDecoder_New() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if (byteBuf == null) {
            return null;
        }
        //1  按第一种更直观
        byte head1 = byteBuf.readByte();
        byte head2 = byteBuf.readByte();
        byte type = byteBuf.readByte();
        byte length = byteBuf.readByte();
        byte[] data = new byte[length];
        byteBuf.readBytes(data);
        Packet packet = new Packet().setType(type).setLength(length).setData(data).setByteBuf(byteBuf);
        return packet;
    }
}
