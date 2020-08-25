package com.example.alarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class StopMathsAlarmActivity extends AppCompatActivity {

    TextView question;
    EditText answer;
    Button stopAlarm;
    int num1,num2,num3,ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_maths_alarm);
        question= findViewById(R.id.mathsQuestion);
        answer = findViewById(R.id.mathsQuestionAnswer);
        stopAlarm = findViewById(R.id.stopAlarm);
        Random random = new Random();
        num1 = random.nextInt(20);
        num2 = random.nextInt(20);
        num3 = random.nextInt(100);
         ans = (num1*num2)+ num3;
         question.setText("Solve: "+num1 +" * "+num2+" + "+num3 );
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans1 = answer.getText().toString();
                if(TextUtils.isEmpty(ans1)){
                    Toast.makeText(StopMathsAlarmActivity.this, "Enter ans", Toast.LENGTH_SHORT).show();
                }else{
                    if(Integer.parseInt(ans1) == ans){
                        AlertReciever.mediaPlayer.stop();
                        Intent intent1 = new Intent(StopMathsAlarmActivity.this,AlarmService.class);
                        stopService(intent1);
                        finish();
                        System.exit(0);
                    }
                }
            }
        });

    }
}
