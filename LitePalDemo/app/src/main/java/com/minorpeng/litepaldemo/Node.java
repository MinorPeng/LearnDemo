package com.minorpeng.litepaldemo;

import org.litepal.crud.DataSupport;

/**
 * @author hesheng1024
 * @date 2020/5/1 10:49
 */
public class Node extends DataSupport {

    private int id;
    private String name;

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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    private String information;

}
