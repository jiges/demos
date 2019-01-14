package com.ccr.server;

import com.ccr.message.Request;
import com.ccr.message.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class RequestHandler extends SimpleChannelInboundHandler<Request> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        System.out.println("Server RequestHandler request arrived:" + msg.toString());
        switch (msg.getType()) {
            case SET_USER_NAME:
                ctx.channel().attr(AttributeKey.valueOf("name")).set(msg.getContent());
                ctx.fireUserEventTriggered("SET_USER_NAME");
                ctx.channel().writeAndFlush(new Response(1,"设置成功"));
                break;
            case SHOW_ALL_USER:
                ctx.fireUserEventTriggered("SHOW_ALL_USER");
                break;
        }
    }
}
