package com.ccr.client;

import com.ccr.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("Client MessageHandler 收到消息:" + msg);
    }
}
