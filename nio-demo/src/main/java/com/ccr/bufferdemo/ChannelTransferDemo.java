package com.ccr.bufferdemo;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class ChannelTransferDemo {

    public static void main(String[] args) throws Exception{
        RandomAccessFile accessFile1 = new RandomAccessFile(ChannelTransferDemo.class.getResource("/").getPath() + "transfer1.txt","rw");
        RandomAccessFile accessFile2 = new RandomAccessFile(ChannelTransferDemo.class.getResource("/").getPath() + "transfer2.txt","rw");

        FileChannel channel1 = accessFile1.getChannel();
        FileChannel channel2 = accessFile2.getChannel();

        channel2.transferFrom(channel1,0,channel1.size());
        channel1.close();
        channel2.close();
    }
}
