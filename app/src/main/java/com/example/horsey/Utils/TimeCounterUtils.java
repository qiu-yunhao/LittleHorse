package com.example.horsey.Utils;

import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeCounterUtils {
    private static ScheduledExecutorService scheduledExecutor5 = Executors.newSingleThreadScheduledExecutor();

    public static void startTimeCounter(TextView textView) {
        final long[] count = {0};
        Runnable runnable5 = new Runnable() {
            @Override
            public void run() {
                count[0]++;
                textView.setText(formatTime2(count[0]));
            }
        };
        scheduledExecutor5.scheduleAtFixedRate(runnable5, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private static String formatTime2(long seconds) {
        return String.format(" %02d:%02d", seconds / 60, seconds % 60);
    }
}
