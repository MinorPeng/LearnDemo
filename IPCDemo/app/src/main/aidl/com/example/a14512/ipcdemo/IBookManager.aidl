// IBookManager.aidl
package com.example.a14512.ipcdemo;

import com.example.a14512.ipcdemo.binder.Book;

// Declare any non-default types here with import statements

interface IBookManager {

    /**
     * 获取图书
     */
    List<Book> getBookList();

    /**
    * 添加图书
    * */
    void addBook(in Book book);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
