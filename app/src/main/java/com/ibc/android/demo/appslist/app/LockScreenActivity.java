package com.ibc.android.demo.appslist.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.spicycurryman.getdisciplined10.app.R;

public class LockScreenActivity extends Activity {
    private static final String TAG = LockScreenActivity.class.getSimpleName();
    EditText pin;
    TextView pinMsg;
    String firstPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

    }
    @Override
    public void onBackPressed() {
        // Display confirmation here, finish() activity.
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        Intent i = new Intent(LockScreenActivity.this, HeartBeat.class);
        startService(i);

        finish();
        super.onBackPressed();
    }

}