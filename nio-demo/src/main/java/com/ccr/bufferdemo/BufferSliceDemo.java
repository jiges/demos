package com.ccr.bufferdemo;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferSliceDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < 5; i++) {
            buffer.putChar((char) (65 + i));
        }

        buffer.position(5);
        buffer.limit(8);
        System.out.println(buffer.get());

        Buffer sliceBuffer = buffer.slice();
        System.out.println("sliceBuffer.position " + sliceBuffer.position());
        System.out.println("sliceBuffer.capacity " + sliceBuffer.capacity());
        System.out.println("sliceBuffer.limit " + sliceBuffer.limit());


    }
}
