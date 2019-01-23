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
 * @author ccr12312@163.com at 2019-1-22
 */
public class FileRegionHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            RandomAccessFile accessFile = new RandomAccessFile("F:\\netbeans-8.2-windows.exe","r");//220M文件
            FileChannel channel = accessFile.getChannel();
            FileRegion fileRegion = new DefaultFileRegion(channel,0,channel.size());
            //设置文件长度
            HttpUtil.setContentLength(response,fileRegion.count());
            //设置下载的文件名
            response.headers().set(HttpHeaderNames.CONTENT_DISPOSITION,"attachment;filename=netbeans-8.2-windows.exe");
            //设置实体类型
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType("F:\\netbeans-8.2-windows.exe"));

            ctx.write(response);
            ctx.write(fileRegion,promise);
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
