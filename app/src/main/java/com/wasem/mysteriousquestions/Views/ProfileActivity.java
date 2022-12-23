package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.DataBase.Listeners.UpdateDeleteListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wasem.mysteriousquestions.databinding.ActivityProfileBinding;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    PlayerViewModel playerViewModel;
    Player updatedPlayer = new Player();
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        sp = getSharedPreferences("Player", MODE_PRIVATE);
        edit = sp.edit();

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
                    playerViewModel.updatePlayer(LoginActivity.currentPlayerId,updatedPlayer.getUsername(),updatedPlayer.getEmail(),updatedPlayer.getPassword(),updatedPlayer.getDateOfBirth(),updatedPlayer.getCountryName(),updatedPlayer.getGender(), new UpdateDeleteListener() {
                        @Override
                        public void onUpdateDeleteListener(int rowsAffected) {
                            Log.d("PLAYER-ID", "onUpdateDeleteListener: "+LoginActivity.currentPlayerId);
                            Log.d("rowsAffected", "onUpdateListener: "+rowsAffected);
                        }
                    });

                    FancyToast.makeText(getBaseContext(),"Your Profile data changed successfully!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    onBackPressed();
                }
            }
        });
        
    }


//********************************************** Validation Methods ************************************************************************************//


    boolean emailValidation () {
        String email = binding.etNewEmail.getText().toString().trim();
        if (email.isEmpty() ) {
            FancyToast.makeText(getBaseContext(), "Don't Leave it Empty!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewEmail);
            return false; }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(getBaseContext(), "Enter Valid Email!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
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
            binding.etNewUsername.setError("Don't Leave it Empty!");
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewUsername);
            return false; }

        else if(!(username.length()>3 && username.length()<16)){
            binding.etNewUsername.setError("Level Name Should be above 3 characters");
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
            binding.etNewPassword.setError("Don't Leave it Empty!");
            return false;
        } else if (!(password.length() > 7 && password.length() < 16)) {
            binding.etNewPassword.setError("Password Should be at least 8 Characters!");
            return false;
        } else if (password.equals(confirm_password)) {
            updatedPlayer.setPassword(password);
            return true;
        } else {
            FancyToast.makeText(getBaseContext(), "Password Didn't Match!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewPassword);
            YoYo.with(Techniques.Bounce).duration(700).playOn(binding.etNewConfirmPassword);
            return false;
        }

    }

}