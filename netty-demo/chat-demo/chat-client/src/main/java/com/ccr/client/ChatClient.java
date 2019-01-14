package com.ccr.client;

import com.ccr.client.sender.MessageSender;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * 客户端功能：
 * 1、连接聊天服务器
 * 2、展示所有在线人员
 * 3、发起聊天
 * 4、发起多人聊天
 * 5、收到下线通知
 * @author ccr12312@163.com at 2018-12-25
 */
public class ChatClient {

//    private static Logger logger = LoggerFactory.getLogger(ChatClient.class);

    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        String line;
        MessageSender sender = null;
        printMenu();
        while ((line = reader.readLine()) != null) {
            try {
                int option = Integer.parseInt(line);
                switch (option) {
                    case 1:
                        sender = new MessageSender();
                        sender.connect();
                        break;
                    case 2:
                        if(sender == null) {
                            System.out.println("请先连接服务器!");
                            break;
                        }
                        System.out.println("请输入名称：");
                        String name = reader.readLine();
                        sender.setUpName(name);
                        break;
                    case 3:
                        if(sender == null) {
                            System.out.println("请先连接服务器!");
                            break;
                        }
                        sender.showAllUsers();
                        break;
                    case 4:
                    case 5:
                        if(sender == null) {
                            System.out.println("请先连接服务器!");
                            break;
                        }
                        System.out.println("请输入接收入名称：");
                        String receiver = reader.readLine();
                        System.out.println("请输入内容：");
                        String content = reader.readLine();
                        sender.sendMessage(receiver,content);
                        break;
                    case 6:
                        sender.shutDown();
                        return;
                }
            } catch (NumberFormatException e) {
//                logger.debug("input option error. e",e.getMessage());
                System.out.println("输入错误请重新输入:");
                printMenu();
            }

        }
    }

    private static void printMenu() {
        System.out.println("请选择相应的功能并按回车键:");
        System.out.println("1、连接聊天服务器");
        System.out.println("2、设置名称");
        System.out.println("3、展示所有在线人员");
        System.out.println("4、发起聊天");
        System.out.println("5、发起多人聊天");
        System.out.println("6、退出");
    }

    private static ChannelFuture connect(Bootstrap bootstrap) {
        return bootstrap.connect(new InetSocketAddress("localhost",8080));
    }

}
