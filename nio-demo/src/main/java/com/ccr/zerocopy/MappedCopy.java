package com.ccr.zerocopy;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 220M文件拷贝，需要75413
 * @author ccr12312@163.com at 2019-1-7
 */
public class MappedCopy {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if(key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);
                    System.out.println("连接建立");
                    socketChannel.register(selector,SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(5 * 1024);
                    if(socketChannel.read(readBuffer) > -1) {
                        readBuffer.flip();
                        System.out.println("读取数据.." + new String(readBuffer.array(),readBuffer.position(),readBuffer.limit()));
                        RandomAccessFile accessFile = new RandomAccessFile("F:\\netbeans-8.2-windows.exe","r");//220M文件
                        FileChannel fileChannel = accessFile.getChannel();
                        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,0,fileChannel.size());
                        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                        key.attach(mappedByteBuffer);
                    } else {
                        System.out.println("连接断开...");
                        socketChannel.close();
                    }
                } else if (key.isWritable()) {
                    System.out.println("开始发送文件..");
                    SocketChannel channel = (SocketChannel) key.channel();
                    MappedByteBuffer buffer = (MappedByteBuffer) key.attachment();
                    if(buffer.hasRemaining()) {
                        System.out.println("发送字节数：" + channel.write(buffer));
                    } else {
                        System.out.println("发送完毕...");
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                    }
                }
                iter.remove();
            }
        }

    }
}
