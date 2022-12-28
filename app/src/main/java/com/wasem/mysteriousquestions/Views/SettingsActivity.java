package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.MyService;
import com.wasem.mysteriousquestions.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        musicSettingsStatus();

        musicButtonListener();







        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ProfileActivity.class);
                startActivity(intent);
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
                    FancyToast.makeText(getApplicationContext(),"Music is On!",Toast.LENGTH_SHORT,FancyToast.WARNING,false);
                    Intent intent = new Intent(getApplicationContext(),MyService.class);
                    startService(intent);
                }
                else {
                    AppSharedPreferences.getInstance(getApplicationContext()).musicStatusOff();
                    FancyToast.makeText(getApplicationContext(),"Music turned off!",Toast.LENGTH_SHORT,FancyToast.WARNING,false);
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