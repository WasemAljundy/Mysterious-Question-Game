package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.ViewPager.DepthPageTransformer;
import com.wasem.mysteriousquestions.databinding.ActivityPlayerDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetailsActivity extends AppCompatActivity {
    ActivityPlayerDetailsBinding binding;
    PlayerViewModel viewModel;
    List<PlayerQuestion> playerProgress = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPlayerIdStatus();

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        viewModel.getAllPlayerQuestionProgress(LoginActivity.currentPlayerId).observe(this, new Observer<List<PlayerQuestion>>() {
            @Override
            public void onChanged(List<PlayerQuestion> playerQuestions) {
                playerProgress = playerQuestions;
                if (playerProgress.size() > 0) {
                    for (int i = 0; i < playerProgress.size(); i++) {
                        int playerPoints = playerProgress.get(i).playerPoints;
                        binding.tvTotalPoints.setText(String.valueOf(playerPoints));
                        int skipTimes = playerProgress.get(i).skipTimes;
                        binding.tvSkipTimes.setText(String.valueOf(skipTimes));
                        int correctAnswers = playerProgress.get(i).correctAnswers;
                        binding.tvCorrectAnswers.setText(String.valueOf(correctAnswers));
                        int wrongAnswers = playerProgress.get(i).wrongAnswers;
                        binding.tvWrongAnswers.setText(String.valueOf(wrongAnswers));
                    }
                }
                else
                    Toast.makeText(PlayerDetailsActivity.this, "No gameplay history!", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void checkPlayerIdStatus() {
        if (LoginActivity.currentPlayerId == 0) {
            AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
            FancyToast.makeText(getBaseContext(), "Please login again to Update!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


}