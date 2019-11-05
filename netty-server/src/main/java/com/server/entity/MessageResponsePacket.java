package com.server.entity;


import com.server.utils.Packet;
import lombok.Data;

import static com.server.utils.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String fromClientId;

    private String message;

    public MessageResponsePacket() {

    }

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }


}
