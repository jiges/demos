package com.ccr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.SocketAddress;

/**
 * @author ccr12312@163.com at 2019-1-10
 */
public class TestServerDemo {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter channel registered...");
                                    super.channelRegistered(ctx);
                                }

                                @Override
                                public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter channelUnregistered...");
                                    super.channelUnregistered(ctx);
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter channelActive...");
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println(" ChannelInboundHandlerAdapter channelInactive...");
                                    super.channelInactive(ctx);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter channelRead...");
                                    System.out.println(msg.toString());
                                    super.channelRead(ctx, msg);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter channelReadComplete...");
                                    super.channelReadComplete(ctx);
                                }

                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter userEventTriggered...");
                                    super.userEventTriggered(ctx, evt);
                                }

                                @Override
                                public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelInboundHandlerAdapter channelWritabilityChanged...");
                                    super.channelWritabilityChanged(ctx);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    System.out.println("exceptionCaught...");
                                    super.exceptionCaught(ctx, cause);
                                }
                            });
                            ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                                @Override
                                public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter bind...");
                                    super.bind(ctx, localAddress, promise);
                                }

                                @Override
                                public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter connect...");
                                    super.connect(ctx, remoteAddress, localAddress, promise);
                                }

                                @Override
                                public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter disconnect...");
                                    super.disconnect(ctx, promise);
                                }

                                @Override
                                public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter close...");
                                    super.close(ctx, promise);
                                }

                                @Override
                                public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter deregister...");
                                    super.deregister(ctx, promise);
                                }

                                @Override
                                public void read(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter read...");
                                    super.read(ctx);
                                }

                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter write...");
                                    super.write(ctx, msg, promise);
                                }

                                @Override
                                public void flush(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("ChannelOutboundHandlerAdapter flush...");
                                    super.flush(ctx);
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
