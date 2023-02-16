package com.example.visitamuseo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitamuseo.CommunicationThreads.CommunicationThreadLogin;
import com.example.visitamuseo.CommunicationThreads.CommunicationThreadReceiveData;
import com.example.visitamuseo.R;
import com.example.visitamuseo.utils.internalDatabase.DbManager;
import com.example.visitamuseo.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends FullscreenActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername= findViewById(R.id.UsernameEditText_login);
        editTextPassword= findViewById(R.id.PasswordEditText_login);
        buttonLogin= findViewById(R.id.login_button);
        textViewSignUp= findViewById(R.id.SignUpTextView);

        buttonLogin.setOnClickListener(view -> {


            executor.execute(() -> {
                    CommunicationThreadReceiveData communicationThreadReceiveData = new CommunicationThreadReceiveData(this,"Single");
                    communicationThreadReceiveData.getData();
                    handler.post(() -> {
                        startActivity(new Intent(this, NavigationActivity.class));

                    });
                });

          /*  String username= editTextUsername.getText().toString();
            String password= editTextPassword.getText().toString();
            if(checkField(username) && checkField(password)){*/
               /*executor.execute(() -> {
                    CommunicationThreadLogin communicationThreadLogin = new CommunicationThreadLogin(username+","+password);
                    User userLogged= communicationThreadLogin.checkUser();
                    handler.post(() -> {
                        if (userLogged == null)
                            Toasty.error(this,"Utente non trovato!", Toast.LENGTH_SHORT, true).show();
                        else {
                           *//* Toasty.info(this, "Utente trovato", Toast.LENGTH_SHORT, true).show();
                            System.out.println(userLogged);
                            //fare login*//*
                            DbManager database = DbManager.getDbInstance(getApplicationContext());
                            database.userDao().insertUser(userLogged);
                            //startActivity(new Intent(this, ViewExhibitionListActivity.class));
                        }
                    });
                });
            }*/
        });

        textViewSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private boolean checkField(String text) {
        if (text.isEmpty()) {
            Toasty.warning(this, "Attenzione! Compilare entrambi i campi", Toasty.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

}