package com.example.hp.parcelabletest.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP on 2017/3/16.
 * 利用Parcelable传递数据（对象）
 */

public class Person implements Parcelable {

    private String name;
    private int age;
    private String address;
    private String birthday;
    private double reportCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getReportCard() {
        return reportCard;
    }

    public void setReportCard(double reportCard) {
        this.reportCard = reportCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //写入数据
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(address);
        dest.writeString(birthday);
        dest.writeDouble(reportCard);
    }
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.
            Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            //读取数据，必须按照写入数据的顺序读取，否则数据混乱
            Person person = new Person();
            person.name = source.readString();
            person.age = source.readInt();
            person.address = source.readString();
            person.birthday = source.readString();
            person.reportCard = source.readDouble();
            return person;
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
