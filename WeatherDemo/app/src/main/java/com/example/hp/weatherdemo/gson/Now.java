package com.example.hp.weatherdemo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 2017/2/10.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
