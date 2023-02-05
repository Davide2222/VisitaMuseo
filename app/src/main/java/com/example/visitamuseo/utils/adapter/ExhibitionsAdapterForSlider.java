package com.example.visitamuseo.utils.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitamuseo.R;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.model.SliderViewInterface;
import com.example.visitamuseo.view.activity.ViewExhibitionDetailsActivity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ExhibitionsAdapterForSlider extends RecyclerView.Adapter<ExhibitionsAdapterForSlider.Holder> {

    private ArrayList<SliderViewInterface> exhibitions;
    private LayoutInflater layoutInflater;

    public ExhibitionsAdapterForSlider(ArrayList<SliderViewInterface> exhibitionList, Context context) {
        this.exhibitions = exhibitionList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExhibitionsAdapterForSlider.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_preview_slider, null);
        return new ExhibitionsAdapterForSlider.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SliderAdapter sliderAdapter = new SliderAdapter(exhibitions,position);
        holder.imageSlider.setSliderAdapter(sliderAdapter);
        holder.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderAdapter.setClickListener(v -> {
            Intent intent = new Intent(layoutInflater.getContext(), ViewExhibitionDetailsActivity.class);
            intent.putExtra("exhibition",((Exhibition) exhibitions.get(holder.getAdapterPosition())));
            layoutInflater.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exhibitions.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final SliderView imageSlider;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageSlider = itemView.findViewById(R.id.cardImageSlider);
        }
    }
}
