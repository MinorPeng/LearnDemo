package com.example.hp.rxjava_retrofit.bus;

/**
 * Created by HP on 2017/5/4.
 */

public class RefreshMessage {

    private String name;
    private String id;

    public RefreshMessage(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
