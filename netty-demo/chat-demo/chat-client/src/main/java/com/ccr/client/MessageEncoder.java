package com.ccr.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class MessageEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("client MessageEncoder：" + msg);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(msg);

        //promise参数必须加，否则后面会newPromise(),进而通知不到在此之前的Future，
        ctx.writeAndFlush(Unpooled.copiedBuffer(outputStream.toByteArray()),promise);
    }
}
