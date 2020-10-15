package com.android.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView welcome;
    private Animation fadeOut,fadeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        hideTitleBar();
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);
            finish(); }, 5000);

        welcome = findViewById(R.id.welcome);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        fadeOut.setRepeatCount(Animation.INFINITE);
//        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        welcome.startAnimation(fadeOut);

    }

    //Method untuk menyembunyikan ActionBar
    private void hideTitleBar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}


