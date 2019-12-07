package com.phs1024.studydemo.report.bean;

/**
 * @author PHS1024
 * @date 2019/10/28 11:07:27
 */
public class Contact {

    private String mName;
    private String mPhone;

    public Contact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }
}
