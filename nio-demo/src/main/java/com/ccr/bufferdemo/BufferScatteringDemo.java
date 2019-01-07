package com.ccr.bufferdemo;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferScatteringDemo {
    public static void main(String[] args) throws Exception {
        String path = FileCopyDemo.class.getResource("/").getPath();
        FileInputStream inputStream = new FileInputStream(path + "mappedFile.txt");

        FileChannel channel = inputStream.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(6);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);

        ByteBuffer[] buffers = {buffer1,buffer2};

        channel.read(buffers);

        if(buffer1.hasArray()) {
            System.out.println(new String(buffer1.array()));
        }
        if(buffer2.hasArray()) {
            System.out.println(new String(buffer2.array()));
        }
        channel.close();
    }
}
