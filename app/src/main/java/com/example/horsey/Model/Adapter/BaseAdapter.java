package com.example.horsey.Model.Adapter;

import android.content.Context;
import android.widget.ExpandableListView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public abstract class BaseAdapter<T,S extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<S> {
    protected List<T> list;
    protected final WeakReference<Context> context;
    protected ClickListener listener;

    public BaseAdapter(List<T> list,Context context) {
        this.list = list;
        this.context = new WeakReference<>(context);
    }

    public BaseAdapter(Context context) {
        this.context = new WeakReference<>(context);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void onClick(int pos);
    }

}
