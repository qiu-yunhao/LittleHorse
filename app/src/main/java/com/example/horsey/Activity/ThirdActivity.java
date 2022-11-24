package com.example.horsey.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horsey.Bean.Help;
import com.example.horsey.Fragment.BaseFragment;
import com.example.horsey.Fragment.OneTypeFragment;
import com.example.horsey.Fragment.SecondTypeFragment;
import com.example.horsey.Fragment.ThirdTypeFragment;
import com.example.horsey.Game.Receiver;
import com.example.horsey.Model.Adapter.ThirdViewPagerAdapter;
import com.example.horsey.R;
import com.example.horsey.View.FragmentController;
import com.example.horsey.ViewModel.GameViewModel;



import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

public class ThirdActivity extends BaseActivity implements FragmentController {
    public static final String TAG = "ThirdActivity";
    private ViewPager2 viewPager;
    private TextView textView_time;

    private int type, pos;
    private int time, count;
    private AppCompatImageButton button_avatar;
    private MediaPlayer play;
    private GameViewModel viewModel;
    private TextView textView_page;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        type = intent.getIntExtra(TAG, 0);
        pos = 0;
        time = 0;
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        initView();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView() {
        GameViewModel game = new ViewModelProvider(this).get(GameViewModel.class);
        viewPager = findViewById(R.id.game_viewpager_3);

        //可能有问题
        viewPager.setUserInputEnabled(false);
        TextView textView_now = findViewById(R.id.third_type);
        textView_now.setText(getNow());
        TextView textView_grades = findViewById(R.id.third_grades);
        textView_time = findViewById(R.id.third_time);
        textView_page = findViewById(R.id.third_page);
        play = new MediaPlayer();
        button_avatar = findViewById(R.id.third_avatar);
        AppCompatImageButton button_home = findViewById(R.id.Third_home);
        button_home.setOnClickListener(v -> {
            Intent intent = new Intent(Receiver.Activity_choose_grammar);
            sendBroadcast(intent);
            this.finish();
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pos = position;
            }
        });
        new Timer().schedule(new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                time += 1;
                textView_time.setText(String.format("%02d:%02d",time / 60,time % 60));
            }
        },0,1000);
        Thread thread = new GameViewModel.TimeThread();
        game.getGrades().observe(this, integer -> textView_grades.setText(new StringBuilder().append(integer).append("分")));
        thread.start();
        Help help = new Help();
        button_avatar.setOnClickListener(v -> {
            int i = (type == 1) ? pos : ((type == 2) ? type + pos : ((type == 3) ? 4 : 0));
            playMusic(play, help.getData(i).getMusic());
        });
        AppCompatImageButton pre = findViewById(R.id.third_pre);
        pre.setOnClickListener(v -> {
            preFragment();
        });

        AppCompatImageButton next = findViewById(R.id.third_next);
        next.setOnClickListener(v-> {
            nextFragment();
        });
        AppCompatImageButton close = findViewById(R.id.third_close);
        close.setOnClickListener(v->{
            this.finish();
        });
        
    }

    //Type
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<BaseFragment> getFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        Help help = new Help();
        if (type == 1) {
            fragments.add(new OneTypeFragment(help.getData(0).getTitle()));
        } else if (type == 2) {
            fragments.add(SecondTypeFragment.newInstance(3));
            ThirdTypeFragment fragment = ThirdTypeFragment.newInstance(4);
            fragment.setOutPutBackGround(R.drawable.house_2);
            fragment.setBackGround(R.drawable.bg_3);
            fragments.add(fragment);
            fragment = ThirdTypeFragment.newInstance(5);
            fragment.setBackGround(R.drawable.bg_3);
            fragment.setOutPutBackGround(R.drawable.house_3);
            fragments.add(fragment);
        } else if (type == 3) {
            fragments.add(SecondTypeFragment.newInstance(6));
            ThirdTypeFragment fragment =ThirdTypeFragment.newInstance(7);
            fragment.setBackGround(R.drawable.bg_2);
            fragment.changeInputLayoutType(ThirdTypeFragment.STATUS.horizontal);
            fragment.changeOutputLayoutType(ThirdTypeFragment.STATUS.horizontal);
            fragments.add(fragment);
            fragment = ThirdTypeFragment.newInstance(8);
            fragment.setBackGround(R.drawable.bg_1);
            fragments.add(fragment);

        } else {
            Toast.makeText(this, "加载错误", Toast.LENGTH_SHORT).show();
        }
        for (BaseFragment baseFragment : fragments){
            baseFragment.setController(this);
        }
        return fragments;
    }

    private String getNow(){
        switch (type){
            case 1:return "动词短语";
            case 2:return "介词短语";
            case 3:return "综合情景";
            default:return "error";
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        ThirdViewPagerAdapter adapter = new ThirdViewPagerAdapter(this, getFragments());
        count = adapter.getItemCount();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        button_avatar.performClick();
        textView_page.setText("1/" + count);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void preFragment() {
        if (pos > 0) {
            viewPager.setCurrentItem(--pos);
        }
        textView_page.setText( (pos + 1) + "/" + count);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void nextFragment() {
        if (pos < count - 1)
            viewPager.setCurrentItem(++pos);
        textView_page.setText( (pos + 1) + "/" + count);
    }

    @Override
    public void right() {
        viewModel.addGrades(2);
        button_avatar.setImageResource(R.drawable.right);
        playMusic(play, String.valueOf(R.raw.yes));
    }

    @Override
    public void error() {
        viewModel.subGrades();
        button_avatar.setImageResource(R.drawable.error);
        playMusic(play, String.valueOf(R.raw.no));
    }

    @Override
    public void repeatTitle() {
        button_avatar.performClick();
    }

}