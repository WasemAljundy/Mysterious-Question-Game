package com.wasem.mysteriousquestions;

import static com.wasem.mysteriousquestions.R.string.there_is_some_question_need_answers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.wasem.mysteriousquestions.Views.SplashActivity;

public class NotificationJobService extends JobService {
    public static String CHANNEL_ID = "24-HOURS Reminder";
    public static String CHANNEL_NAME = "Mysterious Questions";
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        getNotification();
        jobFinished(jobParameters,true);
        return false;
    }

    public void getNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(getBaseContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationJobService.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationJobService.this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.img_logo);
        builder.setContentTitle(getString(there_is_some_question_need_answers));
        builder.setContentText(getString(R.string.notification_comeback_push));
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.addAction(R.drawable.img_logo,"PLAY NOW!",pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationJobService.this);
        managerCompat.notify(1, builder.build());
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}


