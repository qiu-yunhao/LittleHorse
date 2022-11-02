package com.example.horsey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseActivity3 extends AppCompatActivity {

    //存储所有题目对应的图片
    private ArrayList<Bitmap> arrBitmap = new ArrayList<>();
    //存储每一个题目、选项及分数
    private ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private int count = 1;

    private String s = "choose_speaking";
    private String needToChoose = "swim";

    private TextView name1, name2, name3;
    private ImageButton b1, b2, b3, left, right, back, enter;
    View decorView;
    private String option = "";
    private String json = "";
    WhichtoChoose whichtoChoose = new WhichtoChoose();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_grammar);
        decorView = getWindow().getDecorView();
        UIShrink();
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        b1 = findViewById(R.id.cv_ib1);
        b2 = findViewById(R.id.cv_ib2);
        b3 = findViewById(R.id.cv_ib3);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        back = findViewById(R.id.back);
        enter = findViewById(R.id.enter);
        init();
    }

    private void init() {
        getOptions();
        change();
        transactions();
    }

    private void UIShrink() {
//
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    private void transactions() {
        chooseAndEnter();
        fourButtons();
    }

    private void chooseAndEnter() {
        back.setOnClickListener(view -> finish());
        enter.setOnClickListener(view -> {
            if (s.equals(json)) {
                Toast.makeText(getApplicationContext(), "进入游戏！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity3.this, MainActivity.class);
                Bundle bundle = new Bundle();
                GameType gameType = new GameType("语法游戏", option, json, s, needToChoose);
                bundle.putSerializable("gameType", gameType);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "当前的等级还不能做这一部分哦！", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private String getJSONName(String str) {
        String JSONName = arrayList.get(count - 1).get(str);
        return JSONName;
    }

    private void fourButtons() {
        b1.setOnClickListener(view -> {
            option = getOptionName("opt1");
            json = getJSONName("pic1");
            whichtoChoose.chooseIt(b1, b2, b3);
        });
        b2.setOnClickListener(view -> {
            option = getOptionName("opt2");
            json = getJSONName("pic2");
            whichtoChoose.chooseIt(b2, b1, b3);
        });
        b3.setOnClickListener(view -> {
            option = getOptionName("opt3");
            json = getJSONName("pic3");
            whichtoChoose.chooseIt(b3, b2, b1);
        });

    }

    private String getOptionName(String str) {
        String optionName = arrayList.get(count - 1).get(str);
        return optionName;
    }

    private void getOptions() {
        try {
            StringBuffer buffer = new StringBuffer();
            InputStream inputStream = getAssets().open("yufa.json");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] b = new byte[1024];

            while (bufferedInputStream.read(b) != -1) {
                String sta = new String(b);
                buffer.append(sta);
            }

            String problems = buffer.toString();
            try {
                JSONArray jsonArray = new JSONArray(URLDecoder.decode(problems, "GB2312"));
                System.out.println(jsonArray.toString());
                //生成一个问题
                //for (int i = 0; i < jsonArray.length(); i++) {
                int i = 0;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("opt1", jsonArray.getJSONObject(i).getString("opt1"));
                map.put("pic1", jsonArray.getJSONObject(i).getString("pic1"));
                map.put("opt2", jsonArray.getJSONObject(i).getString("opt2"));
                map.put("pic2", jsonArray.getJSONObject(i).getString("pic2"));
                map.put("opt3", jsonArray.getJSONObject(i).getString("opt3"));
                map.put("pic3", jsonArray.getJSONObject(i).getString("pic3"));
                arrayList.add(map);
                //}
                //for(int i=0;i<jsonArray.length();i++){
                String name1 = arrayList.get(i).get("pic1") + ".png";
                BufferedInputStream bufferedInputStream1 = new BufferedInputStream(getAssets().open(name1));
                String name2 = arrayList.get(i).get("pic2") + ".png";
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(getAssets().open(name2));
                String name3 = arrayList.get(i).get("pic3") + ".png";
                BufferedInputStream bufferedInputStream3 = new BufferedInputStream(getAssets().open(name3));
                Bitmap bm1 = BitmapFactory.decodeStream(bufferedInputStream1);
                Bitmap bm2 = BitmapFactory.decodeStream(bufferedInputStream2);
                Bitmap bm3 = BitmapFactory.decodeStream(bufferedInputStream3);
                arrBitmap.add(bm1);
                arrBitmap.add(bm2);
                arrBitmap.add(bm3);
                //}

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void change() {
        whichtoChoose.clear(b1, b2, b3);
        option = "";
        name1.setText(arrayList.get(0).get("opt1"));
        name2.setText(arrayList.get(0).get("opt2"));
        name3.setText(arrayList.get(0).get("opt3"));
        b1.setImageBitmap(arrBitmap.get(0));
        b2.setImageBitmap(arrBitmap.get(1));
        b3.setImageBitmap(arrBitmap.get(2));
    }
}