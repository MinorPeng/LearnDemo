// IOnNewBookListener.aidl
package com.example.a14512.ipcdemo;

// Declare any non-default types here with import statements
import com.example.a14512.ipcdemo.aidl.Book;

interface IOnNewBookListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book newBook);
}
