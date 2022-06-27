package com.example.horsey.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import com.example.horsey.Game.Receiver;
import com.example.horsey.R;
import com.example.horsey.View.FragmentController;
import com.example.horsey.ViewModel.GameViewModel;
import com.example.horsey.ViewModel.ThirdViewPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThirdActivity extends BaseActivity implements FragmentController {
    public static final String TAG = "ThirdActivity";
    private ViewPager2 viewPager;
    private TextView textView_time;
    private MutableLiveData<Long> time;
    private int type, pos;
    private SimpleDateFormat format;
    private AppCompatImageButton button_avatar;
    private MediaPlayer play;
    private GameViewModel viewModel;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        type = intent.getIntExtra(TAG, 0);
        pos = 0;
        time = new MutableLiveData<>(0L);
        format = new SimpleDateFormat("mm:ss");
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        initView();
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView() {
        GameViewModel game = new ViewModelProvider(this).get(GameViewModel.class);
        viewPager = findViewById(R.id.game_viewpager_3);
        TextView textView_grades = findViewById(R.id.third_grades);
        textView_time = findViewById(R.id.third_time);
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
        time.observe(this, aLong -> textView_time.setText(format.format(new Date(aLong))));
        Thread thread = new TimeThread();
        game.getGrades().observe(this, integer -> textView_grades.setText(new StringBuilder().append(integer).append("分")));
        thread.start();
        Help help = new Help();
        button_avatar.setOnClickListener(v -> {
            int i = (type == 1) ? pos : ((type == 2) ? type + pos : ((type == 3) ? 4 : 0));
            playMusic(play, help.getData(i).getMusic());
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
            fragments.add(SecondTypeFragment.newInstance(2));
        } else if (type == 3) {
            fragments.add(SecondTypeFragment.newInstance(4));
        } else {
            Toast.makeText(this, "加载错误", Toast.LENGTH_SHORT).show();
        }
        for (BaseFragment baseFragment : fragments){
            baseFragment.setController(this);
        }
        return fragments;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        ThirdViewPagerAdapter adapter = new ThirdViewPagerAdapter(this, getFragments());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        button_avatar.performClick();
    }

    @Override
    public void perFragment() {
        if (pos > 0) {
            viewPager.setCurrentItem(--pos);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void nextFragment() {
        if (pos < getFragments().size())
            viewPager.setCurrentItem(++pos);
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

    class TimeThread extends Thread {
        private long t = 0;

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(1000);
                    t += 1000;
                    time.postValue(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}