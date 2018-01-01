package com.example.blalonde9489.projectapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class BgService extends Service {


    private Timer timer;

    public BgService() {
    }
    public void onCreate()
    {
        super.onCreate();
        Log.d("News reader", "Service started");
        startTimer();

        Intent service=new Intent(this, BgService.class);
        startService(service);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("News Reader", "No binding for this service");
        return null;
    }
    public void OnDestroy()
    {
        Log.d("News Reader", "Service destroyed");
        stopTimer();
    }
    private void startTimer()
    {
        TimerTask task=new TimerTask(){
            @Override
            public void run(){
                Log.d("news reader", "Timer executed");
            }
        };
        timer=new Timer(true);
        int delay=1000*10;
        int interval=1000*10;
        timer.schedule(task, delay, interval);
    }
    private void stopTimer(){
        if(timer!=null)
        {
            timer.cancel();
        }
    }
}
