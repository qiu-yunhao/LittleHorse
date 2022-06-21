package com.example.horsey;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChosenMark {


    public void chosen(Button b1,Button b2,Button b3){
        b1.setBackgroundColor(Color.parseColor("#187485"));
        b2.setBackgroundColor(Color.parseColor("#65B1BF"));
        b3.setBackgroundColor(Color.parseColor("#65B1BF"));
    }
    public void notChosen(Button b1,Button b2,Button b3){
        b1.setBackgroundColor(Color.parseColor("#65B1BF"));
        b2.setBackgroundColor(Color.parseColor("#65B1BF"));
        b3.setBackgroundColor(Color.parseColor("#65B1BF"));
    }

}
