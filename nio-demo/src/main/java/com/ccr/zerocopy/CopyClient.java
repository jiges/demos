package com.ccr.zerocopy;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ccr12312@163.com at 2019-1-2
 */
public class CopyClient {
    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));

        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        RandomAccessFile accessFile = new RandomAccessFile("F:\\netbeans-8.2-windows_bak.exe","rw");
        FileChannel channel1 = accessFile.getChannel();


        long nano = 0;
        int totalSize = 0;

        while (true) {
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while(iter.hasNext()) {
                SelectionKey key = iter.next();

                if(key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.setOption(StandardSocketOptions.SO_RCVBUF,32 * 1024);
                    //
                    if(channel.finishConnect()) {
                        System.out.println("连接已建立");
                        nano = System.currentTimeMillis();
                        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
                        channel.write(buffer);
                    }
                }

                if(key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(500 * 1024 * 1024);
                    buffer.clear();
                    int readSize = channel.read(buffer);
                    if(readSize > -1) {
                        totalSize += readSize;
                        System.out.println("read bytes :" + readSize);
                        System.out.println("totalSize :" + totalSize);
                        System.out.println("耗时: " + (System.currentTimeMillis() - nano));

                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            System.out.println("写入本地磁盘:" + channel1.write(buffer));
                            System.out.println("文件指针:" + channel1.position());
                        }

                    } else {
                        channel.close();
                    }
                }
                iter.remove();
            }
        }

    }
}
