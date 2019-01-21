package com.ccr.server;

import io.netty.channel.FileRegion;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

import static org.junit.Assert.*;

/**
 * @author ccr12312@163.com at 2019-1-17
 */
public class FileTransferHandlerTest {

    @org.junit.Test
    public void channelRead0() {
        HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,"/download");
        EmbeddedChannel channel = new EmbeddedChannel(new FileTransferHandler());
        assertFalse(channel.writeInbound(request));
        channel.readOutbound();

        channel.readOutbound();
    }
}