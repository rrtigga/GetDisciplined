package com.spicycurryman.getdisciplined10.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
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

    //will show the time
    private TextView number_text;

    private TextView minute_text;
    private TextView second_text;


    private TextView little_hour_text;
    private TextView little_minute_text;
    private TextView little_second_text;

    private TextView little_hour_text2;
    private TextView little_minute_text2;
    private TextView little_second_text2;

    CountDownTimer countDownTimer;          // built in android class CountDownTimer
    long totalTimeCountInMilliseconds;
    long timeBlinkInMilliseconds;           // start time of start blinking
    boolean blink;


    // Consider showing drawable scrubber after pressing H M or S




    Button block_button1;
    Button start_timer;

    int hourint, minuteint,secondint;

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
        setContentView(R.layout.merge);


        //set views

        start_timer = (Button) findViewById(R.id.start_button);
        number_text = (TextView) findViewById(R.id.hour_progress_number);
        minute_text = (TextView) findViewById(R.id.minute_progress_number);
        second_text = (TextView) findViewById(R.id.second_progress_number);


        //getReferenceOfViews ();                         // get all views
        setActionListeners ();

        // This determine what the actual "countdown" time will be.
        //totalTimeCountInMilliseconds = 60 * 1000;      // time count for 3 minutes = 180 seconds
        //timeBlinkInMilliseconds = 30 * 1000;






        //Make sure you find out why it appears after a whole 1 second after the app appears
        SpannableString s = new SpannableString("GetDisciplined");
        s.setSpan(new TypefaceSpan(this, "roboto-lightitalic.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);
        // set the action bar in this activity as the home
        actionBar.setHomeButtonEnabled(true);




        //Listeners for the buttons
        addListenerOnButton();



        //Editing Button Text

        block_button_text = (Button)findViewById(R.id.block_button);
        block_button_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-bold.ttf"));

        start_timer_text = (Button)findViewById(R.id.start_button);
        start_timer_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/robotocondensed-bold.ttf"));

        number_text = (TextView)findViewById(R.id.hour_progress_number);
        number_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        minute_text = (TextView)findViewById(R.id.minute_progress_number);
        minute_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        second_text = (TextView)findViewById(R.id.second_progress_number);
        second_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_hour_text = (TextView)findViewById(R.id.hourtext);
        little_hour_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_minute_text = (TextView)findViewById(R.id.minutetext);
        little_minute_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_second_text = (TextView)findViewById(R.id.secondtext);
        little_second_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_hour_text2 = (TextView)findViewById(R.id.little_hour_text2);
        little_hour_text2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_minute_text2 = (TextView)findViewById(R.id.little_minute_text2);
        little_minute_text2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));

        little_second_text2 = (TextView)findViewById(R.id.little_second_text2);
        little_second_text2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto-light.ttf"));




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
                    curText.setTextColor(getResources().getColor(R.color.black));
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

                            int progress_count = 0;

                            for (int i=0;i<24;i=i+1)
                            {

                                if (progress ==120) {
                                    mSeekArcProgress.setText("24");

                                }
                                else if (progress == progress_count)
                                {
                                    mSeekArcProgress.setText(String.valueOf(String.format("%02d",i)));
                                }

                                progress_count = progress_count + 5;

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
                            int progress_count = 0;

                            for (int i=0;i<120;i++)
                            {

                                 if (progress ==120) {
                                     mSeekArcMinuteProgress.setText("00");

                                 }
                                else if (progress == progress_count)
                                {
                                    mSeekArcMinuteProgress.setText(String.valueOf(String.format("%02d",i)));
                                }

                                progress_count = progress_count + 2;


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



                            // so ur setting it to HALF of what "progress" is = to

                            int progress_count = 0;

                            for (int i=0;i<60;i++)
                            {


                                if (progress ==120) {
                                    mSeekArcSecondProgress.setText("00");

                                }
                                else if (progress == progress_count)
                                {
                                    mSeekArcSecondProgress.setText(String.valueOf(String.format("%02d",i)));
                                }

                                progress_count = progress_count + 2;


                            }

                        }
                    });
                }

            }
        };
        findViewById(R.id.hourtext).setOnClickListener(clickListener);
        findViewById(R.id.minutetext).setOnClickListener(clickListener);
        findViewById(R.id.secondtext).setOnClickListener(clickListener);


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
                mSeekArc.setTouchInSide(false);
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


    private void setActionListeners() {

        number_text = (TextView) findViewById(R.id.hour_progress_number);
        minute_text = (TextView) findViewById(R.id.minute_progress_number);
        second_text = (TextView) findViewById(R.id.second_progress_number);




        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // textViewShowTime.setTextAppearance(getApplicationContext(), R.style.normalText);

                hourint = Integer.valueOf(number_text.getText().toString());

                minuteint = Integer.valueOf(minute_text.getText().toString());

                secondint = Integer.valueOf(second_text.getText().toString());

                Log.i("YourActivity", "Hours: " + hourint);

                Log.i("YourActivity", "Minutes: " + minuteint);

                Log.i("YourActivity", "Seconds: " + secondint);

                totalTimeCountInMilliseconds = ((hourint*60*60) +(minuteint*60) + (secondint)) * 1000;      // time count for 3 minutes = 180 seconds
                timeBlinkInMilliseconds = 30*1000;

                countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
                    // 500 means, onTick function will be called at every 500 milliseconds

                    @Override
                    public void onTick(long leftTimeInMilliseconds) {
                        long seconds = leftTimeInMilliseconds / 1000;
                        mSeekArc.setVisibility(View.INVISIBLE);


                        if ( leftTimeInMilliseconds < timeBlinkInMilliseconds ) {
                           // textViewShowTime.setTextAppearance(getApplicationContext(), R.style.blinkText);
                            // change the style of the textview .. giving a red alert style

                            if ( blink ) {
                                number_text.setVisibility(View.VISIBLE);
                                minute_text.setVisibility(View.VISIBLE);
                                second_text.setVisibility(View.VISIBLE);


                                // if blink is true, textview will be visible
                            } else {
                                number_text.setVisibility(View.INVISIBLE);
                                minute_text.setVisibility(View.INVISIBLE);
                                second_text.setVisibility(View.INVISIBLE);


                            }

                            blink = !blink;         // toggle the value of blink
                        }

                        second_text.setText(String.format("%02d", seconds % 60));
                        minute_text.setText(String.format("%02d", (seconds / 60) % 60));
                        number_text.setText(String.format("%02d", seconds / 3600));                     // format the textview to show the easily readable format
                    }


                    @Override
                    public void onFinish() {
                        // this function will be called when the timecount is finished
                        //textViewShowTime.setText("Time up!");
                        number_text.setVisibility(View.VISIBLE);
                        minute_text.setVisibility(View.VISIBLE);
                        second_text.setVisibility(View.VISIBLE);
                        mSeekArc.setVisibility(View.VISIBLE);
                    }

                }.start();

            }
        });
    }
    private void getReferenceOfViews() {

        start_timer = (Button) findViewById(R.id.start_button);
        number_text = (TextView) findViewById(R.id.hour_progress_number);
        minute_text = (TextView) findViewById(R.id.minute_progress_number);
        second_text = (TextView) findViewById(R.id.second_progress_number);


    }

}