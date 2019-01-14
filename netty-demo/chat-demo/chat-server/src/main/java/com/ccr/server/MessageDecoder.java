package com.ccr.server;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.ObjectInputStream;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class MessageDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf) {
            ByteBuf buffer = (ByteBuf) msg;
            byte[] bytes = new byte[buffer.writerIndex() - buffer.readerIndex()];
            buffer.readBytes(bytes);
            ByteInputStream byteInputStream = new ByteInputStream(bytes,bytes.length);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            Object res = objectInputStream.readObject();
            System.out.println("Server MessageDecoder message decoded:" + res);
            ctx.fireChannelRead(res);
        }
    }
}
