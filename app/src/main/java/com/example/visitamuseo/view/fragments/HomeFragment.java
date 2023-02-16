package com.example.visitamuseo.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitamuseo.R;
import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.model.SliderViewInterface;
import com.example.visitamuseo.utils.adapter.ExhibitionsAdapterForSlider;
import com.example.visitamuseo.utils.internalDatabase.DbManager;
import com.example.visitamuseo.view.activity.NavigationActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HomeFragment extends Fragment {

    private final NavigationActivity navigationActivity;
    private KenBurnsView background;
    private AVLoadingIndicatorView loadingSpinner;
    private RecyclerView exhibitionsRecyclerView;
    private final static int[] backgrounds = {
            R.drawable.background_home_one,
            R.drawable.background_home_two,
            R.drawable.background_home_three,
            R.drawable.background_home_four
    };

    public HomeFragment(NavigationActivity navigationActivity) {
        this.navigationActivity = navigationActivity;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        background = view.findViewById(R.id.fragment_home_background);
        exhibitionsRecyclerView = view.findViewById(R.id.Exhibitions_recycler_view);
        exhibitionsRecyclerView.setLayoutManager(new CardSliderLayoutManager(200, 600, 0));
        new CardSnapHelper().attachToRecyclerView(exhibitionsRecyclerView);
        loadingSpinner = view.findViewById(R.id.fragment_home_spinner);
        showExhibitions();
    }

    @Override
    public void onResume() {
        super.onResume();
        background.resume();
        background.setImageResource(backgrounds[new Random().nextInt(backgrounds.length)]);
    }

    @Override
    public void onStop() {
        super.onStop();
        background.pause();
    }

    public NavigationActivity getNavigationActivity() {
        return navigationActivity;
    }

    public void setLoadingSpinnerVisibility(boolean isVisible) {
        if(isVisible) {
            loadingSpinner.setVisibility(View.VISIBLE);
        } else {
            loadingSpinner.setVisibility(View.GONE);
        }
    }

    private void showExhibitions() {
        DbManager database = DbManager.getDbInstance(requireActivity().getApplicationContext());
        ArrayList<SliderViewInterface> exhibitions=new ArrayList<>();

        List<Exhibition> tmpEx=database.exhibitionsDao().getAll();
        exhibitions.addAll(tmpEx);

        ExhibitionsAdapterForSlider cardAdapter = new ExhibitionsAdapterForSlider(exhibitions, getActivity());
        exhibitionsRecyclerView.setAdapter(cardAdapter);
        setLoadingSpinnerVisibility(false);
        exhibitionsRecyclerView.setVisibility(View.VISIBLE);
    }

}
