package com.example.horsey.Model.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horsey.Model.ChoiceItem;
import com.example.horsey.R;

import java.util.List;

public class VoiceChoiceAdapter extends BaseAdapter<ChoiceItem, VoiceChoiceAdapter.VoiceViewHolder> {

    public VoiceChoiceAdapter(List<ChoiceItem> list, Context context) {
        super(list, context);
    }

    public VoiceChoiceAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.get()).inflate(R.layout.fragment_choice_itemcell,parent,false);
        return new VoiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceViewHolder holder, int position) {
        ChoiceItem item = list.get(position);
        holder.itemView.setOnClickListener( view -> {
            listener.onClick(position);
            if(!holder.isClick) {
                view.setBackgroundColor(Color.BLUE);
                holder.isClick = true;
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
                holder.isClick = false;
            }
        });
        holder.bind(item);
    }

    static class VoiceViewHolder extends RecyclerView.ViewHolder  {
        private ImageView imageView;
        private TextView textView;
        private boolean isClick;
        public VoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
            isClick = false;

        }

        private void initView() {
            imageView = itemView.findViewById(R.id.choice_item_cell_imageView);
            textView = itemView.findViewById(R.id.choice_item_cell_textView);
        }

        public void bind(ChoiceItem item) {
            imageView.setImageResource(item.getId());
            textView.setText(item.getName());
        }
    }
}
