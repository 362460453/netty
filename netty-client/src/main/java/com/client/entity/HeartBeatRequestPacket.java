package com.client.entity;


import com.client.utils.Packet;

import static com.client.utils.ICommand.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
