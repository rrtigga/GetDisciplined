package com.ibc.android.demo.appslist.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.spicycurryman.getdisciplined10.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LockScreenActivity extends Activity {
    private static final String TAG = LockScreenActivity.class.getSimpleName();
    Map<String, ?> allEntries;
    SharedPreferences sharedPrefsapp;
    ArrayList<String> packagezList;


    TextView MainT, sloganT, swipeT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lock_screen);

        MainT=(TextView) findViewById(R.id.main_text);
        Typeface mainCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto-thinitalic.ttf");
        MainT.setTypeface(mainCustomFont);

        sloganT=(TextView) findViewById(R.id.theslogan);
        Typeface sloganCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto-bolditalic.ttf");
        sloganT.setTypeface(sloganCustomFont);

        swipeT=(TextView) findViewById(R.id.disciplined);
        Typeface swipeCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto-thinitalic.ttf");
        swipeT.setTypeface(swipeCustomFont);

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
        sharedPrefsapp = getApplicationContext().getSharedPreferences("appdb", Context.MODE_PRIVATE);
        allEntries= null;
        allEntries = sharedPrefsapp.getAll();

        //prefix = "m";
        packagezList= null;


        packagezList = new ArrayList<String>();




        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.e("right key: ", entry.getKey() + "right value: " + entry.getValue().toString()  );
            packagezList.add(entry.getKey());
        }


        // Killing any process for blocked applications when the back button is pressed while the lock screen is displayed

        for(Object object: packagezList){
            am.killBackgroundProcesses((String) object);
            Log.d("Killed Background Process!: ", (String) object);


        }



        // Now that we've got the PID, kill the Instagram process.


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



    @Override
    public void onStop(){


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
            sharedPrefsapp = getApplicationContext().getSharedPreferences("appdb", Context.MODE_PRIVATE);
            allEntries= null;
            allEntries = sharedPrefsapp.getAll();

            //prefix = "m";
            packagezList= null;


            packagezList = new ArrayList<String>();




            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                //Log.e("right key: ", entry.getKey() + "right value: " + entry.getValue().toString()  );
                packagezList.add(entry.getKey());
            }


            // Killing any process for blocked applications when the back button is pressed while the lock screen is displayed

            for(Object object: packagezList){
                am.killBackgroundProcesses((String) object);
                Log.d("Killed Background Process!: ", (String) object);


            }



            // Now that we've got the PID, kill the Instagram process.


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
            super.onStop();
        }
    }

