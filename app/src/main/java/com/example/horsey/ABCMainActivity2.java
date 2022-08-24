package com.example.horsey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.horsey.View.GridViewAdapter;
import com.example.horsey.databinding.AbcActivityMainDbBinding;
import com.example.horsey.databinding.AbcResultListBinding;
import com.example.horsey.databinding.ClickToSeeResultBinding;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ABCMainActivity2 extends AppCompatActivity{
    public static final String KEYWORD="com.example.evasch";
    private static final String TAG = "ABCMainActivity2";

    private ABCViewModel abcViewModel;
    private AbcActivityMainDbBinding binding;
    private ClickToSeeResultBinding binding2;
    private AbcResultListBinding binding3;

    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        abcViewModel = new ViewModelProvider(this).get(ABCViewModel.class);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.abc_activity_main_db, null, false);
        setContentView(binding.getRoot());
        binding.setViewModel(abcViewModel);
        binding.setLifecycleOwner(this);    //数据观察必备

        abcViewModel.getCurProblem().observe(this, new Observer<EachProblemInfo>() {
            @Override
            public void onChanged(EachProblemInfo eachProblemInfo) {
                binding.image.setImageBitmap(eachProblemInfo.getImage());
            }
        });

        int normalColor = getResources().getColor(R.color.OptionNormalColor);
        int chosenColor = getResources().getColor(R.color.OptionChosenColor);
        abcViewModel.getCurOption().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.never.setBackgroundColor(normalColor);
                binding.seldom.setBackgroundColor(normalColor);
                binding.number.setText(abcViewModel.answeredNum()+"/"+ abcViewModel.getListSize());

                if(s == null) return;
                //为方便直接输入中文，若有多种选择类型，通过curProblem读取
                switch (s){
                    case "是":
                        binding.never.setBackgroundColor(chosenColor);break;
                    case "否":
                        binding.seldom.setBackgroundColor(chosenColor);break;
                }

                gridViewAdapter.notifyDataSetChanged();
                if (abcViewModel.canSubmit()){
                    binding.submit.setBackgroundColor(getResources().getColor(R.color.game));
                }
            }
        });

        gridViewAdapter = new GridViewAdapter(this, abcViewModel.isAnswered);
        binding.gridView.setAdapter(gridViewAdapter);
        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abcViewModel.updateQuestion(i+1);
                binding.drawerLayout.closeDrawer(binding.drawer);
            }
        });

        binding.drawerLayout.setScrimColor(Color.parseColor("#80ffffff"));
        binding.number.setOnClickListener(view -> binding.drawerLayout.openDrawer(binding.drawer));
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.closeDrawer(binding.drawer);
            }
        });

        binding2 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.click_to_see_result, null, false);
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!abcViewModel.canSubmit())
                    Toast.makeText(ABCMainActivity2.this, "题目还没答完哦，暂时还不能提交呢！", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(ABCMainActivity2.this, "提交成功！结果生成中...", Toast.LENGTH_SHORT).show();
                    abcViewModel.readyForResult();
                    setContentView(binding2.getRoot());
                }
            }
        });

        binding2.getresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding3 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.abc_result_list, null, false);
                setContentView(binding3.getRoot());
                abcViewModel.makeChart(binding3.radar);
                //布局无法获取viewModel的数据，暂时用以下代替，待解决
                binding3.button2.setText(abcViewModel.getName());
                binding3.button4.setText(abcViewModel.getGender());
                binding3.button6.setText(abcViewModel.getAge());
                binding3.button8.setText(abcViewModel.getDatetime());
                binding3.button10.setText(abcViewModel.getDescription());
            }
        });


        View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void back(View view){
        finish();
    }

    public void home(View view){
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}
