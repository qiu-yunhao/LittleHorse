package com.example.horsey.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.example.horsey.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 这个是把答题框的内容拖拽到题目图片中
 */

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 这个是把答题框的内容拖拽到题目图片中
 */
public class SecondTypeFragment extends BaseFragment {
    private static final String PARAM = "SecondTypeFragment";
    private LinearLayout title, answer;
    private int type,move;
    private HashMap<AppCompatImageView,Integer> map;

    public static SecondTypeFragment newInstance(int type) {
        SecondTypeFragment fragment = new SecondTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        type = bundle.getInt(PARAM, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.touch_fragment, container, false);
        Log.d("Pa",""+v.getWidth());
        map = new HashMap<>();
        initView(v);
        return v;
    }


    /**
     * 这部分的图片加载应该依据type的取值来进行，也就是题号
     * 可以利用一个中转的工具类来实现依据type返回题目图片和答案区图片，以及替换图片
     * 答案要与题目框的相对应请参照语法的综合情景进行理解，例如题目为4张图片，但只有2，3张需要将下方图片拖入，因而答案应设置为{-1,0,1,-1}
     * 即不需要填空的地方用-1表示
     */



    //题目图片
    private List<Integer> getTitleImageViewIDs() {
        List<Integer> list = new ArrayList<>();
        //list.add(R.drawable.user);
        //list.add(R.drawable.s1);
        return list;
    }

    //答题框图片
    private List<Integer> getAnswerImageViewIDs() {
        List<Integer> list = new ArrayList<>();
        //list.add(R.drawable.s1);
        //list.add(R.drawable.user);
        return list;
    }

    private List<Integer> getResult() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(0);
        return list;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView(View v) {
        title = v.findViewById(R.id.type_title);
        answer = v.findViewById(R.id.type_answer);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        lp.setMargins(30, 30, 30, 30);
        for (int i = 0; i < getTitleImageViewIDs().size(); i++) {
            AppCompatImageView imageView = new AppCompatImageView(requireContext());
            imageView.setLayoutParams(lp);
            imageView.setImageResource(getTitleImageViewIDs().get(i));
            imageView.setOnDragListener((view, e) -> {
                /**
                 * 此处通过event.getX(); event.getY(); 获取的x，y是手指（也即是被拖拽view的中心点）在监听view的位置。
                 */
                if (e.getAction() == DragEvent.ACTION_DROP) {
                    if (move == map.getOrDefault(imageView,-1)){

                        //加分,更换图片
                        Toast.makeText(requireContext(),"加分",Toast.LENGTH_SHORT).show();
                        answer.removeView(view);
                    }else{
                        //扣分
                        Toast.makeText(requireContext(),"扣分",Toast.LENGTH_SHORT).show();
                        Log.d(PARAM,"扣分");

                    }
                }
                return true;
            });
            map.put(imageView,getResult().get(i));
            title.addView(imageView);
        }
        for (int i = 0; i < getAnswerImageViewIDs().size(); i++) {
            AppCompatImageView imageView = new AppCompatImageView(requireContext());
            imageView.setImageResource(getAnswerImageViewIDs().get(i));
            imageView.setLayoutParams(lp);
            imageView.setOnLongClickListener(view -> {
                CharSequence s = (CharSequence) view.getTag();
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                ClipData clipData = new ClipData(s, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                move = -1;
                for(int j = 0 ; j < answer.getChildCount() ; j++){
                    if(view == answer.getChildAt(j)){
                        move = j;
                    }
                }
                Log.d("移动序号",String.valueOf(move));
                view.startDragAndDrop(clipData, new View.DragShadowBuilder(view), null, 0);
                return true;
            });
            answer.addView(imageView);
        }


    }

    private boolean check(int x,int y){
        int t = -1;
        for(int i = 0 ; i < getTitleImageViewIDs().size() ; i++){
            View v = title.getChildAt(i);
            Log.d("判断图片地址",v.getLeft() + " " + v.getRight() + " " + (v.getTop() + title.getTop()) + " " + (v.getBottom()+title.getTop()));
            if(x <= v.getRight() && x >= v.getLeft() && y <= v.getBottom() + title.getTop() && y>= v.getTop() + title.getTop()){
                t = i;
            }
        }
        Log.d("终点坐标",String.valueOf(t));
        return getResult().get(t) == move;
    }
}

