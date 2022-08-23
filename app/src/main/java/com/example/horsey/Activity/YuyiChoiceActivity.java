package com.example.horsey.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horsey.R;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class YuyiChoiceActivity extends AppCompatActivity {
    private ImageButton noun, adj, verb, prep, start, back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_meaning);
        initView();
        initListener();
    }

    private void initView(){
        noun=findViewById(R.id.cv_ib1);
        adj=findViewById(R.id.cv_ib2);
        verb=findViewById(R.id.cv_ib3);
        prep=findViewById(R.id.cv_ib4);
        start = findViewById(R.id.enter);
        back = findViewById(R.id.back);
    }

    private void initListener(){
        start.setOnClickListener(v -> {
            Intent intent = new Intent(this, YuyiActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(v -> {
            finish();
        });
    }
}
