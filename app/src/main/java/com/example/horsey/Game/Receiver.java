package com.example.horsey.Game;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

public class Receiver extends BroadcastReceiver {
    private final WeakReference<Activity> activityWeakReference;
    //需要关闭的Activity的名称,继续添加就行
    public static final String ActivityName = "";

    public Receiver(Activity activity){
        activityWeakReference = new WeakReference<>(activity);
    }

    //收到广播的信号处理
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case ActivityName:
                activityWeakReference.get().finish();
                break;
        }
    }
}
