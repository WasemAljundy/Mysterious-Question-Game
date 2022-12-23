package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.DataBase.Listeners.InsertListener;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.databinding.ActivityRegisterBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    PlayerViewModel playerViewModel;
    Player player = new Player();
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        sp = getSharedPreferences("Player", MODE_PRIVATE);
        edit = sp.edit();


        binding.tvBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                binding.tvBirthDate.setText(date);
                                player.setDateOfBirth(date);
//                                age = now.get(Calendar.YEAR) - year;
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getSupportFragmentManager(), "DatePickerDialog");
            }

        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userValid = usernameValidation();
                boolean passValid = passwordValidation();
                boolean emailValid = emailValidation();
                gender_Country();

                if (userValid && passValid && emailValid) {

                    playerViewModel.insertPlayer(player, new InsertListener() {
                        @Override
                        public void onInsertListener(Long itemId) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.putExtra("username", player.getUsername());
                            intent.putExtra("password", player.getPassword());
                            setResult(10, intent);
                            Log.d("PlayerId", "onInsertListener: "+itemId);
                            finish();
                        }
                    });
                }
            }
        });


    }


    //********************************************** Validation Methods ************************************************************************************//


    boolean passwordValidation() {
        String password = binding.etRegisterPassword.getText().toString().trim();
        String confirm_password = binding.etRegisterConfirmPassword.getText().toString().trim();
        if (password.isEmpty()) {
            binding.etRegisterPassword.setError("Don't Leave it Empty!");
            return false;
        } else if (!(password.length() > 7 && password.length() < 16)) {
            binding.etRegisterPassword.setError("Password Should be at least 8 Characters!");
            return false;
        } else if (password.equals(confirm_password)) {
            player.setPassword(password);
            return true;
        } else {
            FancyToast.makeText(getBaseContext(), "Password Didn't Match!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etRegisterPassword);
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etRegisterConfirmPassword);
            return false;
        }

    }

    boolean emailValidation() {
        String email = binding.etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            FancyToast.makeText(getBaseContext(), "Don't Leave it Empty!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etEmail);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(getBaseContext(), "Enter Valid Email!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etEmail);
            return false;
        } else {
            player.setEmail(email);
            return true;
        }

    }


    void gender_Country() {
        String country = binding.countryCodePicker.getSelectedCountryName();
        player.setCountryName(country);
        player.setGender(String.valueOf(binding.rbMale.isChecked()));

    }

    boolean usernameValidation() {
        String username = binding.etUsername.getText().toString().trim();

        if (username.isEmpty()) {
            binding.etUsername.setError("Don't Leave it Empty!");
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etUsername);
            return false;
        } else if (!(username.length() > 3 && username.length() < 16)) {
            binding.etUsername.setError("Level Name Should be above 3 characters");
            return false;
        } else {
            player.setUsername(username);
            return true;
        }

    }


//    boolean ageValidation() {
//        if (age>10 && age < 100) {
//            player.setBirthdate(date);
//            return true;
//        }
//        else if (age<10 || age > 100) {
//            binding.tvBirthdate.setError(getString(R.string.min_age_10years));
//            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.tvBirthdate);
//            return false;
//        }
//        else if (age == 0 ) {
//            binding.tvBirthdate.setError(getString(R.string.dont_leaveEmpty));
//            return false; }
//
//        else
//            return false;
//
//    }


}