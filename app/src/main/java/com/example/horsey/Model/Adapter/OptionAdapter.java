package com.example.horsey.Model.Adapter;


import androidx.annotation.NonNull;

import com.example.horsey.Model.Adapter.Adapter;

import java.util.List;

public class OptionAdapter extends Adapter {
    public OptionAdapter(List<Integer> optionList) {
        super(optionList);
    }

    @Override
    public void setListener(AListener listener) {
        super.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
