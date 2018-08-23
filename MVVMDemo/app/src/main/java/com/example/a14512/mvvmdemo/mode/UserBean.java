package com.example.a14512.mvvmdemo.mode;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.a14512.mvvmdemo.BR;

/**
 * @author 14512 on 2018/8/20
 */
public class UserBean extends BaseObservable {
    private String name;
    private int age;

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public int getAge() {
        return age;
    }
}
