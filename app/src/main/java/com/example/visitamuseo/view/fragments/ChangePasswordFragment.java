package com.example.visitamuseo.view.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.visitamuseo.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;


public class ChangePasswordFragment extends DialogFragment {

    private EditText textInputOldPassword;
    private EditText textInputNewPassword;
    private Button buttonSubmit;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputOldPassword = view.findViewById(R.id.fragment_change_password_text_input_old_password);
        textInputNewPassword = view.findViewById(R.id.fragment_change_password_text_input_new_password);
        buttonSubmit = view.findViewById(R.id.fragment_change_password_button_submit);
        initializeUserInterface();

        buttonSubmit.setOnClickListener(view1 -> {
            Toasty.info(requireActivity(), "Opzione non ancora disponibile!", Toast.LENGTH_SHORT, true).show();
        });

    }

    private void initializeUserInterface() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if(getDialog() != null) {
            Window window = getDialog().getWindow();
            if(window != null) {
                window.getAttributes().windowAnimations = R.style.Animation_AppCompat_Dialog;
            }
        }
    }

    public boolean validate2Password() {
        String oldPassword = textInputOldPassword.getText().toString();
        String newPassword = textInputNewPassword.getText().toString();
        if (oldPassword.equals("")) {
            textInputOldPassword.setError("La password non può essere vuota");
            return false;
        }
        if ("".equals(newPassword)) {
            textInputOldPassword.setError("La password non può essere vuota");
            return false;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{3,10}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oldPassword);
        Matcher matcher1= pattern.matcher(newPassword);

        if (!matcher.matches())
            textInputOldPassword.setError("Caratteristiche della password:\n" +
                    "3-10 caratteri\n" +
                    "1 numero\n" +
                    "1 carattere maiuscolo\n" +
                    "1 carattere minuscolo\n");

        if (!matcher1.matches())
            textInputOldPassword.setError("Caratteristiche della password:\n" +
                    "3-10 caratteri\n" +
                    "1 numero\n" +
                    "1 carattere maiuscolo\n" +
                    "1 carattere minuscolo\n");
      return  matcher.matches() && matcher1.matches();
    }

}

















