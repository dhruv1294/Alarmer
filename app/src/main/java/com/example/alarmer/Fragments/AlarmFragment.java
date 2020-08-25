package com.example.alarmer.Fragments;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Switch;

import android.widget.TextView;


import com.example.alarmer.AddAlarmActivity;
import com.example.alarmer.AlertReciever;
import com.example.alarmer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;



/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment  {


   private static TextView timeTextView;
    FloatingActionButton floater;
    int hour=0 , min=0;
    private static Calendar calendar;
    SharedPreferences sharedPreferences;
    public static Button cancelButton;
    AddAlarmActivity addAlarmActivity;


    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarm, container, false);
        floater = v.findViewById(R.id.floatingActionButton);
        timeTextView = v.findViewById(R.id.alarmTime1);
        cancelButton = v.findViewById(R.id.cancelButton);

        sharedPreferences = getActivity().getSharedPreferences("Alarm",Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("status",99)==1){
            cancelButton.setVisibility(View.VISIBLE);
        }
        calendar = Calendar.getInstance();
        if(sharedPreferences.getInt("hour1",0) == 0 && sharedPreferences.getInt("min1",0) == 0){
            timeTextView.setText("Add Alarm");
        }else {
            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, sharedPreferences.getInt("hour1", 0), sharedPreferences.getInt("min1", 0));
            if (calendar != null)
                timeTextView.setText(DateFormat.format("hh:mm aa", calendar));
        }
        floater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=1;i<=7;i++){
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(),AlertReciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),i,intent,0);
                    assert alarmManager != null;
                    alarmManager.cancel(pendingIntent);
                }
                cancelButton.setVisibility(View.INVISIBLE);
                timeTextView.setText("Add Alarm");
                sharedPreferences.edit().putInt("hour1",0).apply();
                sharedPreferences.edit().putInt("min1",0).apply();
                sharedPreferences.edit().putInt("status",0).apply();
            }
        });



        return v;
    }
    public void setTimeTextView(int hour,int min){

        calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,hour,min);
        Log.i("Time",Integer.toString(hour)+":"+Integer.toString(min));
        timeTextView.setText(DateFormat.format("hh:mm aa",calendar));


    }





}
