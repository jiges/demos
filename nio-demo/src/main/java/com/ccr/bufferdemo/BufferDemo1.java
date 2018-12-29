package com.ccr.bufferdemo;

import java.nio.IntBuffer;

/**
 * @author ccr12312@163.com at 2018-12-29
 */
public class BufferDemo1 {
    public static void main(String[] args) {

        IntBuffer buffer = IntBuffer.allocate(5);
        System.out.printf("初始化Buffer, position:%2d,limit:%2d,capacity:%2d%n",buffer.position(),buffer.limit(),buffer.capacity());
        System.out.println("buffer中的数据 " + buffer.hasRemaining());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
            System.out.printf("正在写入数据的Buffer, position:%2d,limit:%2d,capacity:%2d%n",buffer.position(),buffer.limit(),buffer.capacity());
        }

        System.out.printf("写入数据后的Buffer, position:%2d,limit:%2d,capacity:%2d%n",buffer.position(),buffer.limit(),buffer.capacity());
        System.out.println("buffer中的数据 " + buffer.hasRemaining());

        buffer.flip();
        System.out.printf("翻转后的Buffer, position:%2d,limit:%2d,capacity:%2d%n",buffer.position(),buffer.limit(),buffer.capacity());

        for (;buffer.hasRemaining();) {
            System.out.println(buffer.get());
            System.out.printf("正在读数据的Buffer, position:%2d,limit:%2d,capacity:%2d%n",buffer.position(),buffer.limit(),buffer.capacity());
        }
    }
}
