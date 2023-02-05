package com.example.visitamuseo.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visitamuseo.utils.internalDatabase.DbManager;

public class ToDirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        Runnable runnable = () -> {
            if (IsAlreadyLogged()) {
                startActivity(new Intent(ToDirectActivity.this, NavigationActivity.class));
            } else {
                startActivity(new Intent(ToDirectActivity.this, LoginActivity.class));
            }
            finish();
        };
        handler.postDelayed(runnable, 1000);
    }

    private boolean IsAlreadyLogged(){
        DbManager database= DbManager.getDbInstance(getApplicationContext());
        int numOfUser=database.userDao().numberLoggedUser();
        return numOfUser != 0;
    }
}