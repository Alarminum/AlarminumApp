package com.alarminum.alarminumapp.base;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

abstract public class BaseViewHolder<E> extends RecyclerView.ViewHolder {
    private E element;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(E element) {
        this.element = element;
    }

    public final E getElement() {
        return element;
    }

}
