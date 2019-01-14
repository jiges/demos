package com.ccr.message;

import java.io.Serializable;

/**
 * @author ccr12312@163.com at 2019-1-10
 */
public class Message implements Serializable {

    private String receiver;

    private String content;

    public Message(String sender, String content) {
        this.receiver = sender;
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public Message setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
