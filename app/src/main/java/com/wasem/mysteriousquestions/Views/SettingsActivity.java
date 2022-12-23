package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.MyService;
import com.wasem.mysteriousquestions.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("Settings", MODE_PRIVATE);
        edit = sp.edit();

        boolean soundOn = binding.switchSound.isOn();
        boolean soundOff = !binding.switchSound.isOn();

//        binding.switchSound.isOn()

        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}