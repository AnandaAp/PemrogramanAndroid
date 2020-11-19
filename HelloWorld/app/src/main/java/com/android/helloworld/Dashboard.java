package com.android.helloworld;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.WifiManager;
//import android.widget.CompoundButton;
//import com.google.android.material.switchmaterial.SwitchMaterial;

public class Dashboard extends AppCompatActivity {
    public static TabLayout getTabLayout() {
        return tabLayout;
    }

    public static void setTabLayout(TabLayout tabLayout) {
        Dashboard.tabLayout = tabLayout;
    }

    private static TabLayout tabLayout;
    TabItem home,profile,setting;

    public static ViewPager getViewPager() {
        return viewPager;
    }

    public static void setViewPager(ViewPager viewPager) {
        Dashboard.viewPager = viewPager;
    }

    private static ViewPager viewPager;
    private PageAdapter pageAdapter;
    private static int addToFirebase = 0;
    private static boolean editData = false;

    public static boolean isEditData() {
        return editData;
    }

    public static void setEditData(boolean editData) {
        Dashboard.editData = editData;
    }

    public static boolean isAfterEditData() {
        return afterEditData;
    }

    public static void setAfterEditData(boolean afterEditData) {
        Dashboard.afterEditData = afterEditData;
    }

    private static boolean afterEditData = false;

    public static int getAddToFirebase() {
        return addToFirebase;
    }

    public static void setAddToFirebase(int addToFirebase) {
        Dashboard.addToFirebase = addToFirebase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        viewPagerOnSet();
    }

    private void init(){
        hideTitleBar();
        tabLayout = findViewById(R.id.tabLayout);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        setting = findViewById(R.id.setting);
        viewPager = findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
    }
    private void hideTitleBar(){
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }
    private void viewPagerOnSet(){
        getViewPager().setAdapter(pageAdapter);
        getViewPager().addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        getTabLayout().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}