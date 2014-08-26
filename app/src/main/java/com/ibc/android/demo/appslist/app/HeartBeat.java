package com.ibc.android.demo.appslist.app;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class HeartBeat extends Service {



    private static final String TAG = HeartBeat.class.getSimpleName();
    public Timer TIMER;

   String CURRENT_PACKAGE_NAME;


    private static Set<AccessGranted> mAccessGrantedList = new HashSet<AccessGranted>();
    private Set<String> mLockedApps = new HashSet<String>();
    private long lastModified = 0;
    private BroadcastReceiver mScreenStateReceiver;
    private BroadcastReceiver mAccessGrantedReceiver;
    private File mLockedAppsFile;
    private ArrayList newArrayList = null;



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        startService(new Intent(this, HeartBeat.class));

        // Log.i("LocalService", "Received start id " + startId + ": " +
        // intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        if (TIMER == null) {

            TIMER = new Timer(true);
            TIMER.scheduleAtFixedRate(new LockAppsTimerTask(), 1000, 250);

            mScreenStateReceiver = new BroadcastReceiver() {

                private boolean screenOff;

                @Override
                public void onReceive(Context context, Intent intent) {


                    
                    if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                        screenOff = true;
                    } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                        screenOff = false;
                    }

                    if (screenOff) {
                        Log.i(TAG, "Cancel Timer");
                        TIMER.cancel();
                    } else {
                        Log.i(TAG, "Restart Timer");
                        TIMER = new Timer(true);
                        TIMER.scheduleAtFixedRate(new LockAppsTimerTask(), 1000, 250);
                    }
                }
            };

            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mScreenStateReceiver, filter);

            mAccessGrantedReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    String packageName = intent.getStringExtra("packageName");
                    if (action.equals(Constants.ACTION_GRANT_ACCESS) && packageName != null) {
                        AccessGranted ag = new AccessGranted(packageName);
                        mAccessGrantedList.remove(ag);
                        mAccessGrantedList.add(ag);
                    }
                }
            };

            IntentFilter filter2 = new IntentFilter(Constants.ACTION_GRANT_ACCESS);
            registerReceiver(mAccessGrantedReceiver, filter2);
        }
        // this.stopSelf();



 //startforeground goes here



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, HeartBeat.class));
    }


    private class LockAppsTimerTask extends TimerTask {

        @Override
        public void run() {
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

            try {
                //List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(1, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
                String packageName = "com.android.camera";
                ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
                        .getRunningTasks(1);
                ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
                String activityOnTop = ar.topActivity.getPackageName();

               // Log.e("activity on Top", "" + activityOnTop);
             //   Log.e(" My package name", "" + getApplicationContext().getPackageName());

                newArrayList = ApkAdapter.getArrayList();
                for (Object data : newArrayList) {


// Provide the packagename(s) of apps here, you want to show password activity
                    if ((activityOnTop.contains((CharSequence) data)) &&
                            (!activityOnTop.contains(getApplicationContext().getPackageName()
                            ))) {  // you have to make this check even better


                        Intent i = new Intent(getApplicationContext(), LockScreenActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("packageName", packageName);
                        startActivity(i);
                    }
                }


            } catch (Exception e) {
                Log.e("Foreground App", e.getMessage(), e);
            }
        }



    }

}