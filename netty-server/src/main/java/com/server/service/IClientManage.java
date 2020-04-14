package com.server.service;

import io.netty.channel.Channel;

public interface IClientManage {
    /*
    踢出设备
     */
    void kickOutClient(Channel channel);

    /*
    设备尝试注册
     */
    void clientChannelActive(Channel channel);

    /*
    设备注册回调
     */
    void clientRegisterCallback(Channel channel, String equipmentMapKey);

    /*
    定时检查客户端
     */
    void checkClient();
}
