package com.example.hp.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String newId;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        Button addData = (Button) findViewById(R.id.add_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        addData.setOnClickListener(this);
        queryData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_data:
                Uri uriAdd = Uri.parse("content://com.example.databasetest.provider/book");
                ContentValues valuesAdd = new ContentValues();
                valuesAdd.put("name", "A Clash of Kings");
                valuesAdd.put("author", "George Martin");
                valuesAdd.put("pages", 520);
                valuesAdd.put("price", 11.21);
                Uri newUri = getContentResolver().insert(uriAdd, valuesAdd);
                newId = newUri.getPathSegments().get(1);
                Log.d("123456",";"+newId);
                break;
            case R.id.query_data:
                Uri uriQuery = Uri.parse("content://com.example.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uriQuery, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int ages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        mTextView.setText(name + "\n" + author + "\n" + ages + "\n" + price);
                        int i = 1;
                        Log.d("123456", ":"
                                + name + "\n" + author + "\n" + ages + "\n" + price + "\n"+i);
                        i++;

                    }
                    cursor.close();
                }
                Log.d("123456",":");
                break;
            case R.id.update_data:
                Uri uriUpdate = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                ContentValues valuesUpdate = new ContentValues();
                valuesUpdate.put("name", "A Storm of Swords");
                valuesUpdate.put("pages", 1325945);
                valuesUpdate.put("price", 25.25);
                getContentResolver().update(uriUpdate, valuesUpdate, null, null);
                break;
            case R.id.delete_data:
                Uri uriDelete = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                getContentResolver().delete(uriDelete, null, null);
                break;
            default:
                break;
        }
    }
}
