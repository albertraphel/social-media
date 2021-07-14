package com.albert.SocialMedia.helloworld;

public class HelloWorldBean {

    public HelloWorldBean(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
