package com.ccr;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * @author ccr12312@163.com at 2019-1-10
 */
public class TestClientDemo {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture future = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channel registered...");
                                    super.channelRegistered(ctx);
                                }

                                @Override
                                public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channelUnregistered...");
                                    super.channelUnregistered(ctx);
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channelActive...");
                                    ChannelFuture future1 = ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
                                    future1.addListener(new ChannelFutureListener() {
                                        @Override
                                        public void operationComplete(ChannelFuture future) throws Exception {
                                            System.out.println(future.isSuccess());
                                            if(!future.isSuccess()) {
                                                future.cause().printStackTrace();
                                            }
                                        }
                                    });
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channelInactive...");
                                    super.channelInactive(ctx);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("channelRead...");
                                    super.channelRead(ctx, msg);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channelReadComplete...");
                                    super.channelReadComplete(ctx);
                                }

                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    System.out.println("userEventTriggered...");
                                    super.userEventTriggered(ctx, evt);
                                }

                                @Override
                                public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channelWritabilityChanged...");
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
                                    System.out.println("bind...");
                                    super.bind(ctx, localAddress, promise);
                                }

                                @Override
                                public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                    System.out.println("connect...");
                                    super.connect(ctx, remoteAddress, localAddress, promise);
                                }

                                @Override
                                public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                    System.out.println("disconnect...");
                                    super.disconnect(ctx, promise);
                                }

                                @Override
                                public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                    System.out.println("close...");
                                    super.close(ctx, promise);
                                }

                                @Override
                                public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                    System.out.println("deregister...");
                                    super.deregister(ctx, promise);
                                }

                                @Override
                                public void read(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("read...");
                                    //出站事件为什么会有read方法？
                                    super.read(ctx);
                                }

                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                    System.out.println("write...");
                                    //出站事件继续传给下一个处理器
                                    super.write(ctx, msg, promise);
                                }

                                @Override
                                public void flush(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("flush...");
                                    super.flush(ctx);
                                }
                            });
                            ch.pipeline().addLast(new LoggingHandler());
                        }
                    })
                    .remoteAddress("localhost",8080)
                    .connect();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }
}
