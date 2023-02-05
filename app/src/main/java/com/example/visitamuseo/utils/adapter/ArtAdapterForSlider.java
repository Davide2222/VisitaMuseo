package com.example.visitamuseo.utils.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visitamuseo.R;
import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.SliderViewInterface;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ArtAdapterForSlider extends RecyclerView.Adapter<ArtAdapterForSlider.Holder> {

    private ArrayList<SliderViewInterface> arts;
    private LayoutInflater layoutInflater;

    public ArtAdapterForSlider(ArrayList<SliderViewInterface> artsList, Context context) {
        this.arts = artsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_preview_slider, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        SliderAdapter sliderAdapter = new SliderAdapter(arts,position);

        sliderAdapter.setClickListener(v -> showArtDetailsDialog(v,arts.get(holder.getAdapterPosition())));
        holder.imageSlider.setSliderAdapter(sliderAdapter);
        holder.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }

    @Override
    public int getItemCount() {
        return arts.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        final SliderView imageSlider;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageSlider = itemView.findViewById(R.id.cardImageSlider);
        }
    }

    public void showArtDetailsDialog(View view, SliderViewInterface art) {
        AlertDialog.Builder builder = new AlertDialog.Builder(layoutInflater.getContext());
        View customLayout = layoutInflater.inflate(R.layout.card_view_art_details, null);
        ImageView imageView = customLayout.findViewById(R.id.imageArt);
        ImageView remove = customLayout.findViewById(R.id.removeArtCard);
        TextView authorName = customLayout.findViewById(R.id.authorName);
        TextView description= customLayout.findViewById(R.id.description);

        authorName.setText(((Art) art).getAuthor());
        description.setText(((Art) art).getDescription());

        Glide.with(view.getContext())
                .load(art.getUrlImage())
                .centerCrop()
                .into(imageView);

        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.Animation_AppCompat_Dialog;
        dialog.getWindow().setLayout(1000, 1500);

        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        remove.setOnClickListener(view1 -> dialog.dismiss());
    }
}
