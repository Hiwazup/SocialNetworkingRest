package com.martin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Post {

    @JsonIgnore
    private Date timestamp = new Date();
    private String username;
    private String message;

    public Post(){}

    public Post(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
