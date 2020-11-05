package com.android.helloworld;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
    String[] film;
    String[] sinopsis;
    RecyclerView recyclerView;
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
        film = getResources().getStringArray(R.array.ListFilm);
        sinopsis = getResources().getStringArray(R.array.Sinopsis);
        recyclerView = view.findViewById(R.id.recicleView);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(requireContext(),film,sinopsis,image);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }
}