package com.example.horsey.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.horsey.R;

public class ThirdFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_voice,container,false);
        initView(v);
        return v;
    }

    @Override
    public void initView(View v) {

    }
}
