package com.android.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import static android.content.ContentValues.TAG;


public class fragment_login extends Fragment {
    private EditText emailLogin;
    private EditText passLogin;
    private InputMethodManager imm; //variable untuk menyembunyikan keyboard
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }
    //Method untuk menginisialisasi variable
    private void init(View view){
        emailLogin = view.findViewById(R.id.emailLogin);
        passLogin = view.findViewById(R.id.passLogin);
        TextView register = view.findViewById(R.id.Register);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        dismissKeyboard(emailLogin,passLogin);
        btnLogin.setOnClickListener(v -> check());
        register.setOnClickListener(v -> moveToSignUp(view));
    }
    //Method untuk input validasi
    private  void check(){
        ConnectToDB sqliteHelper = new ConnectToDB(requireContext());
        User user = new User(emailLogin.getText().toString().trim(), passLogin.getText().toString());
        User currentUser = sqliteHelper.Authenticate(user);
        //Check Authentication is successful or not
        if(emailLogin.getText().toString().equals("")
                || passLogin.getText().toString().equals("")){
            Toast.makeText(requireContext().getApplicationContext()
                    ,"Ops Sepertinya Kamu Belum "
                    + "Memasukan Input",Toast.LENGTH_SHORT).show();
        }
        else if (currentUser != null) {
            Toast.makeText(requireContext().getApplicationContext()
                    ,"Login Sukses",Toast.LENGTH_SHORT).show();
            SessionManagement sessionManagement = new SessionManagement(requireContext());
            sessionManagement.saveSession(user);
            moveToDashboard();
        }
        else {
            Toast.makeText(requireContext().getApplicationContext()
                    ,"Username atau Password Anda tidak benar!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: cek session");
        checkSession();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(requireContext());
        int isUserLoggedIn = sessionManagement.getSession();
        if (isUserLoggedIn != -1){
            Log.i(TAG, "checkSession: user sudah login");
            moveToDashboard();
        }
        else{
            Log.i(TAG, "checkSession: user belum login");
        }
    }

    //Method untuk menyembunyikan soft keyboard ketika mengklik area lain diluar EditText
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
    private void moveToDashboard(){
        Intent intent = new Intent(requireActivity(), Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
        requireActivity().finish();
    }
    private void moveToSignUp(View v){
        Navigation.findNavController(v).navigate(R.id.fragment_sign_up);
    }

}