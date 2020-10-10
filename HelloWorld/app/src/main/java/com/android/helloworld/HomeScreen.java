package com.android.helloworld;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    BottomNavigationView bottomNav;
    Intent intent;
    ActionBar actionBar;
    ColorDrawable colorDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intent = getIntent();
        bottomNav = findViewById(R.id.menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrag,new HomeFragment()).
                commit();

        //set awal title di actionbar
        setTitle(getString(R.string.home));
        customizeActionBar("#5567c9");

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment fragmentSekarang = null;

                switch (item.getItemId()){
                    case R.id.homeFragment:
                        setTitle(getString(R.string.home));
                        fragmentSekarang = new HomeFragment();

                        break;
                    case R.id.profileFragment:
                        setTitle(getString(R.string.profile));
                        fragmentSekarang = new ProfileFragment();

                        break;
                    case R.id.settingFragment:
                        setTitle(getString(R.string.setting));
                        fragmentSekarang = new SettingFragment();

                        break;
                }

                assert fragmentSekarang != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFrag,
                        fragmentSekarang).commit();
                return true;
            };
    private void customizeActionBar(String color){
        actionBar = getSupportActionBar();
        colorDrawable = new ColorDrawable(Color.parseColor(color));
        actionBar.setBackgroundDrawable(colorDrawable);
    }
}