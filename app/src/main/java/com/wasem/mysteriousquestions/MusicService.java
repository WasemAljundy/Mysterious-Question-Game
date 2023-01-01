package com.wasem.mysteriousquestions;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;

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

}