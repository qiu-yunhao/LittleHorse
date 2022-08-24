package com.example.horsey;

import android.graphics.Bitmap;

public class EachProblemInfo {
    private int count;
    private Bitmap image;
    private String title;
    private String option1, option2;

    public EachProblemInfo(int count, Bitmap image, String title, String option1, String option2) {
        this.count = count;
        this.image = image;
        this.title = title;
        this.option1 = option1;
        this.option2 = option2;
    }

    public int getCount() {
        return count;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }
}
