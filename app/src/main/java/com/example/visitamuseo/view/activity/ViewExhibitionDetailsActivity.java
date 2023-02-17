package com.example.visitamuseo.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.R;
import com.example.visitamuseo.model.SliderViewInterface;
import com.example.visitamuseo.utils.adapter.ArtAdapterForSlider;
import com.example.visitamuseo.utils.internalDatabase.DbManager;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class ViewExhibitionDetailsActivity extends FullscreenActivity {

    private Exhibition exhibition;
    ArrayList<SliderViewInterface> arts=new ArrayList<>();

    private ImageView imageExhibition;
    private TextView nameExhibition;
    private TextView descriptionExhibition;
    private RecyclerView artRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exhibition_details);

        Toolbar toolbar = findViewById(R.id.toolbarExhibition);
        imageExhibition = findViewById(R.id.ImageExhibition);
        nameExhibition = findViewById(R.id.ExhibitionNameTitle);
        descriptionExhibition =findViewById(R.id.description);
        artRecyclerView = findViewById(R.id.SliderArtRecyclerView);

        if (getIntent().hasExtra("exhibition")) {
            exhibition=(Exhibition) getIntent().getSerializableExtra("exhibition");
        }

        DbManager database = DbManager.getDbInstance(this);
        List<Art> tmpArts=database.artDao().getArts(exhibition.getName());
        arts.addAll(tmpArts);

        setSupportActionBar(toolbar);

        ImageView back=toolbar.findViewById(R.id.backButtonExhibitionActivity);
        back.setOnClickListener(view -> {
            onBackPressed();
        });

        Glide.with(this)
                .load(exhibition.getUrlImage())
                .centerCrop()
                .into(imageExhibition);

        nameExhibition.setText(exhibition.getName());
        descriptionExhibition.setText(exhibition.getDescription());
        artRecyclerView.setLayoutManager(new CardSliderLayoutManager(200, 600, 0));
        showArts();
    }

    private void showArts() {
        ArtAdapterForSlider accommodationCardAdapter = new ArtAdapterForSlider(arts, this);
        artRecyclerView.setAdapter(accommodationCardAdapter);
        artRecyclerView.setVisibility(View.VISIBLE);
    }

}