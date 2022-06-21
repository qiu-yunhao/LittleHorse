package com.example.horsey;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TableMeasure extends AppCompatActivity {

    private TextView button,button1,button2,button3;
    private ImageButton ib;
    private Button enterbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_game_options);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        button=findViewById(R.id.textView1);
        button1=findViewById(R.id.textView2);
        button2=findViewById(R.id.textView3);
        button3=findViewById(R.id.textView4);
        ib=findViewById(R.id.close);
        enterbutton=findViewById(R.id.enterbutton);
        setDecoration();
        beforeEnterButton();

    }

    private void beforeEnterButton() {
        enterbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast=Toast.makeText(getApplicationContext(),"请在左侧选择一个栏目哦！",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
        );
    }

    private void enterButton(Class test){
        enterbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(TableMeasure.this,test);
                        startActivity(intent);
                    }
                }
        );

    }

    private void setDecoration(){
        TableChosenMark tcm=new TableChosenMark();
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
                        tcm.tableChoose(button,button1,button2,button3);
                        enterbutton.setOnClickListener(null);
                        enterButton(ChooseActivity.class);
                    }
                }
        );
        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button1,button,button2,button3);
                        enterbutton.setOnClickListener(null);
                        enterButton(ChooseActivity2.class);
                    }
                }
        );
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button2,button1,button,button3);
                        enterbutton.setOnClickListener(null);
                        enterButton(ChooseActivity3.class);
                    }
                }
        );
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tcm.tableChoose(button3,button1,button2,button);
                        enterbutton.setOnClickListener(null);
                        enterButton(ChooseActivity4.class);
                    }
                }
        );
    }



}
