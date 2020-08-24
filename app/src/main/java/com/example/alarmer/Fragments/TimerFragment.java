package com.example.alarmer.Fragments;


import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private EditText hoursEditText,minsEditText,secsEditText;
    private FloatingActionButton playButton,resetButton;
    private TextView timerTextView,stopRingtone;
    private ProgressBar progressBar;
    private boolean mTimerRunning;

    long timer=0,time2;
    private boolean pauseState;
    private int pauseTime;
    private CountDownTimer mCountDownTimer;
    Ringtone r;




    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        hoursEditText = v.findViewById(R.id.hoursEditText);
        minsEditText = v.findViewById(R.id.minsEditText);
        secsEditText = v.findViewById(R.id.secsEditText);
        stopRingtone = v.findViewById(R.id.stopRingtone);
        playButton = v.findViewById(R.id.floatingplayButton);
        resetButton = v.findViewById(R.id.floatingResetButton);
        resetButton.setVisibility(View.INVISIBLE);
        timerTextView = v.findViewById(R.id.timerTextView);
        progressBar=v.findViewById(R.id.progressBar);
        progressBar.animate().rotation(270.0f);
        Uri ringTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getActivity(), ringTone);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        stopRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r.isPlaying()){
                    r.stop();
                    stopRingtone.setVisibility(View.INVISIBLE);
                }
            }
        });

        updateCountDownText();



        return v;
    }

    public void resetTimer(){
        timer = 0;
        updateCountDownText();
        pauseState=false;
        progressBar.setProgress(100);
        resetButton.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.VISIBLE);
        hoursEditText.setVisibility(View.VISIBLE);
        minsEditText.setVisibility(View.VISIBLE);
        secsEditText.setVisibility(View.VISIBLE);
        hoursEditText.setText("00");
        minsEditText.setText("00");
        secsEditText.setText("00");

    }

    public void pauseTimer(){
        pauseState=true;
        pauseTime = (int) ((timer * 100) / time2);
        mCountDownTimer.cancel();
        mTimerRunning = false;
        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        resetButton.setVisibility(View.VISIBLE);
    }
    public void startTimer(){

        if(!pauseState) {



            int hours = Integer.parseInt(hoursEditText.getText().toString());
            int mins = Integer.parseInt(minsEditText.getText().toString());
            int secs = Integer.parseInt(secsEditText.getText().toString());


            timer = ((hours * 3600) + (mins * 60) + secs) * 1000;

        }
        if(timer==0){
            Toast.makeText(getActivity(), "Enter some Time", Toast.LENGTH_SHORT).show();
        }else {
            hoursEditText.setVisibility(View.INVISIBLE);
            minsEditText.setVisibility(View.INVISIBLE);
            secsEditText.setVisibility(View.INVISIBLE);
            time2=timer;
            mCountDownTimer = new CountDownTimer(timer, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer = millisUntilFinished;
                    updateCountDownText();
                    int progress=0;
                    if(!pauseState)
                    progress = (int) ((millisUntilFinished * 100) / (time2));
                    else
                        progress = (int) ((millisUntilFinished * pauseTime) / (time2));
                   progressBar.setProgress(progress);
                }

                @Override
                public void onFinish() {
                    mTimerRunning = false;


                    resetButton.setVisibility(View.VISIBLE);
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    progressBar.setProgress(0);
                    progressBar.setProgress(100);
                    hoursEditText.setVisibility(View.VISIBLE);
                    minsEditText.setVisibility(View.VISIBLE);
                    secsEditText.setVisibility(View.VISIBLE);
                    pauseState = false;

                    r.play();
                    stopRingtone.setVisibility(View.VISIBLE);

                }
            }.start();
            mTimerRunning = true;
            playButton.setImageResource(R.drawable.ic_pause_black_24dp);
            resetButton.setVisibility(View.INVISIBLE);
        }
    }
    public void updateCountDownText(){
        int hours = (int) (timer / 1000) / 3600;
        int minutes = (int) (timer / 1000) / 60;
        int seconds = (int) (timer / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft",timer);
        outState.putBoolean("timerRunning",mTimerRunning);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null) {
            timer = savedInstanceState.getLong("millisLeft");
            mTimerRunning = savedInstanceState.getBoolean("timerRunning");
            updateCountDownText();
        }
    }
}
