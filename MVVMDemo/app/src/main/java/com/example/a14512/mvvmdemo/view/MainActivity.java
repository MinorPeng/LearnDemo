package com.example.a14512.mvvmdemo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.a14512.mvvmdemo.R;
import com.example.a14512.mvvmdemo.databinding.ActivityMainBinding;
import com.example.a14512.mvvmdemo.mode.ObservableFieldUser;
import com.example.a14512.mvvmdemo.mode.UserBean;
import com.example.a14512.mvvmdemo.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModel viewModel = new ViewModel(binding);
        final UserBean user = new UserBean("Tom", 20);
        binding.setUser(user);
        final ObservableFieldUser fieldUser = new ObservableFieldUser("TT", 22);
        binding.setUser2(fieldUser);

        List<String> list = new ArrayList<>();
        list.add("list1");
        list.add("list2");
        binding.setList(list);

        HashMap<String, Object> map = new HashMap<>();
        map.put("key0", "map_value0");
        map.put("key1", "map_value1");
        binding.setMap(map);

        String[] arrays = {"字符串1", "字符串2"};
        binding.setArray(arrays);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.setName("Hello");
                user.setAge(21);
                fieldUser.mName.set("World");
                fieldUser.mAge.set(23 );
            }
        }, 1000);


    }
}
