package com.ccr.client;

import com.ccr.message.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class ResponseHandler extends SimpleChannelInboundHandler<Response> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
        System.out.println("Client ResponseHandler 收到服务器的响应:" + msg);
    }
}
