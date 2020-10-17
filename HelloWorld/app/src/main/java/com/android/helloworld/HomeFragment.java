package com.android.helloworld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class HomeFragment extends Fragment {
    WifiBroadcastReceiver wifiBroadcastReceiver = new WifiBroadcastReceiver();
    SwitchMaterial wifiSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        wifiSwitch = view.findViewById(R.id.wifiSwitch);
        initBroadcastReceiver();
        return view;
    }

    private void initBroadcastReceiver(){
        WifiManager wifiManager = (WifiManager) requireContext().
                getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked && !wifiManager.isWifiEnabled()){
                //semenjak Android Q, Sistem tidak dapat mematikan wifi secara langsung
                //jika versi android adalah Android Q atau lebih maka harus melalui jendela setelan
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //digunakan untuk menampilkan panel wifi untuk memilih jaringan wifi
                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                    startActivityForResult(panelIntent, 1);
                } else {
                    wifiManager.setWifiEnabled(true);
                }
            }
            else if(!isChecked && wifiManager.isWifiEnabled()){
                //semenjak Android Q, Sistem tidak dapat mematikan wifi secara langsung
                //jika versi android adalah Android Q atau lebih maka harus melalui jendela setelan
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                    startActivityForResult(panelIntent, 1);
                } else {
                //jika versi dibawah nya, maka masih dapat melakukan pemutusan wifi secara langsung
                    wifiManager.setWifiEnabled(false);
                }
            }
        });
    }

    //saya melakukan register pada broadcast saat program berjalan, maka notifikasi akan muncul
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        requireActivity().registerReceiver(wifiBroadcast,filter);
        requireActivity().registerReceiver(wifiBroadcastReceiver,filter);
    }

    //ketika program dikeluarkan atau di minimize, maka notifikasi tidak akan muncul
    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(wifiBroadcast);
        requireActivity().unregisterReceiver(wifiBroadcastReceiver);
    }

    /*memanggil object WifiBroadcastReceiver untuk membuat switch selalu runtime ketika
    wifi on ataupun off*/
    private WifiBroadcastReceiver wifiBroadcast = new WifiBroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            if(wifiStateExtra == WifiManager.WIFI_STATE_ENABLED){
                wifiSwitch.setChecked(true);
                wifiSwitch.setText(R.string.wifiOn);
            }
            else if(wifiStateExtra == WifiManager.WIFI_STATE_DISABLED){
                wifiSwitch.setChecked(false);
                wifiSwitch.setText(R.string.wifiOff);
            }
        }
    };

}