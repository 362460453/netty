package com.server.handler;

import com.server.utils.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 30;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        String channelId = ctx.channel().id().asShortText();
        String equipmentMapKey = null;
        for (Map.Entry<String, String> str : Constants.equipmentMap.entrySet()) {
            if (channelId.equals(str.getValue())) {
                equipmentMapKey = str.getKey();
            }
        }
        log.info("设备 :" + equipmentMapKey + " 空闲检测 离 线");
        Constants.channelMap.remove(channelId);
        Constants.equipmentMap.remove(equipmentMapKey);
        ctx.channel().close();
    }
}
