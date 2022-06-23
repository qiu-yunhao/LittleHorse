package com.example.horsey.ViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ThirdViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;
    public ThirdViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragments = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (fragments != null) ? fragments.get(position) : new Fragment();
    }

    @Override
    public int getItemCount() {
        return (fragments != null) ? fragments.size() : 0;
    }

    public void setFragments(List<Fragment> lists){
        this.fragments = lists;
    }
}
