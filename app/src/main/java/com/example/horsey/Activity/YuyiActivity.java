package com.example.horsey.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.horsey.Fragment.BaseFragment;
import com.example.horsey.Fragment.YuyiFragment;
import com.example.horsey.R;
import com.example.horsey.Utils.RandomUtils;
import com.example.horsey.Bean.YuyiData;
import com.example.horsey.View.FragmentController;
import com.example.horsey.ViewModel.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YuyiActivity extends BaseContainerActivity{
    @Override
    public void initView() {
        super.initView();
        title.setText("语义游戏");
        bigLevel.setText("当前关卡:语义游戏");
    }

    @Override
    protected void setViewPager(){
        List<List<Integer>> imageList = YuyiData.getImageList();
        List<String[]> nameList = YuyiData.getNameList();
        for (int i = 0; i < 5; i++) {
            List<Integer> images = imageList.get(i);
            String[] names = nameList.get(i);

            int randomIndex = RandomUtils.getRandomNumber(images.size());

            YuyiFragment fragment = new YuyiFragment(images, names[randomIndex], randomIndex);
            fragment.setController(this);
            fragmentList.add(fragment);
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }
}
