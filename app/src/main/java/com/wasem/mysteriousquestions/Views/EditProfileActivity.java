package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.DataBase.Listeners.UpdateDeleteListener;
import com.wasem.mysteriousquestions.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wasem.mysteriousquestions.databinding.ActivityProfileBinding;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    PlayerViewModel playerViewModel;
    Player updatedPlayer = new Player();
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        binding.tvNewBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                                binding.tvNewBirthDate.setText(date);
                                updatedPlayer.setDateOfBirth(date);
//                                age = now.get(Calendar.YEAR) - year;
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getSupportFragmentManager(), "DatePickerDialog");
            }

        });

        binding.btnApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userValid = usernameValidation();
                boolean passValid = passwordValidation();
                boolean emailValid = emailValidation();
                gender_Country();

                if (userValid && emailValid && passValid){

                    if (LoginActivity.currentPlayerId != 0) {
                        playerViewModel.updatePlayer(LoginActivity.currentPlayerId,updatedPlayer.getUsername(),updatedPlayer.getEmail(),updatedPlayer.getPassword(),updatedPlayer.getDateOfBirth(),updatedPlayer.getCountryName(),updatedPlayer.getGender(), new UpdateDeleteListener() {
                            @Override
                            public void onUpdateDeleteListener(int rowsAffected) {
                                Log.d("PLAYER-ID", "onUpdateDeleteListener: "+LoginActivity.currentPlayerId);
                                Log.d("rowsAffected", "onUpdateListener: "+rowsAffected);
                            }
                        });
                        FancyToast.makeText(getBaseContext(),getString(R.string.your_profile_data_changed_successfully), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        onBackPressed();
                    }
                    else {
                        AppSharedPreferences.getInstance(getApplicationContext()).rememberMePlayerBtnUnChecked();
                        FancyToast.makeText(getBaseContext(),getString(R.string.please_login_again_to_update), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        });
        
    }


//********************************************** Validation Methods ************************************************************************************//


    boolean emailValidation () {
        String email = binding.etNewEmail.getText().toString().trim();
        if (email.isEmpty() ) {
            FancyToast.makeText(getBaseContext(), getString(R.string.dont_leave_it_empty), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewEmail);
            return false; }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(getBaseContext(), getString(R.string.enter_valid_email), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewEmail);
            return false; }

        else {
            updatedPlayer.setEmail(email);
            return true;
        }
    }

    void gender_Country () {
        String country = binding.newCountryCodePicker.getSelectedCountryName();
        updatedPlayer.setCountryName(country);
        updatedPlayer.setGender(String.valueOf(binding.rbMale.isChecked()));

    }

    boolean usernameValidation () {
        String username = binding.etNewUsername.getText().toString().trim();

        if (username.isEmpty()) {
            binding.etNewUsername.setError(getString(R.string.dont_leave_it_empty));
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewUsername);
            return false; }

        else if(!(username.length()>3 && username.length()<16)){
            binding.etNewUsername.setError(getString(R.string.user_name_should_be_above_3_characters));
            return false; }

        else {
            updatedPlayer.setUsername(username);
            return true;
        }
    }

    boolean passwordValidation() {
        String password = binding.etNewPassword.getText().toString().trim();
        String confirm_password = binding.etNewConfirmPassword.getText().toString().trim();
        if (password.isEmpty()) {
            binding.etNewPassword.setError(getString(R.string.dont_leave_it_empty));
            return false;
        } else if (!(password.length() > 7 && password.length() < 16)) {
            binding.etNewPassword.setError(getString(R.string.passwordShouldBeAtLeast8Characters));
            return false;
        } else if (password.equals(confirm_password)) {
            updatedPlayer.setPassword(password);
            return true;
        } else {
            FancyToast.makeText(getBaseContext(), getString(R.string.password_didnt_match), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewPassword);
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewConfirmPassword);
            return false;
        }

    }

}