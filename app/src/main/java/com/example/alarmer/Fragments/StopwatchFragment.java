package com.example.alarmer.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarmer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment {
    private TextView chronometer;
    private FloatingActionButton playButton,resetButton,flagButton;
    private boolean isResume;
    Handler handler;
    long tMilliSec,tStart,tBuff,tUpdate=0L;
    int sec,min,milliSec;
    LinearLayout container;
    ViewGroup finalContainer;

    public StopwatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        chronometer = v.findViewById(R.id.chronometer);
        playButton = v.findViewById(R.id.floatingplayButton);
        resetButton = v.findViewById(R.id.floatingResetButton);
        flagButton = v.findViewById(R.id.floatingflagButton);
        handler = new Handler();
        container = v.findViewById(R.id.container);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    //chronometer.start();
                    isResume=true;
                    resetButton.setVisibility(View.INVISIBLE);
                    playButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    flagButton.setVisibility(View.VISIBLE);

                }else{
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                  //  chronometer.stop();
                    isResume=false;
                    resetButton.setVisibility(View.VISIBLE);
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    flagButton.setVisibility(View.INVISIBLE);
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    tMilliSec = 0L;
                    tStart =0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec=0;
                    min=0;
                    milliSec=0;
                    chronometer.setText("00:00:000");
                    finalContainer.removeAllViews();
                }
            }
        });

         finalContainer = container;
        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater1 = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater1.inflate(R.layout.flags,null);
                TextView txtView = addView.findViewById(R.id.txtcontent);
                txtView.setText(chronometer.getText());
               if(txtView.getParent() != null) {
                    ((ViewGroup)txtView.getParent()).removeView(txtView); // <- fix
                }
                finalContainer.addView(txtView);

            }
        });

        return v;
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int)(tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int)(tUpdate%1000);
            chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%03d",milliSec));
            handler.postDelayed(this,60);

        }
    };
}
