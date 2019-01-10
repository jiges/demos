package com.ccr.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * 客户端功能：
 * 1、连接聊天服务器
 * 2、展示所有在线人员
 * 3、发起聊天
 * 4、发起多人聊天
 * 5、收到下线通知
 * @author ccr12312@163.com at 2018-12-25
 */
public class ChatClient {

//    private static Logger logger = LoggerFactory.getLogger(ChatClient.class);

    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        String line;
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.printf("收到消息：%s",msg);
                            }
                        });
                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                super.connect(ctx, remoteAddress, localAddress, promise);
                            }
                        });
                    }
                });
        Channel channel = null;

        while ((line = reader.readLine()) != null) {
            try {
                int option = Integer.parseInt(line);
                switch (option) {
                    case 1:
                        ChannelFuture future = connect(bootstrap);
                        try {
                            if(future.sync().isSuccess()) {
                                channel = future.channel();
                            } else {
                                System.out.println("连接失败");
//                                logger.debug("connect failed...");
                            }
                        } catch (InterruptedException e) {
                            System.out.println("连接失败");
//                            logger.error("connect failed...",e);
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5: return;
                }
            } catch (NumberFormatException e) {
//                logger.debug("input option error. e",e.getMessage());
                System.out.println("输入错误请重新输入:");
                printMenu();
            }

        }
    }

    private static void printMenu() {
        System.out.println("请选择相应的功能并按回车键:");
        System.out.println("1、连接聊天服务器");
        System.out.println("2、连接聊天服务器");
        System.out.println("3、展示所有在线人员");
        System.out.println("4、发起聊天");
        System.out.println("5、发起多人聊天");
        System.out.println("6、退出");
    }

    private static ChannelFuture connect(Bootstrap bootstrap) {
        return bootstrap.connect(new InetSocketAddress("localhost",8080));
    }

}
