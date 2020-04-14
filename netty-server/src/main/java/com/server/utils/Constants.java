package com.server.utils;

import io.netty.channel.Channel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {
    /**
     * netty ChannelHandlerContext缓存  key：channelId
     */
    public static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * netty 设备 缓存 key {code_type} 可以用reids,key:code_type,value:channelId
     */
    public static ConcurrentHashMap<String, String> equipmentMap = new ConcurrentHashMap<String, String>();
    /*
    尝试注册设备, 已经连接并未上报自身信息的设备
     */
    public static ConcurrentHashMap<String, LocalDateTime> registerMap = new ConcurrentHashMap<>();

    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static List<String> getKey(ConcurrentHashMap<String, String> map, String value) {
        List<String> keyList = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }
}
