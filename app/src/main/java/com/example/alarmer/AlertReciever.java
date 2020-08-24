package com.example.alarmer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    public static MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        int ringtone  = intent.getIntExtra("ringtone",0);
        switch (ringtone){
            case 0:
                mediaPlayer= MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
                break;
            case 1:
                mediaPlayer= MediaPlayer.create(context, R.raw.extremealarm);
                break;
            case 2:
                mediaPlayer= MediaPlayer.create(context, R.raw.let_me_love_you);
                break;
            case 3:
                mediaPlayer= MediaPlayer.create(context, R.raw.lovestory);
                break;
            case 4:
                mediaPlayer= MediaPlayer.create(context, R.raw.moonlightsonata);
                break;
            case 5:
                mediaPlayer= MediaPlayer.create(context, R.raw.seeyouagain);
                break;
            case 6:
                mediaPlayer= MediaPlayer.create(context, R.raw.swingjazz);
                break;
            case 7:
                mediaPlayer= MediaPlayer.create(context, R.raw.tomorrowland);
                break;
            default:
                mediaPlayer= MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        }

        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        //Toast.makeText(context, "Recieved", Toast.LENGTH_SHORT).show();
        Log.i("currenttime",Long.toString(System.currentTimeMillis()));

        String label = intent.getStringExtra("label");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotifiaction("Alarmer",label);
        notificationHelper.getManager().notify(1,nb.build());

    }
}
