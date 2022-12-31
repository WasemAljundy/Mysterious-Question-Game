package com.wasem.mysteriousquestions;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;

//    public static String CHANNEL_ID = "CHANNEL1";
//    public static String CHANNEL_NAME = "NotificationTest";

    public MusicService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_game_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return (startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    public Notification notificationSend() {
//        NotificationChannel channel = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
//                .setContentTitle("Wasem Aljundy")
//                .setContentText("Time Up")
//                .setSmallIcon(R.drawable.ic_baseline_add_reaction_24)
//                .setVibrate(new long[]{1000, 2000, 200})
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//        NotificationManagerCompat compat = NotificationManagerCompat.from(getBaseContext());
//        compat.notify(1, builder.build());
//        return builder.build();
//    }


}