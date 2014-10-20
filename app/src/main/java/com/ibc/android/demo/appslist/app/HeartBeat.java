package com.ibc.android.demo.appslist.app;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //startService(new Intent(this, HeartBeat.class));

        endTimerPreferences = getApplicationContext().getSharedPreferences("endservice", Context.MODE_PRIVATE);
        timerends= endTimerPreferences.getLong("endservice", 0);

        //Log.e("MONOLO  ", timerends + "");


        sharedPrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        sharedPrefsapp = getApplicationContext().getSharedPreferences("appdb", Context.MODE_PRIVATE);
        allEntries= null;
        allEntries = sharedPrefsapp.getAll();
        packagezList= null;

        packagezList = new ArrayList<String>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.e("right key: ", entry.getKey() + "right value: " + entry.getValue().toString()  );
            packagezList.add(entry.getKey());

        }

        /*for(Object object: packagezList){
            Log.e("YO!", (String) object);
        }*/

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        try {
            //List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(1, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager
                    .getRunningTasks(1);
            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            String activityOnTop = ar.topActivity.getPackageName();



            //check the try

            //Log.e("activity on Top", "" + activityOnTop);
            // Log.e(" My package name", "" + getApplicationContext().getPackageName());

            //for (Object data : newArrayList) {

            if(System.currentTimeMillis() < timerends ) {


                long second = (timerends / 1000) % 60;
                long minute = (timerends / (1000 * 60)) % 60;
                long hour = (timerends / (1000 * 60 * 60)) % 24;

                String time = String.format("%02d:%02d:%02d", hour, minute, second);


/*               NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification myNotification = new Notification(R.drawable.ic_launcher, "Apps now locked!", System.currentTimeMillis());
                Context context = getApplicationContext();
                String notificationTitle = "Apps blocked!";
                String notificationText = "Time Remaining: " + time;
                Intent myIntent = new Intent(HeartBeat.this, HeartBeat.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(HeartBeat.this, 0,   myIntent, Intent.FILL_IN_ACTION);
                myNotification.flags = Notification.FLAG_ONGOING_EVENT;
                myNotification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
                notificationManager.notify(1, myNotification);*/

                for (Object object : packagezList) {

// Provide the packagename(s) of apps here, you want to show password activity
                    if ((activityOnTop.contains((CharSequence) object)) &&
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
            // Log.e("Foreground App", e.getMessage(), e);
        }

        Intent ishintent = new Intent(this, HeartBeat.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5000, pintent);




        return START_STICKY;

}

        // Log.i("LocalService", "Received start id " + startId + ": " +
        // intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.






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



        //startService(new Intent(this, HeartBeat.class));
    }

        // this.stopSelf();
        //startforeground goes here

    }