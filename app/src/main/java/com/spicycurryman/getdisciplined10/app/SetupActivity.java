package com.spicycurryman.getdisciplined10.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class SetupActivity extends Activity {


    TextView MainT, sloganT, swipeT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setup);

        MainT=(TextView) findViewById(R.id.main_text);
        Typeface mainCustomFont = Typeface.createFromAsset(getAssets(),"fonts/Roboto-ThinItalic.ttf");
        MainT.setTypeface(mainCustomFont);

        sloganT=(TextView) findViewById(R.id.theslogan);
        Typeface sloganCustomFont = Typeface.createFromAsset(getAssets(),"fonts/Roboto-BoldItalic.ttf");
        sloganT.setTypeface(sloganCustomFont);

        swipeT=(TextView) findViewById(R.id.swipestart);
        Typeface swipeCustomFont = Typeface.createFromAsset(getAssets(),"fonts/Roboto-ThinItalic.ttf");
        swipeT.setTypeface(swipeCustomFont);
    }




}
