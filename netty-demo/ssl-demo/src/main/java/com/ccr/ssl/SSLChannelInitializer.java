package com.ccr.ssl;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.OptionalSslHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;

import javax.net.ssl.SSLEngine;


/**
 * @author ccr12312@163.com at 2019-1-21
 */
public class SSLChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext sslContext;

    private final boolean startTls;

    public SSLChannelInitializer(SslContext sslContext, boolean startTls) {
        this.sslContext = sslContext;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
//        SSLEngine engine = sslContext.newEngine(ch.alloc());
        ch.pipeline().addLast(new LoggingHandler( LogLevel.INFO));
        ch.pipeline().addLast(new OptionalSslHandler(sslContext));
//        ch.pipeline().addLast("ssl",new SslHandler(engine,startTls));
        ch.pipeline().addLast("codec",new HttpServerCodec());
        ch.pipeline().addLast("aggregator",new HttpObjectAggregator(512 * 1024));
        ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
                System.out.println(msg.uri());
                System.out.println(msg.headers());

                HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.TEXT_PLAIN);
                //如果不加CONTENT_LENGTH，会导致浏览器一直处于pending状态
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH,"hello".getBytes().length);
                if(HttpUtil.isKeepAlive(msg)) {
                    response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE);
                }
                ctx.write(response);
                ctx.write(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
                ChannelFuture lastFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

                if (!HttpUtil.isKeepAlive(msg)) {
                    // Close the connection when the whole content is written out.
                    lastFuture.addListener(ChannelFutureListener.CLOSE);
                }
            }
        });
    }
}
