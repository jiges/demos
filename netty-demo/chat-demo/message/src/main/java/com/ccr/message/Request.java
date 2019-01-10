package com.ccr.message;

/**
 * @author ccr12312@163.com at 2019-1-10
 */
public class Request {
    enum RequestType {
        SET_USER_NAME
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
}
