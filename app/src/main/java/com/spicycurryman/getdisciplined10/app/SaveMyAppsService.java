package com.spicycurryman.getdisciplined10.app;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Spicycurryman on 8/21/14.
 */
public class SaveMyAppsService extends Service{



    String CURRENT_PACKAGE_NAME = "com.spicycurryman.getdisciplined10.app.dev";
    String lastAppPN = "";
    boolean noDelay = false;
    public static SaveMyAppsService instance;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        scheduleMethod();
        CURRENT_PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.e("Current PN", "" + CURRENT_PACKAGE_NAME);

        instance = this;

        return START_STICKY;
    }

    private void scheduleMethod() {
        // TODO Auto-generated method stub

        ScheduledExecutorService scheduler = Executors
                .newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                // This method will check for the Running apps after every 100ms
                if(29==30 ) //check if the time is spent
                {
                    stop();
                }
                else{
                    checkRunningApps();
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void checkRunningApps() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
                .getRunningTasks(1);
        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
        String activityOnTop = ar.topActivity.getPackageName();



        Toast.makeText(SaveMyAppsService.this,
                "Your Message", Toast.LENGTH_LONG).show();

        Log.e("activity on TOp", "" + activityOnTop);


// Provide the packagename(s) of apps here, you want to show password activity
        if (activityOnTop.contains("com.android.camera")  // you can make this check even better
                || activityOnTop.contains(CURRENT_PACKAGE_NAME)) {
            while(true) {
                Toast.makeText(SaveMyAppsService.this,
                        "Your Message", Toast.LENGTH_LONG).show();
            }

        } else {
            // DO nothing
        }
    }

    public static void stop() {
        if (instance != null) {
            instance.stopSelf();
        }
    }
}