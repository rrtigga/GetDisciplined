package com.ibc.android.demo.appslist.app;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.spicycurryman.getdisciplined10.app.ApplicationCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HeartBeat extends Service {

    ArrayList<String> packagezList;
    SharedPreferences sharedPrefs;
    Map<String, ?> allEntries;
    SharedPreferences sharedPrefsapp;
    SharedPreferences endTimerPreferences;
    long timerends;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        endTimerPreferences = getApplicationContext().getSharedPreferences("endservice", Context.MODE_PRIVATE);
        timerends = endTimerPreferences.getLong("endservice", 0);



        sharedPrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        sharedPrefsapp = getApplicationContext().getSharedPreferences("appdb", Context.MODE_PRIVATE);
        allEntries = null;
        allEntries = sharedPrefsapp.getAll();
        packagezList = null;

        packagezList = new ArrayList<String>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            packagezList.add(entry.getKey());

        }


        try {
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();





            if(timerends ==0){
                android.os.Process.killProcess(android.os.Process.myPid());

            }

            if (System.currentTimeMillis() < timerends) {



                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {


                    for (Object object : packagezList) {

// Provide the packagename(s) of apps here, you want to show password activity
                        if (((appProcess.processName.contains((CharSequence) object) && !appProcess.processName.contains("com.spicycurryman.getdisciplined10.app"))
                                )) {

                            if(appProcess.processName.contains( "com.spicycurryman.getdisciplined10.app" ) && !appProcess.processName.contains((CharSequence) object) ){
                                    android.os.Process.killProcess(android.os.Process.myPid());


                            }


                            //Log.e("IS IT", appProcess.processName);

                            //Log.e("OH SHOOT ", String.valueOf(appProcess.pid));
                            if(ApplicationCheck.isActivityVisible()) {
                                //android.os.Process.killProcess(android.os.Process.myPid());


                            }



                                Intent i = new Intent(getApplicationContext(), LockScreenActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                i.putExtra("", "");
                                startActivity(i);

                                android.os.Process.killProcess(android.os.Process.myPid());





                        }
                    }
                }
            }

            }catch(Exception e){
            }

            Intent ishintent = new Intent(this, HeartBeat.class);
            PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pintent);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pintent);


            return START_STICKY;



    }


    @Override
    public void onDestroy() {

        Intent in = new Intent();
        in.setAction("YouWillNeverKillMe");
        sendBroadcast(in);

        Intent ishintent = new Intent(this, HeartBeat.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5000, pintent);



    }



}