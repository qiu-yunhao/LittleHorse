package com.example.horsey.Fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.horsey.Bean.Help;
import com.example.horsey.R;

import java.util.HashMap;
import java.util.List;

public class ThirdTypeFragment extends BaseFragment {
    private static final String TAG = "ThirdTypeFragment";
    private LinearLayout input;
    private LinearLayout output;
    private ConstraintLayout layout;
    private int type, move;
    private HashMap<AppCompatImageView, Integer> map;
    private HashMap<AppCompatImageView, Integer> map_change;
    private View last;
    private int bg_output = 0,bg_layout = 0;
    private STATUS status1 = STATUS.vertical, status2 = STATUS.vertical;

    public static ThirdTypeFragment newInstance(int type) {
        ThirdTypeFragment fragment = new ThirdTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        type = bundle.getInt(TAG, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_third_type,container,false);
        map = new HashMap<>();
        map_change = new HashMap<>();
        initView(v);
        return v;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView(View v) {
        input = v.findViewById(R.id.third_type_input);
        output = v.findViewById(R.id.third_type_output);
        layout = v.findViewById(R.id.third_type_main);
        if(bg_output != 0) {
            output.setBackground(requireContext().getDrawable(bg_output));
        }
        if(bg_layout != 0) {
            layout.setBackground(requireContext().getDrawable(bg_layout));
        }
        if (status1 == STATUS.vertical) {
            input.setOrientation(LinearLayout.VERTICAL);
        } else {
            input.setOrientation(LinearLayout.HORIZONTAL);
        }

        if(status2 == STATUS.vertical) {
            output.setOrientation(LinearLayout.VERTICAL);
        }else {
            output.setOrientation(LinearLayout.HORIZONTAL);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        lp.setMargins(10, 30, 10, 30);
        for (int i = 0; i < getAnswerImageViewIDs().size(); i++) {
            AppCompatImageView imageView = new AppCompatImageView(requireContext());
            imageView.setLayoutParams(lp);
            //imageView.setImageResource(getAnswerImageViewIDs().get(i));
            imageView.setOnDragListener((view, e) -> {
                //此处通过event.getX(); event.getY(); 获取的x，y是手指（也即是被拖拽view的中心点）在监听view的位置。
                if (e.getAction() == DragEvent.ACTION_DROP) {
                    if (move == map.getOrDefault(imageView, -1)) {
                        //加分,更换图片
                        Toast.makeText(requireContext(),"正确",Toast.LENGTH_SHORT).show();
                        controller.get().right();
                        imageView.setImageResource(map_change.get(imageView));
                        last.setVisibility(View.GONE);
                    } else {
                        //扣分
                        Toast.makeText(requireContext(),"错误",Toast.LENGTH_SHORT).show();
                        controller.get().error();
                    }
                }
                return true;
            });
            map.put(imageView, getResult().get(i));
            map_change.put(imageView, getChange().get(i));
            output.addView(imageView);
        }
        for (int i = 0; i < getTitleImageViewIDs().size(); i++) {
            AppCompatImageView imageView = new AppCompatImageView(requireContext());
            imageView.setImageResource(getTitleImageViewIDs().get(i));
            imageView.setLayoutParams(lp);
            imageView.setOnLongClickListener(view -> {
                CharSequence s = (CharSequence) view.getTag();
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                ClipData clipData = new ClipData(s, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                move = -1;
                for (int j = 0; j < input.getChildCount(); j++) {
                    if (view == input.getChildAt(j)) {
                        last = view;
                        move = j;
                    }
                }
                Log.d("移动序号", String.valueOf(move));
                view.startDragAndDrop(clipData, new View.DragShadowBuilder(view), null, 0);
                return true;
            });
            input.addView(imageView);
        }

    }

    //题目图片
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Integer> getTitleImageViewIDs() {
        Help help = new Help();
        return help.getData(type).getTitle();
    }

    //答题框图片
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Integer> getAnswerImageViewIDs() {
        Help help = new Help();
        return help.getData(type).getAnswer();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Integer> getResult() {
        Help help = new Help();
        return help.getData(type).getResult();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Integer> getChange() {
        Help help = new Help();
        return help.getData(type).getChange();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setBackGround(int id) {
        this.bg_layout = id;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setOutPutBackGround(int id) {
        this.bg_output = id;
    }

    public void changeInputLayoutType(STATUS status) {
        status1 = status;
    }

    public void changeOutputLayoutType(STATUS status) {
        status2 = status;
    }

    public enum STATUS{
        horizontal,vertical
    }

}
