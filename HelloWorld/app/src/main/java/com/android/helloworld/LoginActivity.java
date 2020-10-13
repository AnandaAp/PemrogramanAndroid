package com.android.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailLogin;
    private EditText passLogin;
    private Button btnLogin;
    private InputMethodManager imm; //variable untuk menyembunyikan keyboard

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Proses program
        init();
    }
    //Method untuk menginisialisasi variable
    private void init(){
        emailLogin = findViewById(R.id.emailLogin);
        passLogin = findViewById(R.id.passLogin);
        btnLogin = findViewById(R.id.btnLogin);
        hideTitleBar();
        dismissKeyboard(emailLogin,passLogin);
        btnLogin.setOnClickListener(v -> check());
    }
    //Method untuk input validasi
    private  void check(){
        if(emailLogin.getText().toString().equals("")
                || passLogin.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Ops Sepertinya Kamu Belum " +
                    "Memasukan Input",Toast.LENGTH_SHORT).show();
        }
        else if(emailLogin.getText().toString().equals("admin")
                && passLogin.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(),"Login Sukses",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Username atau Password Anda tidak benar!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //Method untuk menyembunyikan soft keyboard ketika mengklik area lain diluar EditText
    private void dismissKeyboard(EditText idTextInput1,EditText idTextInput2){
        idTextInput1.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        idTextInput2.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }
    //Method untuk menyembunyikan ActionBar
    private void hideTitleBar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}