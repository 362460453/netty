package com.server.service;

import com.server.utils.Packet;

/**
 * @ClassName SendCommand
 * @Description: 发送消息公共接口
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
public interface ISendCommand {
    void exec(Packet packet) throws Exception;
}
