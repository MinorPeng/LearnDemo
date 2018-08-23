package com.example.a14512.mvvmdemo.mode;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * @author 14512 on 2018/8/20
 */
public class ObservableFieldUser {
    public ObservableField<String> mName = new ObservableField<>();
    public ObservableInt mAge = new ObservableInt();

    public ObservableFieldUser(String name, int age) {
        mName.set(name);
        this.mAge.set(age);
    }
}
