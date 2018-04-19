package com.example.common.model;

import java.io.Serializable;

/**
 * @author 14512 on 2018/4/19
 */
public class UserModel implements Serializable {
    private String name;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
