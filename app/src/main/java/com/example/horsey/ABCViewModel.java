package com.example.horsey;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ABCViewModel extends AndroidViewModel{
    public static String TAG = "ABCViewModel";

    private ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    private MutableLiveData<EachProblemInfo> curProblem;
    private MutableLiveData<String> curOption;

    //统计用户选择数据
    private String[] chosenButton;
    private String[] splitScore_type; //分别对应得分类型：F/R/B/L/S
    private int[] splitScore_int;
    public boolean[] isAnswered;
    private int count = 1;

    //统计结果
    private int totalScore=0;
    private String[] result=new String[6];
    private int scoreF, scoreR, scoreB, scoreL, scoreS;
    private String name, gender, age, datetime, description;

    public ABCViewModel(@NonNull Application application) {
        super(application);
        getAllWidgets(getApplication());
    }

    //加载问题及处理事务
    public void getAllWidgets(Context context){
        initData(context);
        updateQuestion(count);
    }

    public void initData(Context context){
        arrayList = new ABCModel().getProblems(context);

        curProblem = new MutableLiveData<>();
        curOption = new MutableLiveData<>();

        chosenButton = new String[arrayList.size()];
        splitScore_type = new String[arrayList.size()];
        splitScore_int = new int[arrayList.size()];
        isAnswered = new boolean[arrayList.size()];
    }

    public void updateQuestion(int count){
        this.count = count;
        String strs = "ABC"+(count)+".png";
        Bitmap image = getBitmap(getApplication(), strs);
        String title = arrayList.get(count-1).get("title");
        String option1 = arrayList.get(count-1).get("opt1name");
        String option2 = arrayList.get(count-1).get("opt2name");

        EachProblemInfo temp = new EachProblemInfo(count, image, title, option1, option2);
        curProblem.setValue(temp);

        curOption.setValue(chosenButton[count-1]);
    }

    public void option1Click(){
        isAnswered[count-1]=true;

        int curScore = Integer.parseInt(arrayList.get(count - 1).get("opt1score"));
        String type = arrayList.get(count-1).get("type");
        verifyCategory(type,curScore);

        curOption.setValue(curProblem.getValue().getOption1());
        chosenButton[count-1] = curOption.getValue();

        rightClick();
    }

    public void option2Click(){
        isAnswered[count-1]=true;

        int curScore = Integer.parseInt(arrayList.get(count - 1).get("opt2score"));
        String type = arrayList.get(count-1).get("type");
        verifyCategory(type,curScore);

        curOption.setValue(curProblem.getValue().getOption2());
        chosenButton[count-1] = curOption.getValue();

        rightClick();
    }

    private void verifyCategory(String type, int score) {
        switch (type){
            case "F": splitScore_type[count-1] = "F"; break;
            case "R": splitScore_type[count-1] = "R"; break;
            case "B": splitScore_type[count-1] = "B"; break;
            case "L": splitScore_type[count-1] = "L"; break;
            case "S": splitScore_type[count-1] = "S"; break;
        }
        splitScore_int[count-1] = score;
        Log.e(TAG, "score: "+splitScore_int[count-1]);
    }

    public boolean canSubmit(){
        for(String s : chosenButton){
            if(s == null){
                return false;
            }
        }
        return true;
    }

    public int answeredNum(){
        int num = 0;
        for (Boolean b : isAnswered){
            if (b) num++;
        }
        return num;
    }

    public void leftClick(){
        if (count > 1){
            count--;
        }else if(count== 1){
            count = arrayList.size();
        }
        updateQuestion(count);
    }

    public void rightClick(){
        if (count < arrayList.size()){
            count++;
        }
        else if(count == arrayList.size())
            count = 1;
        updateQuestion(count);
    }

    private Bitmap getBitmap(Context context, String name){
        BufferedInputStream bufferedInputStream1= null;
        try {
            bufferedInputStream1 = new BufferedInputStream(context.getAssets().open(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize = 3;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm = BitmapFactory.decodeStream(bufferedInputStream1,null,options);
        return bm;
    }

    public MutableLiveData<EachProblemInfo> getCurProblem() {
        return curProblem;
    }

    public int getListSize(){
        return arrayList.size();
    }

    public MutableLiveData<String> getCurOption() {
        return curOption;
    }

    /*
    * 以下是结果统计相关方法
    * */

    public void readyForResult() {
        for (int i:splitScore_int){
            totalScore += i;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        long currentTimeMills=System.currentTimeMillis();
        datetime=sdf.format(currentTimeMills);

        result = new ABCModel().getResultList(getApplication());
        name = result[0];
        gender = result[1];
        age = result[2];
        if (totalScore <= 31)
            description = "本次量表得分为：" + totalScore + "分\n\n" + result[3];
        else if(totalScore <= 53)
            description = "本次量表得分为：" + totalScore + "分\n\n" + result[4];
        else
            description = "本次量表得分为：" + totalScore + "分\n\n" + result[5];

        Log.e(TAG, name);

        for (int i=0 ; i<splitScore_type.length; i++){
            String s = splitScore_type[i];
            if(s == null) continue;
            switch (s){
                case "F": scoreF += splitScore_int[i]; break;
                case "R": scoreR += splitScore_int[i]; break;
                case "B": scoreB += splitScore_int[i]; break;
                case "L": scoreL += splitScore_int[i]; break;
                case "S": scoreS += splitScore_int[i]; break;
            }
        }
    }

    public void makeChart(RadarChart radar){
        List<RadarEntry> list = new ArrayList<>();

        //分类统计分数！！
        list.add(new RadarEntry(scoreF/0.3f));                          //***************将50替换为（‘感觉’类所得分数➗0.3）
        list.add(new RadarEntry(scoreR/0.44f));                          //***************将86替换为（‘交往’类所得分数➗0.44）
        list.add(new RadarEntry(scoreB/0.28f));                          //***************将29替换为（‘躯体运动’类所得分数➗0.28）
        list.add(new RadarEntry(scoreL/0.31f));                          //***************将58替换为（‘语言’类所得分数➗0.31）
        list.add(new RadarEntry(scoreS/0.25f));                          //***************将73替换为（‘生活自理’类所得分数➗0.25）

        RadarDataSet radarDataSet = new RadarDataSet(list,"孩子得分情况");
        radarDataSet.setColor(Color.parseColor("#65B1BF"));
        RadarData radarData = new RadarData(radarDataSet);
        radar.setData(radarData);

        radarDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return v+"%";
            }
        });

        radar.getYAxis().setAxisMinimum(0);


        radar.setWebColor(Color.GRAY);
        radar.setWebColorInner(Color.GRAY);


        radar.setBackgroundColor(Color.parseColor("#fafafa"));


        radarDataSet.setFillColor(Color.parseColor("#65B1BF"));
        radarDataSet.setDrawFilled(true);

        radarDataSet.setLineWidth(3);


        XAxis xAxis=radar.getXAxis();

        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if(v==0)
                {
                    return "感觉（S）";
                }
                if(v==1)
                {
                    return "交往（R）";
                }
                if(v==2)
                {
                    return "躯体运动（B）";
                }
                if(v==3)
                {
                    return "语言（L）";
                }
                if(v==4)
                {
                    return "生活自理（S）";
                }
                return "";
            }
        });

        radarDataSet.setDrawValues(false);
        radarDataSet.setValueTextSize(10);
        radarDataSet.setValueTextColor(Color.BLACK);


        YAxis yAxis=radar.getYAxis();
        yAxis.setValueFormatter(new PercentFormatter());

        yAxis.setDrawLabels(true);
        yAxis.setTextColor(Color.BLACK);

        yAxis.setAxisMaximum(80);


        yAxis.setLabelCount(4,false);

        radar.getDescription().setEnabled(false);

        Legend legend=radar.getLegend();
        legend.setEnabled(false);


        radarDataSet.setHighlightLineWidth(1f);
        radarDataSet.setDrawHighlightCircleEnabled(true);
        radarDataSet.setHighlightCircleFillColor(Color.parseColor("#65B1BF"));
        radarDataSet.setHighlightCircleInnerRadius(1f);
        radarDataSet.setHighlightCircleOuterRadius(6f);
        radarDataSet.setHighlightCircleStrokeColor(Color.parseColor("#65B1BF"));
        radarDataSet.setHighlightCircleStrokeAlpha(102);
        radarDataSet.setHighlightCircleStrokeWidth(6f);
        radarDataSet.setDrawHorizontalHighlightIndicator(false);
        radarDataSet.setDrawVerticalHighlightIndicator(false);

        //*****************截至到这里**************************

    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getDescription() {
        return description;
    }
}
