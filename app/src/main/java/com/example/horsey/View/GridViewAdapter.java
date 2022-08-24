package com.example.horsey.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.horsey.BR;
import com.example.horsey.R;
import com.example.horsey.databinding.AnswerItemGvBinding;

public class GridViewAdapter extends BaseAdapter{
    boolean[] isAnswered;
    LayoutInflater layoutInflater;
    AnswerItemGvBinding binding;

    public GridViewAdapter(Context context, boolean[] isAnswered){
        layoutInflater = LayoutInflater.from(context);
        this.isAnswered = isAnswered;
    }

    @Override
    public int getCount() {
        if (isAnswered != null){
            return isAnswered.length;
        } else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.answer_item_gv, null, false);
        view = binding.getRoot();

        binding.setNum(String.valueOf(position+1));
        if (isAnswered[position]){
            binding.numTv.setTextColor(Color.WHITE);
            binding.ansChoseIv.setVisibility(View.VISIBLE);
        }else{
            binding.numTv.setTextColor(Color.GRAY);
            binding.ansChoseIv.setVisibility(View.GONE);
        }


        return view;
    }

}
