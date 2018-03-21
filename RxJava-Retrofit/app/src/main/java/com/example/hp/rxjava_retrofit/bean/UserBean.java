package com.example.hp.rxjava_retrofit.bean;

/**
 * Created by HP on 2017/5/4.
 */

public class UserBean {

    private String name;
    private String pwd;
    private int id;

    public UserBean(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
