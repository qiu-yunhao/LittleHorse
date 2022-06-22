package com.example.horsey.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends AndroidViewModel {
    public MutableLiveData<Integer> grades;


    public GameViewModel(@NonNull Application application) {
        super(application);
        grades = new MutableLiveData<>(0);
    }



}
