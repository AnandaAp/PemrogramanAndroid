package com.android.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import static android.content.ContentValues.TAG;


public class fragment_sign_up extends Fragment {
    private InputMethodManager imm; //variable untuk menyembunyikan keyboard
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        EditText email = view.findViewById(R.id.signUpEmail);
        EditText password = view.findViewById(R.id.signInPassword);
        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        dismissKeyboard(email,password);
        btnSignUp.setOnClickListener(v -> {
            if(validateEmail(email.getText().toString().trim(),email)
                    && validatePassword(password.getText().toString().trim(),password)){
                checkEmail(email.getText().toString(),password.getText().toString(),v);
            }
            else{
                Toast.makeText(requireContext(),"Mohon lengkapi form terlebih dahulu"
                        ,Toast.LENGTH_LONG).show();
            }
        });
        btnBack.setOnClickListener(this::moveToLogin);
    }
    private void checkEmail(String email,String password,View view){
        User user = new User(email,password);
        ConnectToDB sqliteHelper = new ConnectToDB(requireContext());
        if(!sqliteHelper.isEmailExists(email)){
            Log.i(TAG, "checkEmail: memulai pembuatan akun");
            sqliteHelper.addUser(user);
            Toast.makeText(requireContext(),"Sign Up Berhasil",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> moveToLogin(view), Toast.LENGTH_LONG);
            Log.i(TAG, "checkEmail: pembuatan akun selesai");
        }
        else{
            Log.i(TAG, "checkEmail: email sudah ada");
            Toast.makeText(requireContext(),"Email sudah ada",Toast.LENGTH_LONG).show();
        }
    }
    private boolean validateEmail(String email,EditText em){
        if(email.isEmpty()){
            em.setError("Email tidak boleh kosong");
            return false;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            em.setError("Masukan email yang valid");
            return  false;
        }
        else {
            return true;
        }
    }
    private boolean validatePassword(String pass,EditText ps){
        if(pass.isEmpty()){
            ps.setError("Password tidak boleh kosong");
            return false;
        }
        else if(pass.length() < 6){
            ps.setError("Password harus minimal 6 karakter");
            return false;
        }
        else {
            return true;
        }
    }
    private void moveToLogin(View v){
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
    }
    private void dismissKeyboard(EditText idTextInput1,EditText idTextInput2){
        idTextInput1.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                imm = (InputMethodManager) requireActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
        idTextInput2.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                imm = (InputMethodManager) requireActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }
}