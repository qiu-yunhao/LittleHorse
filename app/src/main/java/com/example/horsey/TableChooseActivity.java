package com.example.horsey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TableChooseActivity extends AppCompatActivity {

    View decorView;
    private ImageButton ib;
    private TextView button, button1, button2, button3, button4, button5;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_choose);
        decorView = getWindow().getDecorView();
        UIShrink();
    }

    private void UIShrink() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        button = findViewById(R.id.textView1);
        button1 = findViewById(R.id.textView2);
        button2 = findViewById(R.id.textView3);
        button3 = findViewById(R.id.textView4);
        button4 = findViewById(R.id.textView5);
        button5 = findViewById(R.id.textView6);
        ib = findViewById(R.id.closeChart);
        enter = findViewById(R.id.enterChartButton);
        setDecoration();
        beforeEnterButton();

    }

    private void beforeEnterButton() {
        enter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getApplicationContext(), "请在左侧选择一个栏目哦！", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
        );
    }

    private void enterButton(Class test) {
        enter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getApplicationContext(), "正在加载中，请稍候...", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(TableChooseActivity.this, test);
                        startActivity(intent);
                    }
                }
        );

    }

    private void setDecoration() {
        TableChosenMark tcm = new TableChosenMark();
        ib.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button, button1, button2, button3, button4, button5);
                        enter.setOnClickListener(null);
                        enterButton(ABCStartActivity.class);
                    }
                }
        );
        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button1, button, button2, button3, button4, button5);
                        enter.setOnClickListener(null);
                        enterButton(CABSStartActivity.class);
                        //enterButton(ChooseActivity2.class);

                    }
                }
        );
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button2, button1, button, button3, button4, button5);
                        enter.setOnClickListener(null);
                        enterButton(CARSStartActivity.class);
                        //enterButton(ChooseActivity3.class);

                    }
                }
        );
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button3, button1, button2, button, button4, button5);
                        enter.setOnClickListener(null);
                        enterButton(MChartStartActivity.class);
                        //enterButton(ChooseActivity4.class);

                    }
                }
        );
        button4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button4, button1, button2, button3, button, button5);
                        enter.setOnClickListener(null);
                        enterButton(PCDISentencesStartActivity.class);
                        //enterButton(ChooseActivity4.class);

                    }
                }
        );
        button5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button5, button1, button2, button3, button4, button);
                        enter.setOnClickListener(null);
                        enterButton(PCDIGestureStartActivity.class);
                        //enterButton(ChooseActivity4.class);

                    }
                }
        );
    }


}