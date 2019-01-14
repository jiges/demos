package com.ccr.server;

import com.ccr.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("Server MessageHandler 收到消息:" + msg + "来自于：" + ctx.channel().attr(AttributeKey.valueOf("name")).get().toString());
        ctx.fireChannelRead(msg);
    }
}
