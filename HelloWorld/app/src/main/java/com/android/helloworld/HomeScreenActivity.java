package com.android.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {
    private TextView email;
    private TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Intent intent = getIntent();
        String emailShow = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String passShow = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        email = findViewById(R.id.emailUser);
        email.setText(emailShow);
        password = findViewById(R.id.passUser);
        password.setText(passShow);
    }
}