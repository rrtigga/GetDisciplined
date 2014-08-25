package com.alimuzaffar.android.childlock;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class HeartBeat extends Service {
    private static final String TAG = HeartBeat.class.getSimpleName();
    public Timer TIMER;

    private static Set<AccessGranted> mAccessGrantedList = new HashSet<AccessGranted>();
    private Set<String> mLockedApps = new HashSet<String>();
    private long lastModified = 0;
    private BroadcastReceiver mScreenStateReceiver;
    private BroadcastReceiver mAccessGrantedReceiver;
    private File mLockedAppsFile;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Log.i("LocalService", "Received start id " + startId + ": " +
        // intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        if (TIMER == null) {
            updateBlockedApps();

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
                    if(action.equals(Constants.ACTION_GRANT_ACCESS) && packageName != null) {
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
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
        TIMER.cancel();
        TIMER = null;
        unregisterReceiver(mScreenStateReceiver);
        unregisterReceiver(mAccessGrantedReceiver);
    }

    private void updateBlockedApps() {
        try {
            if(mLockedAppsFile == null)
                mLockedAppsFile = new File(getFilesDir(),Constants.LOCKED_APP_FILE);

            if(mLockedAppsFile == null || mLockedAppsFile.lastModified() == lastModified) {
                Log.i(TAG, "NO CHANGE file = "+mLockedAppsFile);
                return;
            }
            lastModified = mLockedAppsFile.lastModified();
            Log.i(TAG, "CHANGE");
            FileInputStream istream = getApplicationContext().openFileInput(Constants.LOCKED_APP_FILE);
            String jsonStr = Utils.getStringFromInputStream(istream);
            istream.close();
            if (jsonStr != null && jsonStr.length() > 0) {
                JSONObject json = new JSONObject(jsonStr);
                JSONArray locked = json.optJSONArray("locked");
                if (locked != null) {
                    mLockedApps = new HashSet<String>();
                    for (int i = 0; i < locked.length(); i++) {
                        String packageName = locked.getString(i);
                        mLockedApps.add(packageName);
                    }
                }
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private class LockAppsTimerTask extends TimerTask {

        @Override
        public void run() {
            updateBlockedApps();
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

            try {
                List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(1, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
                if (recentTasks != null && recentTasks.size() > 0) {
                    String packageName = recentTasks.get(0).baseIntent.getComponent().getPackageName();
                    if (isBlocked(packageName)) {
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

        private boolean isBlocked(String packageName) {
            // Log.i("Foreground App", "SIZE => "+mAccessGrantedList.size());

            if (mLockedApps.contains(packageName)) {
                if (!mAccessGrantedList.contains(new AccessGranted(packageName))) {
                    return true;
                } else {
                    for (AccessGranted ag : mAccessGrantedList) {
                        if (ag.packageName.equals(packageName)) {
                            return ag.isExpired();
                        }
                    }
                }
            }
            return false;
        }

    };

}