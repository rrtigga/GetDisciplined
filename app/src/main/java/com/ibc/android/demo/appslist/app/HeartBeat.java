package com.ibc.android.demo.appslist.app;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

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
    private ActivityManager mActivityManager;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private String getTopPackageName() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        } else {
            // Hack, see
            // http://stackoverflow.com/questions/24625936/getrunningtasks-doesnt-work-in-android-l/27140347#27140347
            final List<ActivityManager.RunningAppProcessInfo> pis = mActivityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo pi : pis) {
                if (pi.pkgList.length == 1) return pi.pkgList[0];
            }
        }
        return "";
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        endTimerPreferences = getApplicationContext().getSharedPreferences("endservice", Context.MODE_PRIVATE);
        timerends = endTimerPreferences.getLong("endservice", 0);



        sharedPrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        sharedPrefsapp = getApplicationContext().getSharedPreferences("appdb", Context.MODE_PRIVATE);
        allEntries= null;
        allEntries = sharedPrefsapp.getAll();
        packagezList= null;


        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            packagezList.add(entry.getKey());

        }




        try {
             mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
                    .getRunningTasks(1);
            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            String activityOnTop = ar.topActivity.getPackageName();

            if(System.currentTimeMillis() < timerends ) {

                for (Object object : packagezList) {

                    if ((getTopPackageName().contains((CharSequence) object)) &&
                            (!activityOnTop.contains(getApplicationContext().getPackageName()
                            ))) {  // you have to make this check even better

                        mActivityManager.killBackgroundProcesses((String) object);


                        Intent i = new Intent(getApplicationContext(), LockScreenActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("", "");
                        startActivity(i);
                    }

                }
            }


        } catch (Exception e) {
        }

        Intent ishintent = new Intent(this, HeartBeat.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5000, pintent);


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