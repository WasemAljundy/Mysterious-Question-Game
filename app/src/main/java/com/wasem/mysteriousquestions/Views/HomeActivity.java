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
import com.wasem.mysteriousquestions.MyService;
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


        binding.btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LevelsActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(getBaseContext(), MyService.class);
                stopService(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getBaseContext(), MyService.class);
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
                sp = getSharedPreferences("Player",MODE_PRIVATE);
                edit = sp.edit();
                edit.putString("remember","false");
                edit.apply();
                FancyToast.makeText(getBaseContext(),"Logged Out Successfully!",FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();
                finish();
                startActivity(intent_logout);
                return true;
        }
        return false;
    }





}