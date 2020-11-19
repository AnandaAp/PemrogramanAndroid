package com.android.helloworld;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class    HomeFragment extends Fragment {
    private static final String TAG = "Dashboard";
    WifiBroadcastReceiver wifiBroadcastReceiver = new WifiBroadcastReceiver();
    SwitchMaterial wifiSwitch;
    Button btnStartJob,btnStopJob;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        wifiSwitch = view.findViewById(R.id.wifiSwitch);
        btnStartJob = view.findViewById(R.id.btnStart);
        btnStopJob = view.findViewById(R.id.btnStop);

        serviceInit();
        initWifiConfigure();
        return view;
    }

    private void initWifiConfigure(){
        WifiManager wifiManager = (WifiManager) requireContext().
                getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked && !wifiManager.isWifiEnabled()){
                /*semenjak Android Q, Sistem tidak dapat mematikan wifi secara langsung
                jika versi android adalah Android Q atau lebih maka harus melalui jendela setelan*/
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

    private void serviceInit(){
            btnStartJob.setOnClickListener(v -> onStartJobService());
            btnStopJob.setOnClickListener(v -> onStopJobService());
    }

    //saya melakukan register pada broadcast saat program berjalan, maka notifikasi akan muncul
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction("com.android.HelloWorld.SEND_ACTION");
        requireActivity().registerReceiver(wifiBroadcast,filter);
        requireActivity().registerReceiver(wifiBroadcastReceiver,filter);
    }

    //ketika program dikeluarkan atau di minimize, maka notifikasi tidak akan muncul
    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(wifiBroadcast);
        requireActivity().unregisterReceiver(wifiBroadcastReceiver);
    }

    /*memanggil object WifiBroadcastReceiver untuk membuat switch selalu runtime ketika
    wifi on ataupun off*/
    private final WifiBroadcastReceiver wifiBroadcast = new WifiBroadcastReceiver(){
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
    private void onStartJobService(){
        Log.i(TAG, "Masuk ke onStartJobService");
        Log.i(TAG, "onStartJobService: Membuat componentName");
        ComponentName componentName = new ComponentName(requireActivity().getApplicationContext(),MyJobService.class);
        Log.i(TAG, "onStartJobService: membuat info");
        JobInfo info = new JobInfo.Builder(121018,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        Log.i(TAG, "onStartJobService: membuat scheduler");
        JobScheduler scheduler = (JobScheduler) requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        Log.i(TAG, "onStartJobService: pengecekan resultCode");
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG, "onStartJobService: job berhasil di buat");
        }
        else{
            Log.i(TAG, "onStartJobService: job scheduling failed");
        }
    }
    private void onStopJobService(){
        JobScheduler scheduler = (JobScheduler) requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(121018);
        Log.i(TAG, "onStopJobService: job di hentikan");
        Toast.makeText(requireContext().getApplicationContext()
                ,"Service dihentikan",Toast.LENGTH_SHORT).show();
    }


}