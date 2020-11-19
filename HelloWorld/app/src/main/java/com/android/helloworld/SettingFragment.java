package com.android.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Button btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(v -> logout());
//        DataMahasiswa.getBtnSave().setText("SAVE");
        return view;
    }

    private void logout(){
        SessionManagement sessionManagement = new SessionManagement(requireContext());
        sessionManagement.removeSesion();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
        requireActivity().finish();
    }
}