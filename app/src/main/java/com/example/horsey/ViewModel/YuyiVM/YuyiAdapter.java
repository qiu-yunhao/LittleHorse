package com.example.horsey.ViewModel.YuyiVM;



import com.example.horsey.Model.Adapter.Adapter;

import java.util.List;

public class YuyiAdapter extends Adapter {
    public YuyiAdapter(List<Integer> list) {
        super(list);
    }

    @Override
    public void setListener(AListener listener) {
        super.listener = listener;
    }
}
