package com.client.entity;


import com.client.utils.Packet;
import lombok.Data;

import static com.client.utils.ICommand.MESSAGE_REQUEST;
@Data
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
