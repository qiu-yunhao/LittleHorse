package com.example.horsey.Activity;

import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horsey.Game.Receiver;

import java.io.IOException;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void initView();

    //登记后记得在destroy部分删除
    public void registerBoarderReceiver(String activityName, Receiver receiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(activityName);
        registerReceiver(receiver,intentFilter);
    }

    public void playMusic(MediaPlayer player,String url){
        if(player == null){
            return;
        }
        if (player.isPlaying()){
            player.stop();
        }
        try{
            player.setDataSource(url);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.prepare();
        }catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

}
