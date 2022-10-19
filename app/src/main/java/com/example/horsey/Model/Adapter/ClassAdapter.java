package com.example.horsey.Model.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horsey.R;

import java.util.List;

public class ClassAdapter extends BaseAdapter<String, ClassAdapter.ClassViewHolder>{

    public ClassAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.get()).inflate(R.layout.cell_class,parent,false);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        String s = list.get(position);
        holder.bind(s);
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(position);
            Log.d("position",String.valueOf(position));
        });
        if (position == 0) {
            holder.itemView.performClick();
        }
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textView;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void initView(View v) {
            textView = v.findViewById(R.id.cell_class_textView);
        }

        public void bind(String s) {
            textView.setText(s);
        }
    }
}
