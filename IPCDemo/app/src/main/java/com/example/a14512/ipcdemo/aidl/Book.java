package com.example.a14512.ipcdemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 14512 on 2018/10/8
 */
public class Book implements Parcelable {

    private int mId;
    private String mName;

    public Book(int bookId, String bookName) {
        this.mId = bookId;
        this.mName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
    }
}
