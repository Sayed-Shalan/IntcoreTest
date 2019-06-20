package com.sayed.intcoretest.ui.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class DataBindingViewHolder<Binding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public Binding binding;

    public DataBindingViewHolder(ViewGroup parent, int layout) {
        this(LayoutInflater.from(parent.getContext()), parent, layout);
    }

    public DataBindingViewHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
        this(DataBindingUtil.inflate(inflater, layout, parent, false));
    }


    private DataBindingViewHolder(Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
        initListener();
    }

    protected void initListener() {
    }

    protected Context getContext() {
        return binding.getRoot().getContext();
    }
}
