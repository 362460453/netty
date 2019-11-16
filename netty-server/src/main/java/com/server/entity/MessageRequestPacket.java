package com.server.entity;


import com.server.utils.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    private byte[] cmd;

    public MessageRequestPacket() {

    }

}
