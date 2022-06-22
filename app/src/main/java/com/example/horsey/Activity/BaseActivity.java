package com.example.horsey.Activity;

import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horsey.Game.Receiver;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void initView();

    //登记后记得在destroy部分删除
    public void registerBoarderReceiver(String activityName, Receiver receiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(activityName);
        registerReceiver(receiver,intentFilter);
    }

}
