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


    public void scoreSave(int score){
        editor = sharedPreferences.edit();
        editor.putInt("score",score);
        editor.apply();
    }

    public int getScore(){
        return sharedPreferences.getInt("score",0);
    }

}
