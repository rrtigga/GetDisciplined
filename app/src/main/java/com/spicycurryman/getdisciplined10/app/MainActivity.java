package com.spicycurryman.getdisciplined10.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

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
    private TextView mSeekArcMinuteProgress;
    private TextView mSeekArcSecondProgress;

    private Button block_button_text;
    private Button start_timer_text;
//    private Button security_settings_button_text;
//    private Button blacklist_whitelist_button_text;
    private TextView hour_text;
    private TextView number_text;

    private TextView minute_text;
    private TextView second_text;


    private TextView little_hour_text;
    private TextView little_minute_text;
    private TextView little_second_text;



    Button block_button1,security_settings_button1,blacklist_whitelist_button1;


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    View previousView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);



        //Make sure you find out why it appears after a whole 1 second after the app appears
        SpannableString s = new SpannableString("GetDisciplined");
        s.setSpan(new TypefaceSpan(this, "roboto-lightitalic.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);
        // set the action bar in this activity as the home
        actionBar.setHomeButtonEnabled(true);

        setContentView(R.layout.merge);



        //Listeners for the three buttons
        addListenerOnButton();
//        addListenerOnButton2();
        //addListenerOnButton3();


        //Editing Button Text

        block_button_text = (Button)findViewById(R.id.block_button);
        block_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        start_timer_text = (Button)findViewById(R.id.start_button);
        start_timer_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));
/*
        security_settings_button_text = (Button)findViewById(R.id.security_settings_button);
        security_settings_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-light.ttf"));*/

      /*  blacklist_whitelist_button_text = (Button)findViewById(R.id.blacklist_whitelist_button);
        blacklist_whitelist_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-light.ttf"));*/

//        hour_text = (TextView)findViewById(R.id.hourtext);
//        hour_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        number_text = (TextView)findViewById(R.id.hour_progress_number);
        number_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        minute_text = (TextView)findViewById(R.id.minute_progress_number);
        minute_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        second_text = (TextView)findViewById(R.id.second_progress_number);
        second_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));




      /*  block_button1 = (Button)findViewById(R.id.block_button);
        security_settings_button1 = (Button)findViewById(R.id.security_settings_button);
        blacklist_whitelist_button1= (Button)findViewById(R.id.blacklist_whitelist_button);
*/
        little_hour_text = (TextView)findViewById(R.id.hourtext);
        little_hour_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_minute_text = (TextView)findViewById(R.id.minutetext);
        little_minute_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_second_text = (TextView)findViewById(R.id.secondtext);
        little_second_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));




        mSeekArc = (SeekArc) findViewById(R.id.seekArc);

//Here is the actual "hour progress number" aka the TextView that changes as the scrubber is dragged around
        mSeekArcProgress = (TextView) findViewById(R.id.hour_progress_number);

        mSeekArcMinuteProgress = (TextView) findViewById(R.id.minute_progress_number);

        mSeekArcSecondProgress = (TextView) findViewById(R.id.second_progress_number);



        //make textview selectable

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView previousText = (TextView) previousView;
                TextView curText = (TextView) v;
                // If the clicked view is selected, do nothing
                if (curText.isSelected()) {
                    //curText.setSelected(false);
                    //curText.setTextColor(getResources().getColor(R.color.red_highlight));
                } else { // If this isn't selected, deselect  the previous one (if any)
                    if (previousText != null && previousText.isSelected()) {
                        previousText.setSelected(false);
                        previousText.setTextColor(getResources().getColor(R.color.red_highlight));
                    }
                    curText.setSelected(true);
                    curText.setTextColor(getResources().getColor(R.color.white));
                    previousView = v;
                }

                if(v.getId() == R.id.hourtext){
                    //corresponding button logic should below here
                    mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekArc seekArc) {
                        }
                        @Override
                        public void onStartTrackingTouch(SeekArc seekArc) {
                        }


                        //This sets the actual string for the hours
                        @Override
                        public void onProgressChanged(SeekArc seekArc, int progress,
                                                      boolean fromUser) {
                            if (progress ==0){
                                mSeekArcProgress.setText("00");
                            }
                            else if (progress ==5){
                                mSeekArcProgress.setText("01");
                            }

                            else if (progress ==10){
                                mSeekArcProgress.setText("02");
                            }

                            else if (progress ==15){
                                mSeekArcProgress.setText("03");
                            }

                            else if (progress ==20){
                                mSeekArcProgress.setText("04");
                            }

                            else if (progress ==25){
                                mSeekArcProgress.setText("05");
                            }

                            else if (progress ==30){
                                mSeekArcProgress.setText("06");
                            }

                            else if (progress ==35){
                                mSeekArcProgress.setText("07");
                            }

                            else if (progress ==40){
                                mSeekArcProgress.setText("08");
                            }

                            else if (progress ==45){
                                mSeekArcProgress.setText("09");
                            }

                            else if (progress ==50){
                                mSeekArcProgress.setText("10");
                            }
                            else if (progress ==55){
                                mSeekArcProgress.setText("11");
                            }
                            else if (progress ==60){
                                mSeekArcProgress.setText("12");
                            }
                            else if (progress ==65){
                                mSeekArcProgress.setText("13");
                            }
                            else if (progress ==70){
                                mSeekArcProgress.setText("14");
                            }
                            else if (progress ==75){
                                mSeekArcProgress.setText("15");
                            }
                            else if (progress ==80){
                                mSeekArcProgress.setText("16");
                            }
                            else if (progress ==85){
                                mSeekArcProgress.setText("17");
                            }
                            else if (progress ==90){
                                mSeekArcProgress.setText("18");
                            }
                            else if (progress ==95){
                                mSeekArcProgress.setText("19");
                            }
                            else if (progress ==100){
                                mSeekArcProgress.setText("20");
                            }
                            else if (progress ==105){
                                mSeekArcProgress.setText("21");
                            }
                            else if (progress ==110){
                                mSeekArcProgress.setText("22");
                            }
                            else if (progress ==115){
                                mSeekArcProgress.setText("23");
                            }
                            else if (progress ==120){
                                mSeekArcProgress.setText("24");
                            }


                        }
                    });
                } else if (v.getId() == R.id.minutetext) {
                    //corresponding button logic should below here

                    mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {


                        @Override
                        public void onStopTrackingTouch(SeekArc seekArc) {
                        }
                        @Override
                        public void onStartTrackingTouch(SeekArc seekArc) {
                        }


                        //This sets the actual string for the minutes
                        @Override
                        public void onProgressChanged(SeekArc seekArc, int progress,
                                                      boolean fromUser) {

                            if (progress ==0){
                                mSeekArcMinuteProgress.setText("00");
                            }
                            else if (progress ==2){
                                mSeekArcMinuteProgress.setText("01");
                            }

                            else if (progress ==4){
                                mSeekArcMinuteProgress.setText("02");
                            }

                            else if (progress ==6){
                                mSeekArcMinuteProgress.setText("03");
                            }

                            else if (progress ==8){
                                mSeekArcMinuteProgress.setText("04");
                            }

                            else if (progress ==10){
                                mSeekArcMinuteProgress.setText("05");
                            }

                            else if (progress ==12){
                                mSeekArcMinuteProgress.setText("06");
                            }

                            else if (progress ==14){
                                mSeekArcMinuteProgress.setText("07");
                            }

                            else if (progress ==16){
                                mSeekArcMinuteProgress.setText("08");
                            }

                            else if (progress ==18){
                                mSeekArcMinuteProgress.setText("09");
                            }
                            else if (progress ==20){
                                mSeekArcMinuteProgress.setText("10");
                            }
                            else if (progress ==22){
                                mSeekArcMinuteProgress.setText("11");
                            }

                            else if (progress ==24){
                                mSeekArcMinuteProgress.setText("12");
                            }

                            else if (progress ==26){
                                mSeekArcMinuteProgress.setText("13");
                            }

                            else if (progress ==28){
                                mSeekArcMinuteProgress.setText("14");
                            }

                            else if (progress ==30){
                                mSeekArcMinuteProgress.setText("15");
                            }

                            else if (progress ==32){
                                mSeekArcMinuteProgress.setText("16");
                            }

                            else if (progress ==34){
                                mSeekArcMinuteProgress.setText("17");
                            }

                            else if (progress ==36){
                                mSeekArcMinuteProgress.setText("18");
                            }

                            else if (progress ==38){
                                mSeekArcMinuteProgress.setText("19");
                            }

                            else if (progress ==40){
                                mSeekArcMinuteProgress.setText("20");
                            }

                            else if (progress ==42){
                                mSeekArcMinuteProgress.setText("21");
                            }

                            else if (progress ==44){
                                mSeekArcMinuteProgress.setText("22");
                            }

                            else if (progress ==46){
                                mSeekArcMinuteProgress.setText("23");
                            }

                            else if (progress ==48){
                                mSeekArcMinuteProgress.setText("24");
                            }

                            else if (progress ==50){
                                mSeekArcMinuteProgress.setText("25");
                            }

                            else if (progress ==52){
                                mSeekArcMinuteProgress.setText("26");
                            }

                            else if (progress ==54){
                                mSeekArcMinuteProgress.setText("27");
                            }

                            else if (progress ==56){
                                mSeekArcMinuteProgress.setText("28");
                            }

                            else if (progress ==58){
                                mSeekArcMinuteProgress.setText("29");
                            }

                            else if (progress ==60){
                                mSeekArcMinuteProgress.setText("30");
                            }

                            else if (progress ==62){
                                mSeekArcMinuteProgress.setText("31");
                            }

                            else if (progress ==64){
                                mSeekArcMinuteProgress.setText("32");
                            }

                            else if (progress ==66){
                                mSeekArcMinuteProgress.setText("33");
                            }

                            else if (progress ==68){
                                mSeekArcMinuteProgress.setText("34");
                            }

                            else if (progress ==70){
                                mSeekArcMinuteProgress.setText("35");
                            }

                            else if (progress ==72){
                                mSeekArcMinuteProgress.setText("36");
                            }

                            else if (progress ==74){
                                mSeekArcMinuteProgress.setText("37");
                            }

                            else if (progress ==76){
                                mSeekArcMinuteProgress.setText("38");
                            }

                            else if (progress ==78){
                                mSeekArcMinuteProgress.setText("39");
                            }

                            else if (progress ==80){
                                mSeekArcMinuteProgress.setText("40");
                            }

                            else if (progress ==82){
                                mSeekArcMinuteProgress.setText("41");
                            }

                            else if (progress ==84){
                                mSeekArcMinuteProgress.setText("42");
                            }

                            else if (progress ==86){
                                mSeekArcMinuteProgress.setText("43");
                            }

                            else if (progress ==88){
                                mSeekArcMinuteProgress.setText("44");
                            }

                            else if (progress ==90){
                                mSeekArcMinuteProgress.setText("45");
                            }

                            else if (progress ==92){
                                mSeekArcMinuteProgress.setText("46");
                            }

                            else if (progress ==94){
                                mSeekArcMinuteProgress.setText("47");
                            }

                            else if (progress ==96){
                                mSeekArcMinuteProgress.setText("48");
                            }

                            else if (progress ==98){
                                mSeekArcMinuteProgress.setText("49");
                            }

                            else if (progress ==100){
                                mSeekArcMinuteProgress.setText("50");
                            }

                            else if (progress ==102){
                                mSeekArcMinuteProgress.setText("51");
                            }

                            else if (progress ==104){
                                mSeekArcMinuteProgress.setText("52");
                            }

                            else if (progress == 106){
                                mSeekArcMinuteProgress.setText("53");
                            }

                            else if (progress ==108){
                                mSeekArcMinuteProgress.setText("54");
                            }

                            else if (progress ==110){
                                mSeekArcMinuteProgress.setText("55");
                            }

                            else if (progress ==112){
                                mSeekArcMinuteProgress.setText("56");
                            }

                            else if (progress ==114){
                                mSeekArcMinuteProgress.setText("57");
                            }

                            else if (progress ==116){
                                mSeekArcMinuteProgress.setText("58");
                            }

                            else if (progress ==118){
                                mSeekArcMinuteProgress.setText("59");
                            }

                            else if (progress ==120){
                                mSeekArcMinuteProgress.setText("60");
                            }




                        }
                    });
                } else if (v.getId() == R.id.secondtext) {
                    //corresponding button logic should below here
                    mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekArc seekArc) {
                        }
                        @Override
                        public void onStartTrackingTouch(SeekArc seekArc) {
                        }


                        //This sets the actual string for the seconds
                        @Override
                        public void onProgressChanged(SeekArc seekArc, int progress,
                                                      boolean fromUser) {
                            if (progress ==0){
                                mSeekArcSecondProgress.setText("00");
                            }
                            else if (progress ==2){
                                mSeekArcSecondProgress.setText("01");
                            }

                            else if (progress ==4){
                                mSeekArcSecondProgress.setText("02");
                            }

                            else if (progress ==6){
                                mSeekArcSecondProgress.setText("03");
                            }

                            else if (progress ==8){
                                mSeekArcSecondProgress.setText("04");
                            }

                            else if (progress ==10){
                                mSeekArcSecondProgress.setText("05");
                            }

                            else if (progress ==12){
                                mSeekArcSecondProgress.setText("06");
                            }

                            else if (progress ==14){
                                mSeekArcSecondProgress.setText("07");
                            }

                            else if (progress ==16){
                                mSeekArcSecondProgress.setText("08");
                            }

                            else if (progress ==18){
                                mSeekArcSecondProgress.setText("09");
                            }
                            else if (progress ==20){
                                mSeekArcSecondProgress.setText("10");
                            }
                            else if (progress ==22){
                                mSeekArcSecondProgress.setText("11");
                            }

                            else if (progress ==24){
                                mSeekArcSecondProgress.setText("12");
                            }

                            else if (progress ==26){
                                mSeekArcSecondProgress.setText("13");
                            }

                            else if (progress ==28){
                                mSeekArcSecondProgress.setText("14");
                            }

                            else if (progress ==30){
                                mSeekArcSecondProgress.setText("15");
                            }

                            else if (progress ==32){
                                mSeekArcSecondProgress.setText("16");
                            }

                            else if (progress ==34){
                                mSeekArcSecondProgress.setText("17");
                            }

                            else if (progress ==36){
                                mSeekArcSecondProgress.setText("18");
                            }

                            else if (progress ==38){
                                mSeekArcSecondProgress.setText("19");
                            }

                            else if (progress ==40){
                                mSeekArcSecondProgress.setText("20");
                            }

                            else if (progress ==42){
                                mSeekArcSecondProgress.setText("21");
                            }

                            else if (progress ==44){
                                mSeekArcSecondProgress.setText("22");
                            }

                            else if (progress ==46){
                                mSeekArcSecondProgress.setText("23");
                            }

                            else if (progress ==48){
                                mSeekArcSecondProgress.setText("24");
                            }

                            else if (progress ==50){
                                mSeekArcSecondProgress.setText("25");
                            }

                            else if (progress ==52){
                                mSeekArcSecondProgress.setText("26");
                            }

                            else if (progress ==54){
                                mSeekArcSecondProgress.setText("27");
                            }

                            else if (progress ==56){
                                mSeekArcSecondProgress.setText("28");
                            }

                            else if (progress ==58){
                                mSeekArcSecondProgress.setText("29");
                            }

                            else if (progress ==60){
                                mSeekArcSecondProgress.setText("30");
                            }

                            else if (progress ==62){
                                mSeekArcSecondProgress.setText("31");
                            }

                            else if (progress ==64){
                                mSeekArcSecondProgress.setText("32");
                            }

                            else if (progress ==66){
                                mSeekArcSecondProgress.setText("33");
                            }

                            else if (progress ==68){
                                mSeekArcSecondProgress.setText("34");
                            }

                            else if (progress ==70){
                                mSeekArcSecondProgress.setText("35");
                            }

                            else if (progress ==72){
                                mSeekArcSecondProgress.setText("36");
                            }

                            else if (progress ==74){
                                mSeekArcSecondProgress.setText("37");
                            }

                            else if (progress ==76){
                                mSeekArcSecondProgress.setText("38");
                            }

                            else if (progress ==78){
                                mSeekArcSecondProgress.setText("39");
                            }

                            else if (progress ==80){
                                mSeekArcSecondProgress.setText("40");
                            }

                            else if (progress ==82){
                                mSeekArcSecondProgress.setText("41");
                            }

                            else if (progress ==84){
                                mSeekArcSecondProgress.setText("42");
                            }

                            else if (progress ==86){
                                mSeekArcSecondProgress.setText("43");
                            }

                            else if (progress ==88){
                                mSeekArcSecondProgress.setText("44");
                            }

                            else if (progress ==90){
                                mSeekArcSecondProgress.setText("45");
                            }

                            else if (progress ==92){
                                mSeekArcSecondProgress.setText("46");
                            }

                            else if (progress ==94){
                                mSeekArcSecondProgress.setText("47");
                            }

                            else if (progress ==96){
                                mSeekArcSecondProgress.setText("48");
                            }

                            else if (progress ==98){
                                mSeekArcSecondProgress.setText("49");
                            }

                            else if (progress ==100){
                                mSeekArcSecondProgress.setText("50");
                            }

                            else if (progress ==102){
                                mSeekArcSecondProgress.setText("51");
                            }

                            else if (progress ==104){
                                mSeekArcSecondProgress.setText("52");
                            }

                            else if (progress == 106){
                                mSeekArcSecondProgress.setText("53");
                            }

                            else if (progress ==108){
                                mSeekArcSecondProgress.setText("54");
                            }

                            else if (progress ==110){
                                mSeekArcSecondProgress.setText("55");
                            }

                            else if (progress ==112){
                                mSeekArcSecondProgress.setText("56");
                            }

                            else if (progress ==114){
                                mSeekArcSecondProgress.setText("57");
                            }

                            else if (progress ==116){
                                mSeekArcSecondProgress.setText("58");
                            }

                            else if (progress ==118){
                                mSeekArcSecondProgress.setText("59");
                            }

                            else if (progress ==120){
                                mSeekArcSecondProgress.setText("60");
                            }








                        }
                    });
                }

            }
        };

        findViewById(R.id.hourtext).setOnClickListener(clickListener);
        findViewById(R.id.minutetext).setOnClickListener(clickListener);
        findViewById(R.id.secondtext).setOnClickListener(clickListener);



/*//OnClickListener for the timer

        View.OnClickListener clickListener_time = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.hourtext){
                    //corresponding button logic should below here
                } else if (v.getId() == R.id.minutetext) {
                    //corresponding button logic should below here
                } else if (v.getId() == R.id.secondtext) {
                    //corresponding button logic should below here
                }
            }


    };

    findViewById(R.id.hourtext).setOnClickListener(clickListener_time);
    findViewById(R.id.minutetext).setOnClickListener(clickListener_time);
    findViewById(R.id.secondtext).setOnClickListener(clickListener_time);*/






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
/*    public void addListenerOnButton2() {

        final Context context = this;

        security_settings_button1 = (Button) findViewById(R.id.security_settings_button);

        security_settings_button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, SetupActivity.class);
                startActivity(intent);

            }

        });

    }*/
    // Make sure that you change the intent and class

/*    public void addListenerOnButton3() {

        final Context context = this;

        blacklist_whitelist_button1 = (Button) findViewById(R.id.blacklist_whitelist_button);

        blacklist_whitelist_button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BlockActivity.class);
                startActivity(intent);

            }

        });

    }*/





}


