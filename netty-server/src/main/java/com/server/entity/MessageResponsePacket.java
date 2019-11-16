package com.server.entity;


import com.server.utils.Packet;
import lombok.Data;


@Data
public class MessageResponsePacket extends Packet {

    private String fromClientId;

    private String message;

    public MessageResponsePacket() {

    }

}
