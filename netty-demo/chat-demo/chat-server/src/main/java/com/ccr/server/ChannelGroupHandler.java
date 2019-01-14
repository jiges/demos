package com.ccr.server;

import com.ccr.message.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
@ChannelHandler.Sharable
public class ChannelGroupHandler extends ChannelInboundHandlerAdapter {

    private ChannelGroup channels;

    public ChannelGroupHandler() {
        channels = new DefaultChannelGroup("messageBroadCast",GlobalEventExecutor.INSTANCE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server ChannelGroupHandler.channelRead " + msg);
        if(msg instanceof Message) {
            Message message = (Message) msg;
            ChannelMatcher matcher = new SimpleChannelMatcher(Collections.singletonList(message.getReceiver()));
            channels.writeAndFlush(message.getContent(),matcher);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("Server ChannelGroupHandler.userEventTriggered " + evt);
        if(evt instanceof String) {
            String event = (String) evt;
            if(event.equals("SET_USER_NAME")) {
                channels.add(ctx.channel());
            }

            if(event.equals("SHOW_ALL_USER")) {
                String channelNames = channels.stream().map(x -> x.attr(AttributeKey.valueOf("name")).get().toString()).collect(Collectors.joining());
                System.out.println(channels);
                System.out.println(channelNames);
                ctx.writeAndFlush(channelNames);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    private static class SimpleChannelMatcher implements ChannelMatcher {

        List<String> channelNames;

        public SimpleChannelMatcher(List<String> channelNames) {
            this.channelNames = channelNames;
        }

        @Override
        public boolean matches(Channel channel) {
            return channelNames.contains(channel.attr(AttributeKey.valueOf("name")).get().toString());
        }
    }
}
