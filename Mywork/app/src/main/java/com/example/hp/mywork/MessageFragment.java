package com.example.hp.mywork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/1/21.
 */

public class MessageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.message_layout_fragment, container, false);
        RecyclerView messageDataRecyclerView = (RecyclerView) messageLayout.findViewById(R.id.message_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        messageDataRecyclerView.setLayoutManager(layoutManager);
        DataAdapter dataAdapter = new DataAdapter(getDatas());
        messageDataRecyclerView.setAdapter(dataAdapter);
        return messageLayout;
    }

    private List<Data> getDatas() {
        List<Data> messageDataList = new ArrayList<>();
        for (int i  = 1; i <= 10; i++) {
            Data data = new Data();
            data.
        }
    }
}
