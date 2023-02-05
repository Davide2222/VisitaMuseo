package com.example.visitamuseo.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.R;
import com.example.visitamuseo.model.SliderViewInterface;
import com.example.visitamuseo.utils.adapter.ArtAdapterForSlider;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ViewExhibitionDetailsActivity extends AppCompatActivity {

    private Exhibition exhibition;
    private Art a = new Art("et","d" ,"rtetyert", "https://www.napolidavivere.it/wp-content/uploads/2014/11/fiera-creattiva-alla-mostra-doltremare.jpg");
    private Art b = new Art("1","d" , "rtetyert", "https://www.napolidavivere.it/wp-content/uploads/2014/11/fiera-creattiva-alla-mostra-doltremare.jpg");
    private Art c = new Art("2t","d" , "rtetyert", "https://www.napolidavivere.it/wp-content/uploads/2014/11/fiera-creattiva-alla-mostra-doltremare.jpg");
    private Art d = new Art("3t","d" ,"rtetyert", "https://www.napolidavivere.it/wp-content/uploads/2014/11/fiera-creattiva-alla-mostra-doltremare.jpg");
    private Art fg = new Art("4t","d" ,"rtetyert", "https://www.ansa.it/webimages/ch_600x/2022/12/14/f26f739d1b515f97dcf50d1d3bb7aace.jpg");

    ArrayList<SliderViewInterface> aws=new ArrayList<>();

    private ImageView imageExhibition;
    private TextView nameExhibition;
    private TextView descriptionExhibition;
    private RecyclerView artRecyclerView;
    private View artContainer;
    private AVLoadingIndicatorView loadingSpinner;


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

        
        /////////
        aws.add(a);aws.add(b);aws.add(c);aws.add(d);aws.add(fg);

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
        ArtAdapterForSlider accommodationCardAdapter = new ArtAdapterForSlider(aws, this);
        artRecyclerView.setAdapter(accommodationCardAdapter);
        artRecyclerView.setVisibility(View.VISIBLE);
    }

}