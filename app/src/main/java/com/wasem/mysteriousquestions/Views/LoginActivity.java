package com.wasem.mysteriousquestions.Views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.shashank.sony.fancytoastlib.FancyToast;

import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.MyService;
import com.wasem.mysteriousquestions.databinding.ActivityLoginBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Player player;
    PlayerViewModel playerViewModel;
    public static int currentPlayerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        rememberMeStatus();

        rememberMeClicked();

        backgroundMusic(binding.getRoot());


        playerViewModel.getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {

                binding.tvLoginOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user = binding.etLoginUsername.getText().toString();
                        String pass = binding.etLoginPassword.getText().toString();
                        for (int i = 0; i < players.size(); i++) {
                            int playerId = players.get(i).getPlayerId();
                            String username = players.get(i).getUsername();
                            String password = players.get(i).getPassword();
                            String email = players.get(i).getEmail();
                            String countryName = players.get(i).getCountryName();
                            String dateOfBirth = players.get(i).getDateOfBirth();
                            String gender = players.get(i).getGender();
                            player = new Player(playerId,username,email,password,dateOfBirth,countryName,gender);
                        }

                        if (player.getUsername().equals(user) && player.getPassword().equals(pass)) {
                            currentPlayerId = player.getPlayerId();
                            FancyToast.makeText(getBaseContext(), "Logged In Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            FancyToast.makeText(getBaseContext(), "Incorrect Email Or Password", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
            }
        });

        binding.tvCreateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                login_data.launch(intent);
            }
        });


    }


    public void backgroundMusic(View view){
            if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("true")) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
            }
            else if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("false")) {
                Intent intent = new Intent(getApplicationContext(),MyService.class);
                stopService(intent);
            }
        }

    //******************************************* ActivityResultLauncher ********************************************************************//


    ActivityResultLauncher<Intent> login_data = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==10) {
                        Intent intent = result.getData();
                        if (intent != null ) {
                            binding.etLoginUsername.setText(intent.getStringExtra("username"));
                            binding.etLoginPassword.setText(intent.getStringExtra("password"));
                        }
                    }

                }
            });


    //********************************************** Methods ********************************************************************//


    public void rememberMeStatus() {
        String checkbox = AppSharedPreferences.getInstance(getApplicationContext()).getRememberMePlayerStatus();

        if (checkbox.equals("true")) {
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        } else if (checkbox.equals("false")) {
            FancyToast.makeText(getBaseContext(), "Please SignIn!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
    }


    public void rememberMeClicked() {
        binding.chkBoxRememberME.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnChecked();
                    FancyToast.makeText(getBaseContext(), "Checked!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else if (!compoundButton.isChecked()) {
                    AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
                    FancyToast.makeText(getBaseContext(), "Unchecked!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                }
            }
        });
    }


}