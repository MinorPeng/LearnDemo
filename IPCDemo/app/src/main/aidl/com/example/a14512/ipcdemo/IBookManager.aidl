// IBookManager.aidl
package com.example.a14512.ipcdemo;

// Declare any non-default types here with import statements
import com.example.a14512.ipcdemo.aidl.Book;
import com.example.a14512.ipcdemo.IOnNewBookListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookListener listener);
    void unregisterListener(IOnNewBookListener listener);
}
