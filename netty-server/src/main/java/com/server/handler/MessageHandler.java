package com.server.handler;

import com.server.utils.Packet;
import com.server.utils.TypeEnum;
import io.netty.channel.*;
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
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        System.out.println("服务端收到消息");
        //1.先发送成功,成功的信号是packet对象，只有一个字节 type是响应 ，只不过data是一个字节的回复
        Packet packetResponse = new Packet();
        //构造返回给客户端的packet
        //管道给packet携带到不同业务处理类
        packet.setChannel(ctx.channel());
        packetResponse.setType(2);
        packetResponse.setLength(1);
        packetResponse.setData(new Byte[] {1,1});
        ctx.channel().writeAndFlush(packetResponse);
        //2.根据packet 里面的type用枚举分发不同处理器
        //测试收到的是什么东西
        System.out.println(packet.toString());
        if (TypeEnum.CONTROL.value() == packet.getType()) {
        //一次类推
            //如果符合这个信号呢？
            //转发到别的处理器，在不同业务处理器//ctx.channel().writeAndFlush(packet);
        } else if (1 == 1) {

        }

    }

}
