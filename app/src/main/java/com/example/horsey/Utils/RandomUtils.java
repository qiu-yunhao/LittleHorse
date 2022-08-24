package com.example.horsey.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils{
    private static Random random = new Random();

    public static int getRandomNumber(int size){
        return random.nextInt(size);
    }

    public static List<Integer> getRandomList(int listSize){
        List<Integer> randomList = new ArrayList<>();
        while (randomList.size() != listSize) {
            int index = RandomUtils.getRandomNumber(listSize);
            if (randomList.size() == 0) {
                randomList.add(index);
                continue;
            }
            boolean isContain = false;
            for (int i : randomList) {
                if (i == index) isContain = true;
            }
            if (!isContain) randomList.add(index);
        }
        return randomList;
    }
}
