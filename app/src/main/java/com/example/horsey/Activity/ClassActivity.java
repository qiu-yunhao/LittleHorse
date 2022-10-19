package com.example.horsey.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.horsey.Fragment.RegisterFragment;
import com.example.horsey.Fragment.StudentFragment;
import com.example.horsey.Model.Adapter.BaseAdapter;
import com.example.horsey.Model.Adapter.ClassAdapter;
import com.example.horsey.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassActivity extends AppCompatActivity implements BaseAdapter.ClickListener {

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        initView();
    }

    private void initView() {
        TextView setting = findViewById(R.id.setting);
        TextView back = findViewById(R.id.back_to_main);
        back.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.class_RecyclerView);
        ClassAdapter adapter = new ClassAdapter(getList(), this);
        Log.d("list", String.valueOf(getList().size()));
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private List<String> getList() {
        String[] ans = {"注册", "用户管理", "游戏信息查询"};
        List<String> res = new ArrayList<>();
        Collections.addAll(res, ans);
        return res;
    }

    @Override
    public void onClick(int pos) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (pos){
            case 0:
                ft.replace(R.id.class_frameLayout, new RegisterFragment()).commit();
                break;
            case 1:
                ft.replace(R.id.class_frameLayout, new StudentFragment()).commit();
                break;
            case 2:
                break;
        }
    }
}