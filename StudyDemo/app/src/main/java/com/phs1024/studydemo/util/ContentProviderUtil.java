package com.phs1024.studydemo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.phs1024.studydemo.report.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PHS1024
 * @date 2019/10/28 11:00:42
 */
public class ContentProviderUtil {

    private static final String TAG = "ContentProviderUtil";

    private ContentProviderUtil() {

    }

    public static List<Contact> getContacts(@NonNull Context context) {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        ContentResolver resolver = context.getContentResolver();
        if (resolver == null) {
            Log.e(TAG, "resolver is null");
            return null;
        }
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if (cursor == null) {
            Log.e(TAG, "cursor is null");
            return null;
        }
        List<Contact> contacts = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                //根据联系人的ID获取此人的电话号码
                ContentResolver phoneResolver = context.getContentResolver();
                if (phoneResolver == null) {
                    Log.e(TAG, "phone resolver is null");
                    continue;
                }
                Cursor phonesCursor = phoneResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCursor == null) {
                    Log.e(TAG, "phone cursor is null");
                    continue;
                }
                if (phonesCursor.moveToFirst()) {
                    String phone = phonesCursor.getString(0);
                    Contact contact = new Contact(name, phone);
                    contacts.add(contact);
                }
                phonesCursor.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }
}
