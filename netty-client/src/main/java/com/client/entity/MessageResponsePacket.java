package com.client.entity;

import com.client.utils.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.client.utils.ICommand.MESSAGE_RESPONSE;

/**
 * @ClassName MessageResponsePacket
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
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
