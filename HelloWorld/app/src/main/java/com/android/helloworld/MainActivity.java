package com.android.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText emailLogin;
    private EditText passLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }
    private void init(){
        emailLogin = findViewById(R.id.emailLogin);
        passLogin = findViewById(R.id.passLogin);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private  void check(){
        if(emailLogin.getText().toString().equals("admin")
                && passLogin.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(),"Login Sukses",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Login Gagal",Toast.LENGTH_SHORT).show();
        }
    }
}