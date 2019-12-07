package com.phs1024.studydemo.report.bean;

import java.io.Serializable;

/**
 * @author PHS1024
 * @date 2019/10/24 18:20:44
 */
public class Music implements Serializable {

    private String mName;
    private String mPath;

    public Music(String name, String path) {
        this.mName = name;
        this.mPath = path;
    }

    public String getName() {
        return mName;
    }

    public String getPath() {
        return mPath;
    }

}
