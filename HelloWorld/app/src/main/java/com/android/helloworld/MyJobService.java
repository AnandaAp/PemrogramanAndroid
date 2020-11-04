package com.android.helloworld;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();
    private boolean JobCancel =false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: service berjalan");
        backgroundProcess(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: service berhenti");
        JobCancel = true;
        return true;
    }

    public void backgroundProcess(final JobParameters parameters){
        new Thread(() -> {
            Looper.prepare();
            for(int i = 0; i< 10;i++){
                Log.i(TAG, "run: "+ i);
                final int counter = i;
                Handler backHandler = new Handler(getMainLooper());
                backHandler.post(() -> Toast.makeText(getApplicationContext()
                        ,"Perulangan Service "+counter
                        ,Toast.LENGTH_SHORT).show());

                if(JobCancel){
                    return;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "InterruptedException: ",e.getCause() );
                }
            }
            Log.i(TAG, "Job Service Selesai");
            Toast.makeText(getApplicationContext(),"Job Service Selesai",
                    Toast.LENGTH_SHORT).show();
        }).start();
    }

}
