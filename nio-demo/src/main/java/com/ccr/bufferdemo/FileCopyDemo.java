package com.ccr.bufferdemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ccr12312@163.com at 2018-12-29
 */
public class FileCopyDemo {

    public static void main(String[] args) throws IOException {

        String path = FileCopyDemo.class.getResource("/").getPath();
        FileInputStream inputStream = new FileInputStream(path + "input.txt");
        FileOutputStream outputStream = new FileOutputStream(path + "output.txt");

        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(4);

        while (inChannel.read(buffer) > 0) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        inChannel.close();
        outChannel.close();

    }

}
