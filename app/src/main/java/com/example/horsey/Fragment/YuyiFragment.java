package com.example.horsey.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horsey.R;
import com.example.horsey.Table;
import com.example.horsey.Utils.RandomUtils;
import com.example.horsey.ViewModel.YuyiVM.YuyiAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YuyiFragment extends BaseFragment {
    private RecyclerView Rv;
    private YuyiAdapter adapter;

    private List<Integer> imageList = new ArrayList<>();
    private String rightAnswer;
    private int rightAnswerIndex;

    public YuyiFragment(List<Integer> list, String rightAnswer, int rightAnswerIndex){
        this.rightAnswer = rightAnswer;

        List<Integer> randomList = RandomUtils.getRandomList(list.size());
        initImageListAndRightAnswerIndex(randomList, list, rightAnswerIndex);
    }

    // 将list按照randomList的顺序进行打乱，并找出打乱后对应的正确索引
    private void initImageListAndRightAnswerIndex(List<Integer> randomList, List<Integer> list, int rightAnswerIndex){
        for(int i=0; i<randomList.size(); i++){
            int randomIndex = randomList.get(i);
            imageList.add(list.get(randomIndex));
            if(randomIndex == rightAnswerIndex){
                this.rightAnswerIndex = i;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yuyi, container,false);
        initView(view);
        initListener();
        return view;
    }

    public void initView(View view){
        Rv = view.findViewById(R.id.YuyiRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        Rv.setLayoutManager(layoutManager);
        updateRV();
    }

    private void updateRV(){
        adapter = new YuyiAdapter(imageList);
        Rv.setAdapter(adapter);
    }

    private void initListener(){
        adapter.setListener(pos -> {
            if(pos == rightAnswerIndex){
                controller.get().right();
                Toast.makeText(getActivity(), "回答正确", Toast.LENGTH_SHORT).show();
            } else {
                controller.get().error();
                Toast.makeText(getActivity(), "回答错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "请点击" + rightAnswer, Toast.LENGTH_SHORT);
    }
}
