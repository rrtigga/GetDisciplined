package com.alimuzaffar.android.childlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.spicycurryman.getdisciplined10.app.R;

import org.json.JSONObject;

import java.io.FileInputStream;

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







    private void checkPin(Editable s) {
        String savedPin = "";
        try {
            FileInputStream istream = openFileInput(Constants.LOCKED_APP_FILE);
            String jsonStr = Utils.getStringFromInputStream(istream);
            JSONObject jo = new JSONObject(jsonStr);
            savedPin = jo.getString("pin");
        } catch (Exception e) {
            return;
        }

        if (s.toString().equals(savedPin)) {
            Intent i2 = new Intent();
            i2.setAction(Constants.ACTION_GRANT_ACCESS);
            i2.putExtra("packageName", getIntent().getStringExtra("packageName"));
            sendBroadcast(i2);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LockScreenActivity.this.finish();
                }
            }, 250);
        } else {
            pin.setText(null);
            pinMsg.setText("Incorrect PIN, Please try again.");
        }
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