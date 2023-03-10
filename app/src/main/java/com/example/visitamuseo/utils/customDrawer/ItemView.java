package com.example.visitamuseo.utils.customDrawer;

import android.view.ViewGroup;

import com.example.visitamuseo.utils.adapter.MenuAdapter;

public abstract class ItemView <T extends MenuAdapter.ViewHolder> {

    private boolean selected;

    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T ViewHolder);

    public ItemView setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isSelectable() {
        return !selected;
    }

}