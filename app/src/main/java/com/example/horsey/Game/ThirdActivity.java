package com.example.horsey.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horsey.Activity.BaseActivity;
import com.example.horsey.R;
import com.example.horsey.View.FragmentController;
import com.example.horsey.ViewModel.ThirdViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends BaseActivity implements FragmentController {
    public static final String TAG = "ThirdActivity";
    private ViewPager2 viewPager;
    private TextView textView_grades;
    private TextView textView_time;
    private AppCompatImageButton button_avatar;
    private int type ,pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        type = intent.getIntExtra(TAG,0);
        pos = 0;
        initView();
    }


    @Override
    public void initView() {
        viewPager = findViewById(R.id.game_viewPager_3);
        textView_grades = findViewById(R.id.third_grades);
        textView_time = findViewById(R.id.third_time);
        button_avatar = findViewById(R.id.third_avatar);
        AppCompatImageButton button_home = findViewById(R.id.Third_home);
        button_home.setOnClickListener(v->{
            this.finish();
        });
        ThirdViewPagerAdapter adapter = new ThirdViewPagerAdapter(this,getFragments());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    //Type
    public List<Fragment> getFragments(){
        List<Fragment> fragments = new ArrayList<>();
        if (type == 1){

        } else if(type == 2){

        } else if(type == 3) {

        } else{
            Toast.makeText(this,"加载错误",Toast.LENGTH_SHORT).show();
        }
        return  fragments;
    }

    @Override
    public void nextFragment() {
        if(pos < getFragments().size())
            viewPager.setCurrentItem(++pos);
    }
}