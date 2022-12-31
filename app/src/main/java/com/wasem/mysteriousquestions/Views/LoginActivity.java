package com.wasem.mysteriousquestions.Views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.shashank.sony.fancytoastlib.FancyToast;

import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.MusicService;
import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.databinding.ActivityLoginBinding;

import java.util.List;

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
                            FancyToast.makeText(getBaseContext(), getString(R.string.logged_in_successfully), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            FancyToast.makeText(getBaseContext(), getString(R.string.incorrect_email_or_password), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
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
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        startService(intent);
            if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("true")) {
                startService(intent);
            }
            else if (AppSharedPreferences.getInstance(this).getMusicStatus().equals("false")) {
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
            FancyToast.makeText(getBaseContext(), getString(R.string.please_sign_in), FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
    }


    public void rememberMeClicked() {
        binding.chkBoxRememberME.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnChecked();
                    FancyToast.makeText(getBaseContext(), getString(R.string.checked), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else if (!compoundButton.isChecked()) {
                    AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
                    FancyToast.makeText(getBaseContext(), getString(R.string.unChecked), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                }
            }
        });
    }


}