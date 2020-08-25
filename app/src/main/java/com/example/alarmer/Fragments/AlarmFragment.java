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


   public static TextView timeTextView,alarm2,alarm3;


    private static Calendar calendar;
    SharedPreferences sharedPreferences,pref2,pref3;
    public static Button cancelButton,cancelButton2,cancelButton3;



    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarm, container, false);

        timeTextView = v.findViewById(R.id.alarmTime1);
        alarm2 = v.findViewById(R.id.alarmTime2);
        alarm3 = v.findViewById(R.id.alarmTime3);
        cancelButton3 = v.findViewById(R.id.cancelButton3);
        cancelButton2 = v.findViewById(R.id.cancelButton2);
        cancelButton = v.findViewById(R.id.cancelButton);

        sharedPreferences = getActivity().getSharedPreferences("Alarm",Context.MODE_PRIVATE);
        pref2 = getActivity().getSharedPreferences("Alarm2",Context.MODE_PRIVATE);
        pref3 = getActivity().getSharedPreferences("Alarm3",Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("status",99)==1){
            cancelButton.setVisibility(View.VISIBLE);
        }
        if(pref2.getInt("status2",99)==1){
            cancelButton2.setVisibility(View.VISIBLE);
        }
        if(pref3.getInt("status3",99)==1){
            cancelButton3.setVisibility(View.VISIBLE);
        }

        calendar = Calendar.getInstance();
        if(sharedPreferences.getInt("hour1",0) == 0 && sharedPreferences.getInt("min1",0) == 0){
            timeTextView.setText("Add Alarm1");
        }else {
            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, sharedPreferences.getInt("hour1", 0), sharedPreferences.getInt("min1", 0));
            if (calendar != null)
                timeTextView.setText(DateFormat.format("hh:mm aa", calendar));
        }
        if(pref2.getInt("hour2",0) == 0 && sharedPreferences.getInt("min2",0) == 0){
            alarm2.setText("Add Alarm2");
        }else {
            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, pref2.getInt("hour2", 0), pref2.getInt("min2", 0));
            if (calendar != null)
                alarm2.setText(DateFormat.format("hh:mm aa", calendar));
        }
        if(pref3.getInt("hour3",0) == 0 && sharedPreferences.getInt("min3",0) == 0){
            alarm3.setText("Add Alarm3");
        }else {
            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, pref3.getInt("hour3", 0), pref3.getInt("min3", 0));
            if (calendar != null)
                alarm3.setText(DateFormat.format("hh:mm aa", calendar));
        }
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
                intent.putExtra("alarmNo",1);
                startActivity(intent);
            }
        });
        alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
                intent.putExtra("alarmNo",2);
                startActivity(intent);
            }
        });
        alarm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
                intent.putExtra("alarmNo",3);
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
                timeTextView.setText("Add Alarm1");
                sharedPreferences.edit().putInt("hour1",0).apply();
                sharedPreferences.edit().putInt("min1",0).apply();
                sharedPreferences.edit().putInt("status",0).apply();
            }
        });
        cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=8;i<=14;i++){
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(),AlertReciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),i,intent,0);
                    assert alarmManager != null;
                    alarmManager.cancel(pendingIntent);
                }
                cancelButton2.setVisibility(View.INVISIBLE);
                alarm2.setText("Add Alarm2");
                pref2.edit().putInt("hour2",0).apply();
                pref2.edit().putInt("min2",0).apply();
                pref2.edit().putInt("status2",0).apply();
            }
        });
        cancelButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=15;i<=21;i++){
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(),AlertReciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),i,intent,0);
                    assert alarmManager != null;
                    alarmManager.cancel(pendingIntent);
                }
                cancelButton3.setVisibility(View.INVISIBLE);
                alarm3.setText("Add Alarm3");
                pref3.edit().putInt("hour3",0).apply();
                pref3.edit().putInt("min3",0).apply();
                pref3.edit().putInt("status3",0).apply();
            }
        });





        return v;
    }






}
