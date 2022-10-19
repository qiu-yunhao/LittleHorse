package com.example.horsey.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> grades;
    private int g;
    private static MutableLiveData<Long> time;


    public GameViewModel(@NonNull Application application) {
        super(application);
        grades = new MutableLiveData<>(0);
        g = 0;
        time = new MutableLiveData<>(0L);
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

    public static MutableLiveData<Long> getTime() {
        return time;
    }

    public static class TimeThread extends Thread {
        private long t = 0;

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(1000);
                    t += 1000;
                    time.postValue(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
