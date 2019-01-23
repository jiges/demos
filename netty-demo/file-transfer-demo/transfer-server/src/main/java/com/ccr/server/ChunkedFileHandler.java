package com.ccr.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedInput;

import javax.activation.MimetypesFileTypeMap;
import java.io.RandomAccessFile;

/**
 * 使用ChunkedFile的方式，存在两次拷贝，速度明显慢于FileRegionHandler
 * @author ccr12312@163.com at 2019-1-22
 */
public class ChunkedFileHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            ChunkedInput file = new ChunkedFile(new RandomAccessFile("F:\\netbeans-8.2-windows.exe","r"));

            HttpUtil.setContentLength(response,file.length());
            //设置下载的文件名
            response.headers().set(HttpHeaderNames.CONTENT_DISPOSITION,"attachment;filename=netbeans-8.2-windows.exe");
            //设置实体类型
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType("F:\\netbeans-8.2-windows.exe"));

            ctx.write(response);
            ctx.write(file,promise);
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        }
    }
}
