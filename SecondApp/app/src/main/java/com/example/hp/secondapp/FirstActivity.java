package com.example.hp.secondapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/1/16.
 */

public class FirstActivity extends Activity {

    private List<Contacts> mContactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        initContacts();  //实例化Contacts
        ContactsAdapter adapter = new ContactsAdapter(FirstActivity.this,
                R.layout.contacts_item, mContactsList);
        ListView listView = (ListView) findViewById(R.id.list_contacts);
        listView.setAdapter(adapter);

        //监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts = mContactsList.get(position);
                Toast.makeText(FirstActivity.this, contacts.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("extra_data", contacts.getName());
                startActivity(intent);

            }
        });
    }

    private void initContacts() {
        Contacts contacts1 = new Contacts("联系人1", R.drawable.hugh);
        mContactsList.add(contacts1);

        Contacts contacts2 = new Contacts("联系人2", R.drawable.hugh);
        mContactsList.add(contacts2);

        Contacts contacts3 = new Contacts("联系人3", R.drawable.contacts);
        mContactsList.add(contacts3);

        Contacts contacts4 = new Contacts("联系人4", R.drawable.contacts);
        mContactsList.add(contacts4);

        Contacts contacts5 = new Contacts("联系人5", R.drawable.contacts);
        mContactsList.add(contacts5);

        Contacts contacts6 = new Contacts("联系人6", R.drawable.contacts);
        mContactsList.add(contacts6);

        Contacts contacts7 = new Contacts("联系人7", R.drawable.contacts);
        mContactsList.add(contacts7);

        Contacts contacts8 = new Contacts("联系人8", R.drawable.contacts);
        mContactsList.add(contacts8);

        Contacts contacts9 = new Contacts("联系人9", R.drawable.contacts);
        mContactsList.add(contacts9);

        Contacts contacts10 = new Contacts("联系人10", R.drawable.contacts);
        mContactsList.add(contacts10);

        Contacts contacts11 = new Contacts("联系人11", R.drawable.contacts);
        mContactsList.add(contacts11);

        Contacts contacts12 = new Contacts("联系人12", R.drawable.contacts);
        mContactsList.add(contacts12);

        Contacts contacts13 = new Contacts("联系人13", R.drawable.contacts);
        mContactsList.add(contacts13);

        Contacts contacts14 = new Contacts("联系人14", R.drawable.contacts);
        mContactsList.add(contacts14);

        Contacts contacts15 = new Contacts("联系人15", R.drawable.contacts);
        mContactsList.add(contacts15);

        Contacts contacts16 = new Contacts("联系人16", R.drawable.contacts);
        mContactsList.add(contacts16);
    }
}
