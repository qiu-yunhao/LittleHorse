package com.example.horsey.Activity;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.horsey.Fragment.BaseFragment;
import com.example.horsey.Fragment.VoiceChoiceFragment;
import com.example.horsey.R;
import com.example.horsey.Model.Adapter.ThirdViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//TODO 语音的条目选择
public class ChoiceSecondActivity extends BaseActivity implements PassActivityNumber {

    private ViewPager2 viewPager;
    private int page = 0;
    private int size = 0;
    private int now = -1; // 记录当前的选择条目序号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_second);
        initView();
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.voice_viewPager);
        viewPager.setCurrentItem(page);
        ThirdViewPagerAdapter adapter = new ThirdViewPagerAdapter(this, getChoiceFragments());
        viewPager.setAdapter(adapter);
        size = adapter.getItemCount();
        ImageButton left = findViewById(R.id.choice_left);
        ImageButton right = findViewById(R.id.choice_right);
        TextView title = findViewById(R.id.voice_title);
        title.setText(translateLevel(page));
        left.setOnClickListener(v -> {
            page = (size + page - 1) % size;
            title.setText(translateLevel(page));
            viewPager.setCurrentItem(page);
        });

        right.setOnClickListener(v -> {
            page = (page + 1) % size;
            title.setText(translateLevel(page));
            viewPager.setCurrentItem(page);
        });

        Button button = findViewById(R.id.choice_start);
        button.setOnClickListener(view -> {
            Intent i = new Intent(this, FirstActivity.class);
            i.putExtra(FirstActivity.TAG,now);
            startActivity(i);
        });
    }

    private List<BaseFragment> getChoiceFragments() {
        List<BaseFragment> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            VoiceChoiceFragment fragment = VoiceChoiceFragment.newInstance(i);
            fragment.setPass(this);
            list.add(fragment);
        }
        return list;
    }

    private String translateLevel(int page) {
        switch (page) {
            case 0:
                return "等级一";
            case 1:
                return "等级二";
            case 2:
                return "等级三";
            case 3:
                return "等级四";
            default:
                return "等级zero";
        }
    }

    @Override
    public void passNumber(int num) {
        now = num;
    }
}