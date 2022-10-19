package com.example.horsey.Model.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.horsey.Fragment.BaseFragment;

import java.util.List;

public class ThirdViewPagerAdapter extends FragmentStateAdapter {
    private final List<BaseFragment> fragments;
    public ThirdViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<BaseFragment> fragmentList) {
        super(fragmentActivity);
        this.fragments = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
