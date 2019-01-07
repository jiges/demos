package com.ccr.bufferdemo;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferGatheringDemo {
    public static void main(String[] args) throws Exception{
        String path = FileCopyDemo.class.getResource("/").getPath();
        RandomAccessFile file = new RandomAccessFile(path + "test.txt","rw");

        FileChannel channel = file.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(6);
        ByteBuffer buffer2 = ByteBuffer.allocate(10);

        buffer1.put("hello".getBytes(),0,5);
        buffer1.put((byte)  '\n');

        buffer2.put("world".getBytes(),0,5);

        buffer1.flip();
        buffer2.flip();

        ByteBuffer[] buffers = {buffer1,buffer2};

        channel.write(buffers);

        channel.close();


    }
}
