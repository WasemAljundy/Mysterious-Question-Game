package com.wasem.mysteriousquestions;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private AppSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE);
    }

    private static AppSharedPreferences instance;

    public static synchronized AppSharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppSharedPreferences(context);
        }
        return instance;
    }


    public void playerScoreSave(int score){
        editor = sharedPreferences.edit();
        editor.putInt("score",score);
        editor.apply();
    }

    public int getPlayerScore(){
        return sharedPreferences.getInt("score",0);
    }

    public void levelScoreSave(int levelScore){
        editor = sharedPreferences.edit();
        editor.putInt("levelScore",levelScore);
        editor.apply();
    }

    public int getLevelScore(){
        return sharedPreferences.getInt("levelScore",0);
    }

    public void lvlOneRatingSave(int rating){
        editor = sharedPreferences.edit();
        editor.putInt("lvlOneRating",rating);
        editor.apply();
    }

    public int getLvlOneRating(){
        return sharedPreferences.getInt("lvlOneRating",0);
    }

    public void lvlTwoRatingSave(int rating){
        editor = sharedPreferences.edit();
        editor.putInt("lvlTwoRating",rating);
        editor.apply();
    }

    public int getLvlTwoRating(){
        return sharedPreferences.getInt("lvlTwoRating",0);
    }

    public void lvlThreeRatingSave(int rating){
        editor = sharedPreferences.edit();
        editor.putInt("lvlThreeRating",rating);
        editor.apply();
    }

    public int getLvlThreeRating(){
        return sharedPreferences.getInt("lvlThreeRating",0);
    }

    public void lvlFourRatingSave(int rating){
        editor = sharedPreferences.edit();
        editor.putInt("lvlFourRating",rating);
        editor.apply();
    }

    public int getLvlFourRating(){
        return sharedPreferences.getInt("lvlFourRating",0);
    }

    public void lvlFiveRatingSave(int rating){
        editor = sharedPreferences.edit();
        editor.putInt("lvlFiveRating",rating);
        editor.apply();
    }

    public int getFiveTwoRating(){
        return sharedPreferences.getInt("lvlFiveRating",0);
    }


    public void rememberMePlayerBtnChecked(){
        editor = sharedPreferences.edit();
        editor.putString("remember", "true");
        editor.apply();
    }

    public void rememberMePlayerBtnUnChecked(){
        editor = sharedPreferences.edit();
        editor.putString("remember", "false");
        editor.apply();
    }

    public String getRememberMePlayerStatus(){
        return sharedPreferences.getString("remember","");
    }


    public void notificationStatusOn(){
        editor = sharedPreferences.edit();
        editor.putString("notification_status","true");
        editor.apply();
    }

    public void notificationStatusOff(){
        editor = sharedPreferences.edit();
        editor.putString("notification_status","false");
        editor.apply();
    }

    public String getNotificationStatus(){
        return sharedPreferences.getString("notification_status","");
    }

    public void musicStatusOn(){
        editor = sharedPreferences.edit();
        editor.putString("music_status","true");
        editor.apply();
    }

    public void musicStatusOff(){
        editor = sharedPreferences.edit();
        editor.putString("music_status","false");
        editor.apply();
    }

    public String getMusicStatus(){
        return sharedPreferences.getString("music_status","");
    }


    public void clearAll(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void clearPlayerTotalScore(){
        editor = sharedPreferences.edit();
        editor.remove("score").apply();
    }
    public void clearPlayerLevelScore(){
        editor = sharedPreferences.edit();
        editor.remove("levelScore").apply();
    }

}
