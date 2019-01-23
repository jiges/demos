package com.ccr.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author ccr12312@163.com at 2019-1-17
 */
public class TransferServerDemo {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addFirst(new LoggingHandler(LogLevel.INFO));
                            //Http加解码
                            ch.pipeline().addLast(new HttpServerCodec());
                            //Http请求响应聚合
                            ch.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
                            //文件零拷贝的模式，但是不能对文件进行修改
//                            ch.pipeline().addLast(new FileRegionHandler());
                            //ChunkedFile
                            ch.pipeline().addLast(new ChunkedWriteHandler());
//                            ch.pipeline().addLast(new ChunkedFileHandler());
//                            ch.pipeline().addLast(new ChunkedNioFileHandler());

                            //必须放到最后，
                            ch.pipeline().addLast(new FileTransferHandler());


                        }
                    });
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
