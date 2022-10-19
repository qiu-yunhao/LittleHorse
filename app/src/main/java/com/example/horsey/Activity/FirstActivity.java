package com.example.horsey.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.horsey.Fragment.BaseFragment;
import com.example.horsey.Fragment.VoiceChoiceFragment;
import com.example.horsey.R;
import com.example.horsey.ViewModel.GameViewModel;
import com.example.horsey.Model.Adapter.ThirdViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends BaseActivity {
    public static String TAG = "FirstActivity";
    private MutableLiveData<Integer> grades;
    private GameViewModel viewModel;
    private ViewPager2 viewPager2;
    private Thread thread;
    private int pos = 0; //记录当前所指的Fragment的下标
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        Intent intent = getIntent();
        pos = intent.getIntExtra(TAG,0);
        initView();

    }

    @Override
    public void initView() {
        grades = new MutableLiveData<>(0);
        TextView textView = findViewById(R.id.textView9);
        textView.setText("语音游戏");
        TextView timer = findViewById(R.id.third_time);
        thread = new GameViewModel.TimeThread();
        GameViewModel.getTime().observe(this, a -> timer.setText(String.valueOf(a)));
        thread.start();
        viewPager2 = findViewById(R.id.game_viewpager_3);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        ThirdViewPagerAdapter adapter = new ThirdViewPagerAdapter(this,getFragments());
        viewPager2.setAdapter(adapter);
    }


    private List<BaseFragment> getFragments() {
        List<BaseFragment> res = new ArrayList<>();
        for(int i = 0 ; i < 4 ; i++) {
            BaseFragment fragment = VoiceChoiceFragment.newInstance(i);
            res.add(fragment);
        }
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }
}
