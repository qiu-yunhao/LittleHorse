package com.example.horsey.Bean;

import com.example.horsey.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class YuyiData {
    private static final List<List<Integer>> imageList = new ArrayList<>();

    private static final List<String[]> nameList = new ArrayList<>();
    private static final List<Integer> list1 = Arrays.asList(
            R.drawable.clock,
            R.drawable.pen,
            R.drawable.apple
    );
    private static final String[] name1 = {"闹钟", "笔", "苹果"};

    private static final List<Integer> list2 = Arrays.asList(
            R.drawable.red,
            R.drawable.yellow,
            R.drawable.blue
    );
    private static final String[] name2 = {"红色", "黄色", "蓝色"};

    private static final List<Integer> list3 = Arrays.asList(
            R.drawable.drink,
            R.drawable.swim,
            R.drawable.smell
    );
    private static final String[] name3 = {"喝", "游泳", "闻"};

    private static final List<Integer> list4 = Arrays.asList(
            R.drawable.bowel,
            R.drawable.chopstick
    );
    private static final String[] name4 = {"碗", "筷子"};

    private static final List<Integer> list5 = Arrays.asList(
            R.drawable.apple_on_table,
            R.drawable.apple_under_table
    );
    private static final String[] name5 = {"苹果在桌上", "苹果在桌下"};

    public static List<List<Integer>> getImageList(){
        imageList.add(list1);
        imageList.add(list2);
        imageList.add(list3);
        imageList.add(list4);
        imageList.add(list5);
        return imageList;
    }

    public static List<String[]> getNameList(){
        nameList.add(name1);
        nameList.add(name2);
        nameList.add(name3);
        nameList.add(name4);
        nameList.add(name5);
        return nameList;
    }

}
