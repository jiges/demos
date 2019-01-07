package com.ccr.zerocopy;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 正常的文件拷贝，220M文件拷贝，需要83297ms
 * @author ccr12312@163.com at 2019-1-2
 */
public class NormalCopy {

    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(8080));

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                if(key.isAcceptable()) {
                    System.out.println("申请建立连接...");
                    SocketChannel channel = ((ServerSocketChannel)key.channel()).accept();
                    channel.configureBlocking(false);
//                    channel.setOption(StandardSocketOptions.SO_SNDBUF,64 * 1024);
                    channel.register(selector,SelectionKey.OP_READ);
                }

                if(key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (buffer.hasRemaining()) {
                        System.out.println("写入字节数：" + channel.write(buffer));
                    } else {
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                        channel.shutdownOutput();
                    }
                }

                if(key.isReadable()) {
                    System.out.println("有可读信息");
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(512);
                    try {
                        int n = channel.read(readBuffer);
                        if(n > 0) {
                            readBuffer.flip();
                            System.out.println("read something: " + new String(readBuffer.array(),readBuffer.position(),readBuffer.limit()));
                            RandomAccessFile accessFile = new RandomAccessFile("F:\\netbeans-8.2-windows.exe","r");//220M文件
                            FileChannel fileChannel = accessFile.getChannel();
                            ByteBuffer buffer = ByteBuffer.allocate(500 * 1024 * 1024);
                            long nano = System.currentTimeMillis();
                            fileChannel.read(buffer);
                            System.out.println("复制文件到内存耗时：" + (System.currentTimeMillis() - nano));
                            System.out.println("读取文件大小：" + buffer.position());
                            buffer.flip();
//                            channel.write(buffer);
                            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                            key.attach(buffer);
                        } else {
                            System.out.println("连接关闭!");
                            channel.close();
                        }
                    } catch (IOException e) {
                        System.out.println("远程主机强迫关闭了一个现有的连接");
                        channel.close();
                    }

                }
                iter.remove();
            }
        }
    }
}
