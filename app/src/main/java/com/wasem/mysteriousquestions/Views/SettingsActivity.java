package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerLevel;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.MyService;
import com.wasem.mysteriousquestions.databinding.ActivitySettingsBinding;

import java.util.List;

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

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        viewModel.getAllPlayerQuestionInfo(LoginActivity.currentPlayerId).observe(this, new Observer<List<PlayerQuestion>>() {
            @Override
            public void onChanged(List<PlayerQuestion> playerQuestions) {
                for (PlayerQuestion player:playerQuestions) {
                    int id = player.id;
                    int playerId = player.playerId;
                    int playerPoint = player.playerPoints;
                    int correctAnswers = player.correctAnswers;
                    int wrongAnswers = player.wrongAnswers;
                    int skipTimes = player.skipTimes;
                    playerQuestion = new PlayerQuestion(id,playerId,playerPoint,skipTimes,correctAnswers,wrongAnswers);
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
                    FancyToast.makeText(getBaseContext(),"Please login again to Reset!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                viewModel.deletePlayerQuestion(playerQuestion);
                AppSharedPreferences.getInstance(getApplicationContext()).clearAll();
                FancyToast.makeText(getBaseContext(),"Game Progress Reset!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
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
                    FancyToast.makeText(getApplicationContext(),"Music is On!",Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    Intent intent = new Intent(getApplicationContext(),MyService.class);
                    startService(intent);
                }
                else {
                    AppSharedPreferences.getInstance(getApplicationContext()).musicStatusOff();
                    FancyToast.makeText(getApplicationContext(),"Music turned off!",Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    Intent intent = new Intent(getApplicationContext(),MyService.class);
                    stopService(intent);
                }
            }
        });
    }

    public boolean musicSettingsStatus() {

        if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("true")) {
            Intent intent = new Intent(getApplicationContext(),MyService.class);
            startService(intent);
            return true;
        }
        else if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("false")) {
            Intent intent = new Intent(getApplicationContext(),MyService.class);
            stopService(intent);
            return false;
        }
        return true;
    }

}