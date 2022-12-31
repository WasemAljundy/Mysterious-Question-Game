package com.wasem.mysteriousquestions.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.MusicService;
import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        musicSettingsStatus();

        binding.btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPlayerStatus();
            }
        });

        binding.btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getBaseContext(), MusicService.class);
                stopService(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getBaseContext(), MusicService.class);
        stopService(intent);
    }

    //********************************************** Menu Options ************************************************************************************//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                Intent intent_logout = new Intent(getBaseContext(),LoginActivity.class);
                AppSharedPreferences.getInstance(getApplicationContext()).clearPlayerLevelScore();
                AppSharedPreferences.getInstance(getApplicationContext()).clearPlayerTotalScore();
                AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
                FancyToast.makeText(getBaseContext(),getString(R.string.logged_out_successfully),FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();
                finish();
                startActivity(intent_logout);
                return true;
        }
        return false;
    }


    public void musicSettingsStatus() {

        if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("true")) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            startService(intent);
        }
        else if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("false")) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            stopService(intent);
        }
    }


    private void checkPlayerStatus(){
        if (LoginActivity.currentPlayerId == 0) {
            AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
            FancyToast.makeText(getBaseContext(),getString(R.string.please_login_again_to_save_your_progress), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(getBaseContext(), LevelsActivity.class);
            startActivity(intent);
        }
    }


}