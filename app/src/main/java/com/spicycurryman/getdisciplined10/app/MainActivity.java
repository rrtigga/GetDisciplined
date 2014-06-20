package com.spicycurryman.getdisciplined10.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.triggertrap.seekarc.SeekArc;
import com.triggertrap.seekarc.SeekArc.OnSeekArcChangeListener;

public class MainActivity extends ActionBarActivity {
    private SeekArc mSeekArc;
    private SeekBar mRotation;
    private SeekBar mStartAngle;
    private SeekBar mSweepAngle;
    private SeekBar mArcWidth;
    private SeekBar mProgressWidth;
    private CheckBox mRoundedEdges;
    private CheckBox mTouchInside;
    private CheckBox mClockwise;
    private TextView mSeekArcProgress;

    private Button block_button_text;
    private Button security_settings_button_text;
    private Button blacklist_whitelist_button_text;
    private TextView hour_text;
    private TextView number_text;

    Button block_button1,security_settings_button1,blacklist_whitelist_button1;


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge);

        //Listeners for the three buttons
        addListenerOnButton();
        addListenerOnButton2();
        addListenerOnButton3();


        //Editing Button Text

        block_button_text = (Button)findViewById(R.id.block_button);
        block_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-light.ttf"));

        security_settings_button_text = (Button)findViewById(R.id.security_settings_button);
        security_settings_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-light.ttf"));

        blacklist_whitelist_button_text = (Button)findViewById(R.id.blacklist_whitelist_button);
        blacklist_whitelist_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-light.ttf"));

        hour_text = (TextView)findViewById(R.id.time_text);
        hour_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        number_text = (TextView)findViewById(R.id.seekArcProgress);
        number_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));


        block_button1 = (Button)findViewById(R.id.block_button);
        security_settings_button1 = (Button)findViewById(R.id.security_settings_button);
        blacklist_whitelist_button1= (Button)findViewById(R.id.blacklist_whitelist_button);






        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
        mRotation = (SeekBar) findViewById(R.id.rotation);
        mStartAngle = (SeekBar) findViewById(R.id.startAngle);
        mSweepAngle  = (SeekBar) findViewById(R.id.sweepAngle);
        mArcWidth = (SeekBar) findViewById(R.id.arcWidth);
        mProgressWidth = (SeekBar) findViewById(R.id.progressWidth);
        mRoundedEdges = (CheckBox) findViewById(R.id.roundedEdges);
        mTouchInside = (CheckBox) findViewById(R.id.touchInside);
        mClockwise = (CheckBox) findViewById(R.id.clockwise);

       mRotation.setProgress(mSeekArc.getArcRotation());
       mStartAngle.setProgress(mSeekArc.getStartAngle());
       mSweepAngle.setProgress(mSeekArc.getSweepAngle());
       mArcWidth.setProgress(mSeekArc.getArcWidth());
       mProgressWidth.setProgress(mSeekArc.getProgressWidth());





        mSeekArc.setOnSeekArcChangeListener(new OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }
            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                mSeekArcProgress.setText(String.valueOf(progress));
            }
        });

        mRotation.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                mSeekArc.setArcRotation(progress);
                mSeekArc.invalidate();
            }
        });

        mStartAngle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                mSeekArc.setStartAngle(progress);
                mSeekArc.invalidate();
            }
        });

        mSweepAngle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                mSeekArc.setSweepAngle(progress);
                mSeekArc.invalidate();
            }
        });

        mArcWidth.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                mSeekArc.setArcWidth(progress);
                mSeekArc.invalidate();
            }
        });

        mProgressWidth.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                mSeekArc.setProgressWidth(progress);
                mSeekArc.invalidate();
            }
        });

        mRoundedEdges.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                mSeekArc.setRoundedEdges(isChecked);
                mSeekArc.invalidate();
            }
        });

        mTouchInside.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                mSeekArc.setTouchInSide(isChecked);
            }
        });

        mClockwise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                mSeekArc.setClockwise(isChecked);
                mSeekArc.invalidate();
            }
        });

    }

    // for the on click activity responses for each of the 3 buttons on the menu
    public void addListenerOnButton() {

        final Context context = this;

        block_button1 = (Button) findViewById(R.id.block_button);

        block_button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BlockActivity.class);
                startActivity(intent);

            }

        });

    }

    // Make sure that you change the intent and class
    public void addListenerOnButton2() {

        final Context context = this;

        security_settings_button1 = (Button) findViewById(R.id.security_settings_button);

        security_settings_button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, InstalledAppActivity.class);
                startActivity(intent);

            }

        });

    }
    // Make sure that you change the intent and class

    public void addListenerOnButton3() {

        final Context context = this;

        blacklist_whitelist_button1 = (Button) findViewById(R.id.blacklist_whitelist_button);

        blacklist_whitelist_button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BlockActivity.class);
                startActivity(intent);

            }

        });

    }





}


