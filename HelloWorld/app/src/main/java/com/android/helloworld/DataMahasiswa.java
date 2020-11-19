package com.android.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class DataMahasiswa extends Fragment {
    private static Button btnSave,btnHapus;

    public static Button getBtnSave() {
        return btnSave;
    }

    public static void setBtnSave(Button btnSave) {
        DataMahasiswa.btnSave = btnSave;
    }

    public static Button getBtnHapus() {
        return btnHapus;
    }

    public static void setBtnHapus(Button btnHapus) {
        DataMahasiswa.btnHapus = btnHapus;
    }

    private static EditText noMahasiswa,namaMahasiswa,phone;

    public static EditText getNoMahasiswa() {
        return noMahasiswa;
    }

    public static void setNoMahasiswa(EditText noMahasiswa) {
        DataMahasiswa.noMahasiswa = noMahasiswa;
    }

    public static EditText getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public static void setNamaMahasiswa(EditText namaMahasiswa) {
        DataMahasiswa.namaMahasiswa = namaMahasiswa;
    }

    public static EditText getPhone() {
        return phone;
    }

    public static void setPhone(EditText phone) {
        DataMahasiswa.phone = phone;
    }

    ImageView back;
    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb() {
        this.db = FirebaseFirestore.getInstance();
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_mahasiswa, container, false);
        setDb();
        btnSave = view.findViewById(R.id.btnSimpan);
        btnHapus = view.findViewById(R.id.btnHapus);
        noMahasiswa = view.findViewById(R.id.noMahasiswa);
        namaMahasiswa = view.findViewById(R.id.namaMahasiswa);
        phone = view.findViewById(R.id.phone);
        back = view.findViewById(R.id.back);
        btnSave.setOnClickListener(v -> {
            //sanity check
            if (!noMahasiswa.getText().toString().isEmpty() && !namaMahasiswa.getText().toString().isEmpty()) {
                tambahMahasiswa();
                Dashboard.setAddToFirebase(1);
                Log.i(TAG, "onClick: Update = "+Dashboard.getAddToFirebase());
            } else {
                Toast.makeText(requireActivity(), "No dan Nama Mhs tidak boleh kosong",
                        Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(v -> moveToDashboard());
        btnHapus.setOnClickListener(v -> {
            //sanity check
            deleteDataMahasiswa();
        });
        if(btnSave.getText().toString().equals("UPDATE")){
            updateDataMahasiswa();
        }

        return view;
    }
    private void tambahMahasiswa() {
        Mahasiswa mhs = new Mahasiswa(namaMahasiswa.getText().toString(),
                noMahasiswa.getText().toString(),
                phone.getText().toString());
        getDb().collection("DaftarMhs").document(mhs.getNama()).set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        try {
                            Toast.makeText(requireActivity(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                            Thread.sleep(2000);
                            moveToDashboard();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
    private void moveToDashboard(){
        Intent intent = new Intent(requireActivity(), Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Coba_intent_extra","percobaan");
        startActivity(intent);
    }
    private void deleteDataMahasiswa() {
        getDb().collection("DaftarMhs").document(namaMahasiswa.getText().toString())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        noMahasiswa.setText("");
                        namaMahasiswa.setText("");
                        phone.setText("");
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateDataMahasiswa() {
        Mahasiswa mhs = new Mahasiswa(namaMahasiswa.getText().toString(),
                noMahasiswa.getText().toString(),
                phone.getText().toString());
        getDb().collection("DaftarMhs").document(namaMahasiswa.getText().toString())
                .update("nama",mhs.getNama()
                        ,"nim",mhs.getNim()
                        ,"phone",mhs.getPhone())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        try {
                            noMahasiswa.setText("");
                            namaMahasiswa.setText("");
                            phone.setText("");
                            Toast.makeText(requireActivity(), "Mahasiswa berhasil Diupdate",
                                    Toast.LENGTH_SHORT).show();
                            Thread.sleep(2000);
                            Dashboard.getViewPager().setCurrentItem(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}