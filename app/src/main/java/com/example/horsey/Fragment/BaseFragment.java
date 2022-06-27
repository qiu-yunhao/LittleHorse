package com.example.horsey.Fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.horsey.View.FragmentController;

import java.lang.ref.WeakReference;

public abstract class BaseFragment extends Fragment {

    protected WeakReference<FragmentController> controller;

    public abstract void initView(View v);

    public void setController(FragmentController controller){
        this.controller = new WeakReference<>(controller);
    }

}
