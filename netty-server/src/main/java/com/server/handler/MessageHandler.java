package com.server.handler;

import com.server.service.IClientManage;
import com.server.utils.DispatchRequest;
import com.server.utils.Packet;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @ClassName FirstServerHandler
 * @Description: 负责读取客户端发来的数据
 * @Author yanlin
 * @Date 2019/11/1
 * @Version V1.0
 **/
@ChannelHandler.Sharable
@Component
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    @Autowired
    IClientManage iClientManage;


    /*
    当前channel从远端读取到数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        DispatchRequest.dispatchRequest(ctx, packet);
    }

    /*
    当前channel激活的时候
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        iClientManage.clientChannelActive(ctx.channel());
    }

    /*
    channel出现异常会触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

    /*
     * 当前channel不活跃的时候，也就是当前channel到了它生命周期末
     * 断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        iClientManage.kickOutClient(ctx.channel());
    }
}
