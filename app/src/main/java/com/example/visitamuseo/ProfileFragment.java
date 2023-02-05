package com.example.visitamuseo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.visitamuseo.view.activity.NavigationActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {

    private final NavigationActivity navigationActivity;
    //private ProfilePresenter profilePresenter;
    private ImageView profilePicture;
    private TextView textViewname;
    private Button buttonChangePassword;
    private FloatingActionButton buttonChangeProfilePicture;

    public ProfileFragment(NavigationActivity navigationActivity) {
        this.navigationActivity = navigationActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NotNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewname = view.findViewById(R.id.NameText_profile);
        buttonChangePassword = view.findViewById(R.id.changePasswordButton);
        buttonChangeProfilePicture = view.findViewById(R.id.fragment_profile_button_edit_image_profile);
        profilePicture = view.findViewById(R.id.fragment_profile_picture);
        //profilePresenter = new ProfilePresenter(this);


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref",0);
        String pathImage = sharedPreferences.getString("imagePath","");

        if(!pathImage.equals("")){
            Drawable image = Drawable.createFromPath(pathImage);
            profilePicture.setImageDrawable(image);
        }

        buttonChangeProfilePicture.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        buttonChangePassword.setOnClickListener(view1 -> {
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            FragmentActivity fragmentActivity = getActivity();
            if (fragmentActivity != null) {
                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                changePasswordFragment.show(fragmentManager, "CHANGE_PASSWORD_FRAGMENT");
            }
        });

    }

    public void setTextName(String name) {
        textViewname.setText(name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Uri uri = data.getData();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", 0);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        if(uri != null && uri.getPath() != null){
            myEdit.putString("imagePath",uri.getPath());
            myEdit.apply();
            Drawable image = Drawable.createFromPath(uri.getPath());
            profilePicture.setImageDrawable(image);
        }
    }

}