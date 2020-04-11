package com.server.utils;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {

    /**
     * netty ChannelHandlerContext缓存  key：channelId
     */
    public static Map<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();

    /**
     * netty 设备 缓存 key {code_type} 可以用reids,key:code_type,value:channelId
     */
    public static Map<String, String> equipmentMap = new ConcurrentHashMap<String, String>();
    public static ExecutorService pool = Executors.newCachedThreadPool();
    /**
     * 发请求的DeferredResult  key {mac}_{dateTime}
     */
    public static Map<String, DeferredResult<Object>> responseMap = new ConcurrentHashMap<String, DeferredResult<Object>>();
    /**
     * DeferredResult超时时间
     */
    public static final Long DeferredResultTimeOut = 15000l;
}
