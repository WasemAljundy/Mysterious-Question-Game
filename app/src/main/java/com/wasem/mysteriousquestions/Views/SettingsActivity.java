package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.NotificationJobService;
import com.wasem.mysteriousquestions.MusicService;
import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.databinding.ActivitySettingsBinding;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    PlayerViewModel viewModel;
    PlayerQuestion playerQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        musicSettingsStatus();

        musicButtonListener();

        notificationSettingsStatus();

        notificationButtonListener();

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);


        viewModel.getAllPlayerQuestionInfo(LoginActivity.currentPlayerId).observe(this, new Observer<List<PlayerQuestion>>() {
            @Override
            public void onChanged(List<PlayerQuestion> playerQuestions) {
                for (int i = 0; i < playerQuestions.size(); i++) {
                    playerQuestion = new PlayerQuestion(playerQuestions.get(i).id,playerQuestions.get(i).playerId,
                            playerQuestions.get(i).skipTimes,playerQuestions.get(i).correctAnswers,playerQuestions.get(i).wrongAnswers);
                }
            }
        });


        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        binding.btnPlayerProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PlayerDetailsActivity.class);
                startActivity(intent);
            }
        });

        binding.btnResetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.currentPlayerId == 0) {
                    AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
                    FancyToast.makeText(getBaseContext(),getString(R.string.please_login_again_to_reset), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                viewModel.deletePlayerQuestion(playerQuestion);
                AppSharedPreferences.getInstance(getApplicationContext()).clearAll();
                FancyToast.makeText(getBaseContext(),getString(R.string.game_progress_reset), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });




    }

    public void musicButtonListener(){
        binding.switchSound.setOn(musicSettingsStatus());
        binding.switchSound.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (toggleableView.isOn()) {
                    AppSharedPreferences.getInstance(getApplicationContext()).musicStatusOn();
                    FancyToast.makeText(getApplicationContext(),getString(R.string.music_is_on),Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    Intent intent = new Intent(getApplicationContext(), MusicService.class);
                    startService(intent);
                }
                else {
                    AppSharedPreferences.getInstance(getApplicationContext()).musicStatusOff();
                    FancyToast.makeText(getApplicationContext(),getString(R.string.music_is_off),Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    Intent intent = new Intent(getApplicationContext(), MusicService.class);
                    stopService(intent);
                }
            }
        });
    }

    public boolean musicSettingsStatus() {

        if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("true")) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            startService(intent);
            return true;
        }
        else if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("false")) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            stopService(intent);
            return false;
        }
        return true;
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


    public void notificationButtonListener(){
        binding.switchNotification.setOn(notificationSettingsStatus());
        binding.switchSound.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (toggleableView.isOn()) {
                    FancyToast.makeText(getApplicationContext(),getString(R.string.notification_is_on),Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    AppSharedPreferences.getInstance(getApplicationContext()).notificationStatusOn();
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
                }
                else {
                    FancyToast.makeText(getApplicationContext(),getString(R.string.notification_turned_off),Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    AppSharedPreferences.getInstance(getApplicationContext()).notificationStatusOff();
                    JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    scheduler.cancel(1);
                }
            }
        });
    }



}