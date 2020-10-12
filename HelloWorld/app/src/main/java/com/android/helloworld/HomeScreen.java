package com.android.helloworld;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    BottomNavigationView bottomNav;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Memulai Program
        init();

    }
    private void init(){
        intent = getIntent();
        bottomNav = findViewById(R.id.menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrag,new HomeFragment()).
                commit();
        //Menghilangkan ActionBar
        hideTitleBar();
    }
    //Method agar dapat berpindah antar fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment fragmentSekarang = null;

                switch (item.getItemId()){
                    case R.id.homeFragment:
                        fragmentSekarang = new HomeFragment();

                        break;
                    case R.id.profileFragment:
                        fragmentSekarang = new ProfileFragment();

                        break;
                    case R.id.settingFragment:
                        fragmentSekarang = new SettingFragment();

                        break;
                }

                assert fragmentSekarang != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFrag,
                        fragmentSekarang).commit();
                return true;
            };
    //Method untuk menyembunyikan ActionBar
    private void hideTitleBar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}