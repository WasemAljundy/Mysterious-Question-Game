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

import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.MyService;
import com.wasem.mysteriousquestions.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    PlayerViewModel playerViewModel;
    public static int currentPlayerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        sp = getSharedPreferences("Player", MODE_PRIVATE);
        edit = sp.edit();

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
                        for (Player p : players) {
                            if (p.getUsername().equals(user) && p.getPassword().equals(pass)) {
                                sp = getSharedPreferences("Player", MODE_PRIVATE);
                                edit = sp.edit();
                                edit.putString("username", user.trim());
                                edit.putString("password", pass.trim());
                                edit.apply();
                                currentPlayerId = p.getPlayerId();
                                FancyToast.makeText(getBaseContext(), "Logged In Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else
                                FancyToast.makeText(getBaseContext(), "Incorrect Email Or Password", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
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
        Intent intent = new Intent(getBaseContext(), MyService.class);
        startService(intent);
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
        String checkbox = sp.getString("remember", "");

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
                    sp = getSharedPreferences("Player", MODE_PRIVATE);
                    edit = sp.edit();
                    edit.putString("remember", "true");
                    edit.apply();
                    FancyToast.makeText(getBaseContext(), "Checked!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else if (!compoundButton.isChecked()) {
                    sp = getSharedPreferences("Player", MODE_PRIVATE);
                    edit = sp.edit();
                    edit.putString("remember", "false");
                    edit.apply();
                    FancyToast.makeText(getBaseContext(), "Unchecked!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                }
            }
        });
    }


}