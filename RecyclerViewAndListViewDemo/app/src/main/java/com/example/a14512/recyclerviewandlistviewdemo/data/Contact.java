package com.example.a14512.recyclerviewandlistviewdemo.data;

import java.io.Serializable;

/**
 * @author 14512 on 2018/4/29
 */
public class Contact implements Serializable {
    private String mName;
    private int mPortraitId;
    private String mContent;

    public Contact(String name, int portraitId, String content) {
        this.mName = name;
        this.mPortraitId = portraitId;
        this.mContent = content;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public void setPortraitId(int portraitId) {
        this.mPortraitId = portraitId;
    }

    public int getPortraitId() {
        return mPortraitId;
    }
}
