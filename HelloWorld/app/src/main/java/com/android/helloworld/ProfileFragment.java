package com.android.helloworld;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {
    String[] film,sinopsis,namaMahasiswa,nmMahasiswa,phone;
    private FirebaseFirestore db ;
    private ArrayList<Mahasiswa> mahasiswas = new ArrayList<Mahasiswa>();
    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb() {
        this.db = FirebaseFirestore.getInstance();
    }

    RecyclerView recyclerView1,recyclerView2;
    int[] image ={R.drawable.wonder_woman,R.drawable.trolls_world,R.drawable.thecroods
            ,R.drawable.spongebob,R.drawable.mulan,R.drawable.bad_boy,R.drawable.a_quite_place
            ,R.drawable.peter_rabbit,R.drawable.birds_of_prey,R.drawable.dolittel,R.drawable.onward
            ,R.drawable.the_invicible_man,R.drawable.sonic,R.drawable.free_guy
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setDb();
        film = getResources().getStringArray(R.array.ListFilm);
        sinopsis = getResources().getStringArray(R.array.Sinopsis);
        recyclerView1 = view.findViewById(R.id.recicleView);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(requireContext(),film,sinopsis,image);
        recyclerView1.setAdapter(recycleViewAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext()));
        int prev = Dashboard.getAddToFirebase();
        getAllDocument(view);
        do{
            if(Dashboard.getAddToFirebase() > prev){
                getAllDocument(view);
            }
            return view;
        }
        while (true);
    }

    private void getAllDocument(View view){
        Log.i(TAG, "getAllDocument: masuk logi");
        db.collection("DaftarMhs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int posi = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Mahasiswa mh = new Mahasiswa(document.get("nama").toString()
                                        ,document.get("nim").toString()
                                        ,document.get("phone").toString());
                                mahasiswas.add(mh);
                                Log.i(TAG, "onComplete: "+mahasiswas.get(posi).getNim());
                                posi++;
                            }
                            Log.i(TAG, "getAllDocument: mahasiswaSize = "+mahasiswas.size());
                            namaMahasiswa = new String[mahasiswas.size()];
                            nmMahasiswa = new String[mahasiswas.size()];
                            phone = new String[mahasiswas.size()];
                            int pos = 0;
                            for(int i = mahasiswas.size();i>0;i--){
                                namaMahasiswa[pos] = mahasiswas.get(pos).getNama();
                                nmMahasiswa[pos] = mahasiswas.get(pos).getNim();
                                phone[pos] = mahasiswas.get(pos).getPhone();
                                Log.i(TAG, "onCreateView: mahasiswa = "+namaMahasiswa[pos]+"\n"
                                        +"nim : "+nmMahasiswa[pos]+"\n"+"phone : "+phone[pos]);
                                pos++;
                            }
                        recyclerView2 = view.findViewById(R.id.reMahasiswa);
                        FirebaseRecycleView firebaseRecycleView = new FirebaseRecycleView(requireContext(),namaMahasiswa,nmMahasiswa,phone);
                        firebaseRecycleView.notifyDataSetChanged();
                        recyclerView2.setAdapter(firebaseRecycleView);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext()));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}