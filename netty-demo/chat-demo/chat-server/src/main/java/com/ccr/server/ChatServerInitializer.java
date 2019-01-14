package com.ccr.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author ccr12312@163.com at 2019-1-10
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private ChannelGroupHandler channelGroupHandler = new ChannelGroupHandler();

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new MessageEncoder());
        ch.pipeline().addLast(new MessageDecoder());
        ch.pipeline().addLast(new RequestHandler());
        ch.pipeline().addLast(new MessageHandler());
        ch.pipeline().addLast(channelGroupHandler);

    }
}
