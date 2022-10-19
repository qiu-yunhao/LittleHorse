package com.example.horsey.Model.Adapter;



import com.example.horsey.Model.Adapter.Adapter;

import java.util.List;

public class ChosenAdapter extends Adapter {

    @Override
    public void setListener(AListener listener) {
        super.listener = listener;
    }

    public ChosenAdapter(List<Integer> list) {
        super(list);
    }
}
