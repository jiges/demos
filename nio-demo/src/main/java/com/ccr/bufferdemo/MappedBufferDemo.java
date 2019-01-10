package com.ccr.bufferdemo;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedBufferDemo {

    public static void main(String[] args) throws Exception{
        String path = FileCopyDemo.class.getResource("/").getPath();
        RandomAccessFile file = new RandomAccessFile(path + "mappedFile.txt","rw");

        FileChannel channel = file.getChannel();

        MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_WRITE,0,5);

//        System.out.println(byteBuffer.isLoaded());
//        while (byteBuffer.hasRemaining()) {
//            System.out.println((char) byteBuffer.get());
//        }
        byteBuffer.put((byte)'a');
        byteBuffer.put((byte)'b');
        channel.close();
    }
}
