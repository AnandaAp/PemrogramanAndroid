package com.android.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        hideTitleBar();
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);
            finish(); }, 3000);
    }

    //Method untuk menyembunyikan ActionBar
    private void hideTitleBar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}


