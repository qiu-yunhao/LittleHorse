package com.example.horsey.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horsey.R;

import java.util.List;

public abstract class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    public interface AListener{
        void ClickEvent(int pos);
    }

    AListener listener;

    protected abstract void setListener(AListener listener);

    protected List<Integer> list;

    public Adapter(List<Integer> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_grammer_verb_rv_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.imageView.setImageResource(list.get(position));
        holder.imageView.setOnClickListener( v -> {
            System.out.println(position);
            listener.ClickEvent(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.optionImage);
        }
    }
}
