package com.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.server.service.IClientManage;
import com.server.utils.Constants;
import com.server.utils.Packet;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Description: 客户端管理
 * @Author yanlin
 * @Date 2020/4/12
 * @Version V1.0
 **/
@Service
@Slf4j
public class ClientManageImpl implements IClientManage {
    @Override
    public void kickOutClient(Channel channel) {
        String channelId = channel.id().asShortText();
        Constants.channelMap.remove(channelId);
        Constants.equipmentMap.values().remove(channelId);
        log.warn("地址 :{} 的设备离线\n剩余设备\nequipmentMap剩余{}条数据，分别:{}。\nchannelMap剩余{}条数据,分别:{}",
                channel.remoteAddress(),
                Constants.equipmentMap.size(), Constants.equipmentMap.toString(),
                Constants.channelMap.size(), Constants.channelMap.toString());
        channel.close();
    }

    @Override
    public void clientChannelActive(Channel channel) {
        String channelId = channel.id().asShortText();
        log.info("地址:{}的设备尝试注册,channel_Id:{}", channel.remoteAddress(), channelId);
        Constants.channelMap.put(channelId, channel);//channelId和channel对应关系
        Constants.registerMap.put(channelId, LocalDateTime.now());
        log.info("channelMap:{}", Constants.channelMap.toString());
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byte[] data = {1, (byte) 255};
        Packet packet = new Packet((byte) 3, data);
        if (Constants.registerMap.containsKey(channelId)) {
            log.info("剩余未上报客户端{}个，分别:{}。", Constants.registerMap.size(), Constants.registerMap.toString());
            channel.writeAndFlush(packet);
        } else {
            return;
        }
    }

    @Override
    public void clientRegisterCallback(Channel channel, String equipmentMapKey) {
        String channelId = channel.id().asShortText();
        Constants.equipmentMap.put(equipmentMapKey, channelId);//设备和channelId对应关系
        Constants.registerMap.remove(channelId);
        log.info("地址:{},\nchannelId:{},\n设备:{}注册成功。\n剩余设备\nequipmentMap已有{}条数据，分别:{}。\nchannelMap已有{}条数据,分别:{}.\n目前还有{}台设备未上报信息，分别:{}。",
                channel.remoteAddress(), channel.id().asShortText(), equipmentMapKey,
                Constants.equipmentMap.size(), Constants.equipmentMap.toString(),
                Constants.channelMap.size(), Constants.channelMap.toString(),
                Constants.registerMap.size(), Constants.registerMap.toString());
    }

    @Override
    public void checkClient() {
        //如果欲注册客户端存在
        if (ObjectUtil.isNotNull(Constants.registerMap) && Constants.registerMap.size() > 0) {
            List<String> channelIdList = filterChannelId(Constants.registerMap);//过滤出生存大于20s的客户端
            if (channelIdList.size() > 0) {//存在
                log.info("定时器过滤出欲注册客户端{}个,分别是{}", channelIdList.size(), channelIdList.toString());
                for (String channelId : channelIdList) {//循环每一个大于20s的客户端
                    if (Constants.channelMap.containsKey(channelId)) {//如果这样的客户端没有被心跳踢出
                        log.info("定时器真实执行发送channelId:{}", channelId);
                        byte[] data = {1, (byte) 255};
                        Packet packet = new Packet((byte) 3, data);
                        Constants.channelMap.get(channelId).writeAndFlush(packet);//则在发送一次身份上报信号
                    }
                }
            }
        }
    }

    public static List<String> filterChannelId(ConcurrentHashMap<String, LocalDateTime> map) {
        List<String> channelIdList = new LinkedList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<String, LocalDateTime> entry : map.entrySet()) {
            if (ChronoUnit.SECONDS.between(entry.getValue(), now) >= 20) {//欲注册客户端与现在时间大于20s的在发送一次并清除
                channelIdList.add(entry.getKey());
                Constants.registerMap.remove(entry.getKey());//移除大于20s的客户端
            }
        }
        return channelIdList;
    }
}
