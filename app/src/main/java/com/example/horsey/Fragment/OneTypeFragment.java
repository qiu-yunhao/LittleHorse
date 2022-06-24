package com.example.horsey.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horsey.R;
import com.example.horsey.ViewModel.ChosenAdapter;
import com.example.horsey.ViewModel.OptionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OneTypeFragment extends Fragment {
    private RecyclerView optionRV, chosenRV;
    private OptionAdapter optionAdapter;
    private ChosenAdapter chosenAdapter;
    private List<Integer> optionList = new ArrayList<>();
    private List<Integer> chosenList = new ArrayList<>();
    private List<Integer> correctList = new ArrayList<>();
    private TextView textView;

    public OneTypeFragment(List<Integer> correctList) {
        this.correctList = correctList;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_grammer_verb, container,false);
        initView(view);
        return view;
    }

//    这段代码不用管，是把这段代码当成Activity测试用的
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.choose_grammer_verb);
//
//        initView();
//    }

    //
    public void restartList() {
        optionList.clear();
        chosenList.clear();
        correctList.clear();
        //这一坨是测试数据，不用管
//        int imgID0 = R.drawable.dad;
//        int imgID1 = R.drawable.mom;
//        int imgID2 = R.drawable.doll;
//        optionList.clear();
//        optionList.add(imgID0);
//        optionList.add(imgID1);
//        optionList.add(imgID2);
//        correctList.add(imgID0);
//        correctList.add(imgID2);
//        correctList.add(imgID1);

        // 这一段初始化optionList，将correctList中的数据打乱之后放入optionList，作为选项（无必要可以不用删）
        Random random = new Random();
        List<Integer> randomList = new ArrayList<>();
        while (randomList.size() != correctList.size()) {
            int index = random.nextInt(correctList.size());
            System.out.println(index);
            if (randomList.size() == 0) {
                randomList.add(index);
                continue;
            }
            boolean isContain = false;
            for (int i : randomList) {
                if (i == index) isContain = true;
            }
            if (!isContain) randomList.add(index);
        }

        for (int i : randomList) {
            optionList.add(correctList.get(i));
        }
    }

    // 判断选择框内的顺序是否正确（不用改）
    public boolean judgeList() {
        boolean flag = true;
        for (int i = 0; i < correctList.size(); i++) {
            if (!Objects.equals(correctList.get(i), chosenList.get(i))) {
                flag = false;
            }
        }
        return flag;
    }

    // 初始化各种视图
    public void initView(View view) {
        restartList();

        textView = view.findViewById(R.id.restart);

        optionRV = view.findViewById(R.id.options);
        chosenRV = view.findViewById(R.id.chosen);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        optionRV.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        chosenRV.setLayoutManager(layoutManager2);

        updateRV();
    }

    // 不用改，recycleView视图相关
    public void updateRV() {
        optionAdapter = new OptionAdapter(optionList);
        optionRV.setAdapter(optionAdapter);

        chosenAdapter = new ChosenAdapter(chosenList);
        chosenRV.setAdapter(chosenAdapter);

        Listener();
    }

    public void Listener() {
        textView.setOnClickListener(v -> {
            restartList();
            updateRV();
        });

        chosenAdapter.setListener(pos -> {

        });

        // todo: 需要改！！，把错误、正确那两句改掉即可
        optionAdapter.setListener(pos -> {
            int resource = optionList.get(pos);
            chosenList.add(resource);
            optionList.remove(pos);
            if (chosenList.size() == correctList.size()) {
                if (judgeList()) Toast.makeText(getActivity(), "正确！", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getActivity(), "错误！", Toast.LENGTH_SHORT).show();
            }
            updateRV();
        });
    }
}
