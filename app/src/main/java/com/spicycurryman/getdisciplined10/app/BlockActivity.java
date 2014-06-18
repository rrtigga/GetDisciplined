package com.spicycurryman.getdisciplined10.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


// Find out about the default file template warning



/**
 * Created by Spicycurryman on 6/17/14.
 */
public class BlockActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_apps);
    }

}