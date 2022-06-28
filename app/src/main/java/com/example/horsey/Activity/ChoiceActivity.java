package com.example.horsey.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.horsey.Game.Receiver;
import com.example.horsey.R;

public class ChoiceActivity extends BaseActivity {
    private Receiver receiver;
    private int type;
    private AppCompatImageButton role,color,toy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_grammar);
        type = 0;
        initView();
    }

    @Override
    public void initView() {
        receiver = new Receiver(this);
        registerBoarderReceiver(Receiver.Activity_choose_grammar, receiver);
        role = findViewById(R.id.choose_role);
        color = findViewById(R.id.choose_color);
        toy = findViewById(R.id.choose_toy);
        ImageView start = findViewById(R.id.third_enter);
        role.setOnClickListener(view -> {
            type = 1;
            Toast.makeText(ChoiceActivity.this,"点击人物",Toast.LENGTH_SHORT).show();
            //setBack();
        });
        color.setOnClickListener(view -> {
            type = 2;
            Toast.makeText(ChoiceActivity.this,"点击颜色",Toast.LENGTH_SHORT).show();
            //setBack();
        });
        toy.setOnClickListener(view -> {
            type = 3;
            Toast.makeText(ChoiceActivity.this,"点击玩具",Toast.LENGTH_SHORT).show();
            //setBack();
        });
        start.setOnClickListener(view -> {
            Intent i = new Intent(ChoiceActivity.this, ThirdActivity.class);
            i.putExtra(ThirdActivity.TAG,type);
            startActivity(i);
        });


    }

    @Override
    protected void onDestroy() {
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

}