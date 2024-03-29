package com.example.horsey.Bean;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.horsey.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Help {
    private static final int[][] title = {
            {R.drawable.ca4, R.drawable.ca2, R.drawable.ca1},
            {R.drawable.ca5, R.drawable.ca6, R.drawable.ca7},
            {R.drawable.ca8, R.drawable.ca9, R.drawable.ca10},
            {R.drawable.cb1, R.drawable.cb2, R.drawable.cb3},
            {R.drawable.cb14, R.drawable.cb15},
            {R.drawable.cb9,R.drawable.cb16,R.drawable.cb12},
            {R.drawable.cc5, R.drawable.cc4, R.drawable.cc4, R.drawable.cc3},
            {R.drawable.cc7,R.drawable.cc8},
            {R.drawable.cc6,R.drawable.cc9}

    };
    private static final int[][] answer = {
            {-1, -1, -1},
            {-1, -1, -1},
            {-1, -1, -1},
            {R.drawable.cb4, R.drawable.cb6, R.drawable.cb5},
            {-1,-1},
            {-1,-1,-1},
            {R.drawable.cc1, R.drawable.cc2},
            {-1,-1},
            {-1,-1}
    };

    private static final int[][] result = {
            {-1, -1, -1},
            {},
            {},
            {2, 0, 1},
            {0,1},
            {2,1,0},
            {-1, 1, 0, -1},
            {0,1},
            {0,1}
    };

    private static final int[][] change = {
            {},
            {},
            {},
            {R.drawable.cb_c2, R.drawable.cb_c3, R.drawable.cb_c1},
            {R.drawable.cb7,R.drawable.cb8},
            {R.drawable.cb13,R.drawable.cb6,R.drawable.cb10},
            {-1, R.drawable.cc_c1, R.drawable.cb1, -1},
            {R.drawable.cc7,R.drawable.cc8},
            {R.drawable.cc6,R.drawable.cc9}

    };

    private static final int[] music = {
            R.raw.ca_1,
            0,
            0,
            R.raw.cb_1,
            0,
            0,
            R.raw.cc_1,
            0,
            0
    };
    private static List<DataBean> data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Help() {
        if (data == null) {
            data = new ArrayList<>();
            int n = title.length;
            for (int i = 0; i < n; i++) {
                List<Integer> t = Arrays.stream(title[i]).boxed().collect(Collectors.toList());
                List<Integer> a = Arrays.stream(answer[i]).boxed().collect(Collectors.toList());
                List<Integer> r = Arrays.stream(result[i]).boxed().collect(Collectors.toList());
                String m = String.valueOf(music[i]);
                List<Integer> c = Arrays.stream(change[i]).boxed().collect(Collectors.toList());
                DataBean dataBean = new DataBean(t, a, r, m, c);
                data.add(dataBean);
            }
        }
    }

    public DataBean getData(int index) {
        return data.get(index);
    }
}
