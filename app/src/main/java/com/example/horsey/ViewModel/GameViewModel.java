package com.example.horsey.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> grades;
    private int g;


    public GameViewModel(@NonNull Application application) {
        super(application);
        grades = new MutableLiveData<>(0);
        g = 0;
    }

    public MutableLiveData<Integer> getGrades() {
        return grades;
    }

    public void addGrades(int g) {
        this.g += g;
        grades.postValue(this.g);
    }

    public void subGrades() {
        if (g > 0)
            g--;
        grades.postValue(g);
    }


}
