package com.android.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailLogin;
    private EditText passLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnLogin.setOnClickListener(v -> check());
    }
    private void init(){
        emailLogin = findViewById(R.id.emailLogin);
        passLogin = findViewById(R.id.passLogin);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private  void check(){
        if(emailLogin.getText().toString().equals("")
                || passLogin.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Ops Sepertinya Kamu Belum " +
                    "Memasukan Input",Toast.LENGTH_SHORT).show();
        }
        if(emailLogin.getText().toString().equals("admin")
                && passLogin.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(),"Login Sukses",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Username atau Password Anda tidak benar!",Toast.LENGTH_SHORT).show();
        }
    }
}