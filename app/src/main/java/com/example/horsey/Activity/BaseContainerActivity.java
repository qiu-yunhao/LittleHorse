package com.example.horsey.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.horsey.Fragment.BaseFragment;
import com.example.horsey.Game.Receiver;
import com.example.horsey.R;
import com.example.horsey.Utils.TimeCounterUtils;
import com.example.horsey.View.FragmentController;
import com.example.horsey.ViewModel.GameViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseContainerActivity extends AppCompatActivity implements FragmentController {
    protected List<BaseFragment> fragmentList = new ArrayList<>();
    protected ImageButton home, next, pre, close;
    protected ImageView button_avatar;
    protected TextView time, grade, title, bigLevel, smallLevel;
    protected ViewPager2 viewPager;
    private MediaPlayer player;
    private GameViewModel viewModel;
    private int pos = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_container);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        initView();
        initListener();
        setViewPager();
    }

    public void initView(){
        home = findViewById(R.id.base_home);
        next = findViewById(R.id.base_next);
        pre = findViewById(R.id.base_pre);

        time = findViewById(R.id.base_time);
        TimeCounterUtils.startTimeCounter(time);

        grade = findViewById(R.id.base_grades);
        viewModel.getGrades().observe(this, integer -> grade.setText(new StringBuilder().append(integer).append("分")));

        title = findViewById(R.id.base_title);
        bigLevel = findViewById(R.id.nowBigLevel);

        smallLevel = findViewById(R.id.nowSmallLevel);
        smallLevel.setText("当前关卡:1/5");

        close = findViewById(R.id.base_close);

        button_avatar = findViewById(R.id.base_avatar);

        viewPager=findViewById(R.id.game_viewpager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void initListener(){
        home.setOnClickListener(v -> {
            Intent intent = new Intent(Receiver.Activity_choose_grammar);
            sendBroadcast(intent);
            this.finish();
        });

        close.setOnClickListener(v -> {
            this.finish();
        });

        next.setOnClickListener(v -> {
            nextFragment();
        });

        pre.setOnClickListener(v -> {
            preFragment();
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pos = position;
                smallLevel.setText("当前关卡:"+String.valueOf(pos+1) + "/5");
            }
        });
    }

    public void playMusic(MediaPlayer player, String url){
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


    @Override
    public void preFragment() {
        if (pos > 0) {
            viewPager.setCurrentItem(--pos);
            smallLevel.setText("当前关卡:"+String.valueOf(pos+1) + "/5");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void nextFragment() {
        if (pos < fragmentList.size()-1){
            viewPager.setCurrentItem(++pos);
            smallLevel.setText("当前关卡:" + String.valueOf(pos+1) + "/5");
        }
    }

    @Override
    public void right() {
        viewModel.addGrades(2);
        button_avatar.setImageResource(R.drawable.right);
        playMusic(player, String.valueOf(R.raw.yes));
    }

    @Override
    public void error() {
        viewModel.subGrades();
        button_avatar.setImageResource(R.drawable.error);
        playMusic(player, String.valueOf(R.raw.no));
    }

    @Override
    public void repeatTitle() {
        button_avatar.performClick();
    }

    protected abstract void setViewPager();
}
