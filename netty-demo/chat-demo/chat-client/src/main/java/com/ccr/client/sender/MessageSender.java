package com.ccr.client.sender;

import com.ccr.client.MessageDecoder;
import com.ccr.client.MessageEncoder;
import com.ccr.client.MessageHandler;
import com.ccr.client.ResponseHandler;
import com.ccr.message.Message;
import com.ccr.message.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author ccr12312@163.com at 2019-1-14
 */
public class MessageSender {

    private Bootstrap bootstrap;
    private Channel channel;
    private String clientName;


    public MessageSender() {
        init();
    }

    public void connect() {
        ChannelFuture future = bootstrap.connect();
//        future.addListener(new GenericFutureListener<Future<? super Void>>() {
//            @Override
//            public void operationComplete(Future<? super Void> future) throws Exception {
//                System.out.println("operationComplete");
//                if(future.isSuccess()) {
//                    System.out.println("连接成功...");
//                }
//            }
//        });
        try {
            if(future.sync().isSuccess()) {
                channel = future.channel();
                System.out.println("连接成功...");
            } else {
                System.out.println("连接失败");
            }
        } catch (InterruptedException e) {
            System.out.println("连接失败");
        }
    }

    public void setUpName(String name) {
        if (channel == null) {
            System.out.println("请先连接服务器!");
            return;
        }
        if(channel.isActive()) {
            Request request = new Request(Request.RequestType.SET_USER_NAME,name);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = null;
//
//            try {
//                objectOutputStream = new ObjectOutputStream(outputStream);
//                objectOutputStream.writeObject(request);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            ChannelFuture future = channel.writeAndFlush(request);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("operationComplete...");
                    if(future.isSuccess()) {
                        System.out.println("设置发送成功...");
                    }
                }
            });
        } else {
            System.out.println("服务器连接异常!");
        }
    }

    public void showAllUsers() {
        if (channel == null) {
            System.out.println("请先连接服务器!");
            return;
        }
        if(channel.isActive()) {
            Request request = new Request(Request.RequestType.SHOW_ALL_USER,null);
            ChannelFuture future = channel.writeAndFlush(request);
            future.addListener(future1 -> {
                if(future1.isSuccess()) {
                    System.out.println("请求成功...");
                }
            });
        } else {
            System.out.println("服务器连接异常!");
        }
    }

    public void sendMessage(String user,String content) {
        if (channel == null) {
            System.out.println("请先连接服务器!");
            return;
        }
        if(channel.isActive()) {
            Message message = new Message(user,content);
            ChannelFuture future = channel.writeAndFlush(message);
            future.addListener(future1 -> {
                if(future1.isSuccess()) {
                    System.out.println("消息发送成功...");
                }
            });
        } else {
            System.out.println("服务器连接异常!");
        }
    }

    public void shutDown() {
        try {
            channel.close();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            bootstrap.config().group().shutdownGracefully();
        }
    }


    public void init() {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress("localhost",8080)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder());
                        ch.pipeline().addLast(new MessageDecoder());
                        ch.pipeline().addLast(new ResponseHandler());
                        ch.pipeline().addLast(new MessageHandler());
                    }
                });
    }
}
