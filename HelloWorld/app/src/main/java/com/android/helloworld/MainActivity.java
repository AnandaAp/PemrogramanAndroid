package com.android.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private EditText emailLogin;
    private EditText passLogin;
    private Button btnLogin;
    public static final String EXTRA_MESSAGE1 = "com.android.helloworld.MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "com.android.helloworld.MESSAGE2";

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
        if(emailLogin.getText().toString().equals("")
                || passLogin.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Ops Sepertinya Kamu Belum " +
                    "Memasukan Input",Toast.LENGTH_SHORT).show();
        }
        if(emailLogin.getText().toString().equals("admin")
                && passLogin.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(),"Login Sukses",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeScreenActivity.class);
            String email = emailLogin.getText().toString();
            String pass = passLogin.getText().toString();
            intent.putExtra(EXTRA_MESSAGE1, email);
            intent.putExtra(EXTRA_MESSAGE2,pass);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Username atau Password Anda tidak benar!",Toast.LENGTH_SHORT).show();
        }
    }
}