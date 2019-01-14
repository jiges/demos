package com.ccr.message;

import java.io.Serializable;

/**
 * @author ccr12312@163.com at 2019-1-10
 */
public class Request implements Serializable {
    public enum RequestType {
        SET_USER_NAME,
        SHOW_ALL_USER
    }

    private RequestType type;

    private String content;

    public Request(RequestType type, String content) {
        this.type = type;
        this.content = content;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
