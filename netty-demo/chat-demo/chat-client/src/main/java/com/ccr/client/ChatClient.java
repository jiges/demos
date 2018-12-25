package com.ccr.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static void main(String[] args) throws IOException {
        System.out.println("请选择相应的功能并按回车键:");
        System.out.println("1、连接聊天服务器");
        System.out.println("2、展示所有在线人员");
        System.out.println("3、发起聊天");
        System.out.println("4、发起多人聊天");
        System.out.println("5、退出");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        String line;

        while ((line = reader.readLine()) != null && !line.equals("5")) {

        }
    }

}
