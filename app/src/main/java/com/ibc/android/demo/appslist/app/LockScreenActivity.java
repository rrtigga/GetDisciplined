package com.ibc.android.demo.appslist.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.spicycurryman.getdisciplined10.app.R;

import java.util.ArrayList;
import java.util.List;

public class LockScreenActivity extends Activity {
    private static final String TAG = LockScreenActivity.class.getSimpleName();
    EditText pin;
    TextView pinMsg;
    String firstPin;
    private ArrayList newArrayList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

    }
    @Override
    public void onBackPressed() {

        /*
         * So here's what's going on: When the user presses the back button, 
         * the Instagram app (or any other app that's being blocked) is relaunched 
         * from the stack and that triggers the LockScreenActivity to be launched 
         * again. Kinda goes into an infinite loop in a sense. What I'm going to do 
         * to get around that, is completely kill the Instagram application.
         */



        /*
         * Every running application has a PID (process id). This is how the system 
         * keeps track of which apps are running. First, we're gonna need to find the 
         * PID of the blocked app (Instagram in this case).
         */

        // Grab a list of all running processes and their PIDs.
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();

        // Now loop through the list of PIDs and find Instagram's PID.


        // Now that we've got the PID, kill the Instagram process.

        ActivityManager  am1 = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);


        // Display confirmation here, finish() activity.
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


        startService(new Intent(this, HeartBeat.class));


  /*      Intent iHeartBeatService = new Intent(this, HeartBeat.class);
        PendingIntent piHeartBeatService = PendingIntent.getService(this, 0, iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piHeartBeatService);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 300000, piHeartBeatService);*/

        finish();
        super.onBackPressed();
    }

}