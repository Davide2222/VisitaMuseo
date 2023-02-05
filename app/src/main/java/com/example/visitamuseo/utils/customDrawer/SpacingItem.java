package com.example.visitamuseo.utils.customDrawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitamuseo.utils.adapter.MenuAdapter;

import org.jetbrains.annotations.NotNull;


public class SpacingItem extends ItemView<SpacingItem.ViewHolder> {

    private int spaceValueInDp;

    public SpacingItem(int spaceDp) {
        this.spaceValueInDp = spaceDp;
    }

    @Override
    public ViewHolder createViewHolder(@NotNull ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        View view = new View(context);
        int height = (int) (context.getResources().getDisplayMetrics().density * spaceValueInDp);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder ViewHolder) {}

    @Override
    public boolean isSelectable() {
        return false;
    }

    static class ViewHolder extends MenuAdapter.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

    }

}