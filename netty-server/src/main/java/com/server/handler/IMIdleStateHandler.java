package com.server.handler;

import com.server.service.IClientManage;
import com.server.utils.Constants;
import com.server.utils.SpringContextHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 30;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        List<String> key = Constants.getKey(Constants.equipmentMap, ctx.channel().id().asShortText());
        log.warn("{}秒内未读到设备[{}]消息,触发心跳检测,关闭连接", READER_IDLE_TIME, key.toString());
        IClientManage iClientManage= SpringContextHolder.getBean("clientManageImpl");
        iClientManage.kickOutClient(ctx.channel());
    }
}
