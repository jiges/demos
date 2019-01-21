package com.ccr.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 直接将FileRegion传给Channel,不支持断点续传，底层通过transferTo实现
 * 参考NioSocketChannel.doWriteFileRegion()方法
 * 可以通过ChannelProgressivePromise来实时获取传输的进度
 * @author ccr12312@163.com at 2019-1-17
 */
public class FileTransferHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println(msg.uri());
        if(msg.uri().equals("/download")) {
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK);

            if (HttpUtil.isKeepAlive(msg)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            RandomAccessFile accessFile = new RandomAccessFile("F:\\netbeans-8.2-windows.exe","r");//220M文件
            FileChannel channel = accessFile.getChannel();
            FileRegion fileRegion = new DefaultFileRegion(channel,0,channel.size());
            HttpUtil.setContentLength(response,fileRegion.count());
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType("F:\\netbeans-8.2-windows.exe"));
            //写入响应头
            ctx.channel().write(response);

            ctx.channel().write(fileRegion,ctx.newProgressivePromise()).addListener(new ChannelProgressiveFutureListener() {
                @Override
                public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                    if (total < 0) { // total unknown
                        System.err.println(future.channel() + " Transfer progress: " + progress);
                    } else {
                        System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                    }
                }

                @Override
                public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                    System.err.println(future.channel() + " Transfer complete.");
                }
            });

            ChannelFuture lastFuture = ctx.channel().writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            if (!HttpUtil.isKeepAlive(msg)) {
                // Close the connection when the whole content is written out.
                lastFuture.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
