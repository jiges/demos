package com.ccr.zerocopy;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ccr12312@163.com at 2019-1-3
 */
public class TransferToCopy {

    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        //为什么要设置这个属性
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        long transformCounts = 0;

        while (true) {
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if(key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel channel = serverSocketChannel.accept();
                    System.out.println(channel);
                    System.out.println("客户端已连接 " + channel.getRemoteAddress());
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_CONNECT);
                }

                if(key.isConnectable()) {
                    System.out.println("连接已建立");
                }

                if(key.isWritable()) {
                    FileChannel fileChannel = (FileChannel) key.attachment();
                    SocketChannel channel = (SocketChannel) key.channel();
                    long counts = fileChannel.transferTo(transformCounts,500 * 1024 * 1024,channel);
                    if(counts > 0) {
                        System.out.println("发送字节数： " + counts);
                        transformCounts += counts;
                    } else {
                        System.out.println("关闭通道...");
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                        channel.shutdownOutput();
                    }
                }

                if(key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(64);
                    try {
                        int len = channel.read(readBuffer);
                        if(len > -1) {
                            readBuffer.flip();
                            System.out.println("接收来自客户端【" + channel.getRemoteAddress() + "】的消息：" + new String(readBuffer.array(),readBuffer.position(),readBuffer.limit()));
                            RandomAccessFile accessFile = new RandomAccessFile("F:\\netbeans-8.2-windows.exe","r");//220M文件
                            FileChannel fileChannel = accessFile.getChannel();

                            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                            key.attach(fileChannel);
                        } else {
                            System.out.println("连接已关闭");
                            channel.close();
                        }
                    } catch (Exception e) {
                        System.out.println("远程主机中断连接");
                        channel.close();
                    }

                }

                iter.remove();
            }
        }
    }
}
