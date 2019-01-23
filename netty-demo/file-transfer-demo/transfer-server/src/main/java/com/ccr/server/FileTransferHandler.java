package com.ccr.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;


/**
 * Http文件下载
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

            ChannelProgressivePromise progress = ctx.newProgressivePromise();
            progress.addListener(new ChannelProgressiveFutureListener() {
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
            ctx.write(response,progress);
        }
    }
}
