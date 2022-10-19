package com.example.horsey.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.horsey.Activity.PassActivityNumber;
import com.example.horsey.Model.ChoiceItem;
import com.example.horsey.R;
import com.example.horsey.Model.Adapter.BaseAdapter;
import com.example.horsey.Model.Adapter.VoiceChoiceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoiceChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//TODO 构建语音游戏相关选择页面的Fragment
public class VoiceChoiceFragment extends BaseFragment implements BaseAdapter.ClickListener {

    private static final String ARG_PARAM1 = "param1";
    private int mParam1; //记录当前的页数
    private int count = 0; //记录当前的项目数
    private PassActivityNumber pass;

    private VoiceChoiceAdapter adapter;

    public VoiceChoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment VoiceChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoiceChoiceFragment newInstance(int param1) {
        VoiceChoiceFragment fragment = new VoiceChoiceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_voice_choice, container, false);
        initView(v);
        return v;
    }

    @Override
    public void initView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.choice_recyclerView);
        adapter = new VoiceChoiceAdapter(getContext());
        List<ChoiceItem> temp = getItems();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), temp.size(), RecyclerView.VERTICAL, false));
        adapter.setList(temp);
        recyclerView.setAdapter(adapter);
    }

    private List<ChoiceItem> getItems() {
        List<ChoiceItem> ans = new ArrayList<>();
        switch (mParam1) {
            case 0:
                ans.add(new ChoiceItem(R.drawable.choose_didi,"人物"));
                ans.add(new ChoiceItem(R.drawable.choose_yellow,"颜色"));
                ans.add(new ChoiceItem(R.drawable.choose_muma,"玩具"));
                ans.add(new ChoiceItem(R.drawable.choose_banana,"食物"));
                break;
            case 1:
                ans.add(new ChoiceItem(R.drawable.choose_furniture,"家具"));
                ans.add(new ChoiceItem(R.drawable.choose_supplies,"生活用品"));
                ans.add(new ChoiceItem(R.drawable.choose_kitchen,"厨房用品"));
                ans.add(new ChoiceItem(R.drawable.choose_cleantools,"清洁用具"));
                break;
            case 2:
                ans.add(new ChoiceItem(R.drawable.choose_bus,"交通工具"));
                ans.add(new ChoiceItem(R.drawable.choose_bluebook,"教学工具"));
                ans.add(new ChoiceItem(R.drawable.choose_ball,"球类"));
                ans.add(new ChoiceItem(R.drawable.choose_nature,"自然景观"));
                break;
            case 3:
                ans.add(new ChoiceItem(R.drawable.choose_plant,"植物"));
                ans.add(new ChoiceItem(R.drawable.choose_animal,"动物"));
                ans.add(new ChoiceItem(R.drawable.choose_behaviour,"行为动作"));
                ans.add(new ChoiceItem(R.drawable.choose_up,"位置"));
                break;

        }
        return ans;
    }

    @Override
    public void onClick(int pos) {
        pass.passNumber(mParam1 * 4 + pos);
    }

    public void setPass(PassActivityNumber number) {
        this.pass = number;
    }
}