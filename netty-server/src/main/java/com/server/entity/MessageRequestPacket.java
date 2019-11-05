package com.server.entity;


import com.server.utils.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.server.utils.Command.MESSAGE_REQUEST;

@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toClientId;
    private String message;
    public MessageRequestPacket(){

    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }


    
}
