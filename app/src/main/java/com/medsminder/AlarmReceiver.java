package com.medsminder;

import static com.medsminder.MainActivity.CHANNEL_ID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mMediaPlayer;

    @Override
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm will ring shortly", Toast.LENGTH_LONG).show();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("MedsMinder")
                .setContentText("please eat the medicine")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(212, builder.build());

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

        mMediaPlayer = MediaPlayer.create(context, R.raw.alarm_tone);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();


    }


}
