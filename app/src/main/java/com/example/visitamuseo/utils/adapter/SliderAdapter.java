package com.example.visitamuseo.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.visitamuseo.R;
import com.example.visitamuseo.model.SliderViewInterface;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private ArrayList<SliderViewInterface> sliderItems;
    private int truePosition;
    private View.OnTouchListener touchListener;
    private View.OnClickListener clickListener;

    public SliderAdapter(ArrayList<SliderViewInterface> sliderItems, int positionv) {
        this.sliderItems = sliderItems;
        this.truePosition = positionv;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(@NotNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_image_item, null);
        SliderViewHolder sliderViewHolder = new SliderViewHolder(view);
        if (touchListener != null) {
            sliderViewHolder.itemView.setOnTouchListener(touchListener);
        }
        if (clickListener != null) {
            sliderViewHolder.itemView.setOnClickListener(clickListener);
        }
        return sliderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull SliderViewHolder viewHolder, final int position) {
        SliderViewInterface sliderItem = sliderItems.get(truePosition);
        viewHolder.loadingSpinner.setVisibility(View.GONE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getUrlImage())
                .fitCenter()
                .into(viewHolder.image);

    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    public void setTouchListener(View.OnTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    static class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        private final ImageView image;
        private AVLoadingIndicatorView loadingSpinner;
        View itemView;

        private SliderViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.slider_image_item_image_view);
            loadingSpinner = itemView.findViewById(R.id.slider_image_item_loading_spinner);
            this.itemView = itemView;
        }
    }
}
