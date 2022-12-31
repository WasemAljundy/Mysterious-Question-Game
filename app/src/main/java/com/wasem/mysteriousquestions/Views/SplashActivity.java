package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.NotificationJobService;
import com.wasem.mysteriousquestions.databinding.ActivitySplashBinding;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        YoYo.with(Techniques.RotateIn).duration(1500).playOn(binding.splashLogo);
        YoYo.with(Techniques.RubberBand).duration(2000).playOn(binding.splashName);

        notificationSettingsStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        controlSplashActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    private void controlSplashActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        },2000);
    }


    public boolean notificationSettingsStatus() {

        if (AppSharedPreferences.getInstance(this).getNotificationStatus().equals("true")) {
            ComponentName componentName = new ComponentName(getApplicationContext(), NotificationJobService.class);
            JobInfo jobInfo;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                jobInfo = new JobInfo.Builder(1,componentName)
                        .setPeriodic(TimeUnit.HOURS.toMillis(24),TimeUnit.MINUTES.toMillis(15))
                        .setPersisted(true)
                        .build();
            }
            else {
                jobInfo = new JobInfo.Builder(1,componentName)
                        .setMinimumLatency(TimeUnit.HOURS.toMillis(24))
                        .setPersisted(true)
                        .build();
            }
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.schedule(jobInfo);
            return true;
        }
        else if (AppSharedPreferences.getInstance(this).getNotificationStatus().equals("false")) {
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(1);
            return false;
        }
        return true;
    }


}