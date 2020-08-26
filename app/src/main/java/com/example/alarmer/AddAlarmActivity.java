package com.example.alarmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarmer.Fragments.AlarmFragment;
import com.example.alarmer.Fragments.TimePickerFragment;

import java.util.Calendar;


public class AddAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    String[] ringtones = {"Default Ringtone","Extreme Alarm","Let me love U","LoveStory","Moonlight Sonata","See You Again","Swing Jazz","Tomorrowland"};
    TextClock textClock;
    Button setAlarmButton,testRingtoneButton;

    EditText labelEditText;
    MediaPlayer mediaPlayer;
    int[] requestCodes;
    String label;
    Spinner spin;
    CheckBox sun,mon,tue,wed,thu,fri,sat;
    int ringtone;
    int playing =0;
    Switch aSwitch;
    int intentwhich=0;
    int alarmNo;

    SharedPreferences sharedPreferences,pref2,pref3;
    SharedPreferences.Editor editor,editor2,editor3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        alarmNo = getIntent().getIntExtra("alarmNo",0);
        if(alarmNo == 1){
            requestCodes = new int[]{1, 2, 3, 4, 5, 6, 7};
        }else if(alarmNo ==2 ){
            requestCodes = new int[]{8, 9, 10, 11, 12, 13, 14};
        }else{
            requestCodes = new int[]{15, 16, 17, 18, 19, 20, 21};
        }

        setAlarmButton = findViewById(R.id.setAlarmButton);
        textClock= findViewById(R.id.textClock);
        aSwitch = findViewById(R.id.questionSwitch);
        labelEditText = findViewById(R.id.labelEditText);
        testRingtoneButton=findViewById(R.id.testRingtone);
        checkBox();

        sharedPreferences = this.getSharedPreferences("Alarm",Context.MODE_PRIVATE);
        pref2 = this.getSharedPreferences("Alarm2",MODE_PRIVATE);
        pref3 = this.getSharedPreferences("Alarm3",MODE_PRIVATE);
        spin = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,ringtones);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spin.setAdapter(arrayAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        ringtone = 0;
                        break;
                    case 1:
                        ringtone = 1;

                        break;
                    case 2:
                        ringtone = 2;

                        break;
                    case 3:
                        ringtone = 3;

                        break;
                    case 4:
                        ringtone = 4;

                        break;
                    case 5:
                        ringtone = 5;

                        break;
                    case 6:
                        ringtone = 6;

                        break;
                    case 7:
                        ringtone = 7;

                        break;
                    default:
                        ringtone = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            mediaPlayer.stop();
            }
        });
        testRingtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing==0){
                    switch (ringtone){
                        case 0:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, Settings.System.DEFAULT_RINGTONE_URI);
                            break;
                        case 1:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.extremealarm);
                            break;
                        case 2:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.let_me_love_you);
                            break;
                        case 3:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.lovestory);
                            break;
                        case 4:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.moonlightsonata);
                            break;
                        case 5:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.seeyouagain);
                            break;
                        case 6:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.swingjazz);
                            break;
                        case 7:
                            mediaPlayer= MediaPlayer.create(AddAlarmActivity.this, R.raw.tomorrowland);
                            break;

                    }
                    mediaPlayer.start();
                    playing=1;
                    testRingtoneButton.setText("Stop");
                }else{
                    mediaPlayer.stop();
                    playing=0;
                    testRingtoneButton.setText("Test Ringtone");
                }
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    intentwhich=1;
                }else{
                    intentwhich=0;
                }
            }
        });
        label = labelEditText.getText().toString();




        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sun.isChecked()&&!mon.isChecked()&&!tue.isChecked()&&!wed.isChecked()&&!thu.isChecked()&&!fri.isChecked()&&!sat.isChecked()){
                    Toast.makeText(AddAlarmActivity.this, "Check at least one day", Toast.LENGTH_SHORT).show();
                }else {

                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }

            }
        });
    }

    private void checkBox() {
        sun = findViewById(R.id.sunCheck);
        mon = findViewById(R.id.monCheck);
        tue = findViewById(R.id.tueCheck);
        wed = findViewById(R.id.wedCheck);
        thu = findViewById(R.id.thuCheck);
        fri = findViewById(R.id.friCheck);
        sat = findViewById(R.id.satCheck);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        textClock.setText(DateFormat.format("hh:mm aa",c));
        checkAlarm(hourOfDay,minute);

        if(alarmNo==1) {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.putInt("hour1", hourOfDay);
            editor.putInt("min1", minute);
            editor.putString("label", label);
            editor.putInt("ringtone", ringtone);
            editor.putInt("status", 1);
            editor.apply();
            AlarmFragment.timeTextView.setText(DateFormat.format("hh:mm aa",c));
            AlarmFragment.cancelButton.setVisibility(View.VISIBLE);
        }else if (alarmNo==2){
            editor2 = pref2.edit();
            editor2.clear();
            editor2.putInt("hour2", hourOfDay);
            editor2.putInt("min2", minute);
            editor2.putString("label2", label);
            editor2.putInt("ringtone", ringtone);
            editor2.putInt("status2", 1);
            editor2.apply();
            AlarmFragment.alarm2.setText(DateFormat.format("hh:mm aa",c));
            AlarmFragment.cancelButton2.setVisibility(View.VISIBLE);
        }else if(alarmNo==3){
            editor3 = pref3.edit();
            editor3.clear();
            editor3.putInt("hour3", hourOfDay);
            editor3.putInt("min3", minute);
            editor3.putString("label3", label);
            editor3.putInt("ringtone", ringtone);
            editor3.putInt("status3", 1);
            editor3.apply();
            AlarmFragment.alarm3.setText(DateFormat.format("hh:mm aa",c));
            AlarmFragment.cancelButton3.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this, "Scheduled Successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }



    public void setAlarm(Calendar c,int requestcode){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReciever.class);
        intent.putExtra("alarmlabel",label);
        Log.i("label",label);
        intent.putExtra("ringtone",ringtone);
        intent.putExtra("intentwhich",intentwhich);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,requestcode,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), pendingIntent);


    }


    public void checkAlarm(int hourOfDay,int minute){
        if(sun.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());
            }

            setAlarm(c,requestCodes[0]);
        }
        if(mon.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());
            }

            setAlarm(c,requestCodes[1]);
        }
        if(tue.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());
            }

            setAlarm(c,requestCodes[2]);
        }
        if(wed.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());

            }

            setAlarm(c,requestCodes[3]);
        }
        if(thu.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());
            }

            setAlarm(c,requestCodes[4]);
        }
        if(fri.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());
            }

            setAlarm(c,requestCodes[5]);
        }
        if(sat.isChecked()){


            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            c.set(Calendar.SECOND,0);
            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,7);
                Log.i("time",c.getTime().toString());
            }

            setAlarm(c,requestCodes[6]);
        }

    }

}
