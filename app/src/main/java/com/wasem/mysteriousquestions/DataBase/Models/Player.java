package com.wasem.mysteriousquestions.DataBase.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.wasem.mysteriousquestions.DataBase.DateConverter;

import java.util.Date;

@Entity

@TypeConverters(DateConverter.class)

public class Player {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int playerId;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String dateOfBirth;
    @NonNull
    private String countryName;
    @NonNull
    private String gender;

    public Player() {
    }

    public Player(@NonNull int playerId, @NonNull String username, @NonNull String email, @NonNull String password, @NonNull String dateOfBirth, @NonNull String countryName, @NonNull String gender) {
        this.playerId = playerId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.countryName = countryName;
        this.gender = gender;
    }

    public Player(@NonNull String username, @NonNull String email, @NonNull String password, @NonNull String dateOfBirth, @NonNull String countryName, @NonNull String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.countryName = countryName;
        this.gender = gender;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NonNull String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NonNull
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NonNull String countryName) {
        this.countryName = countryName;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }
}
