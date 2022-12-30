package com.wasem.mysteriousquestions;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyJobService extends JobService {
    public static String CHANNEL_ID = "24-HOURS Reminder";
    public static String CHANNEL_NAME = "Mysterious Questions";
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show();
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_NAME)
                .setContentTitle("There is some Question need answers 👀")
                .setContentText("We haven't seen you in a DAY! , Comeback and solve more Question ✨")
                .setSmallIcon(R.drawable.img_logo)
                .setVibrate(new long[]{1000, 2000, 200})
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat compat = NotificationManagerCompat.from(getBaseContext());
        compat.notify(1, builder.build());
        jobFinished(jobParameters,true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}


