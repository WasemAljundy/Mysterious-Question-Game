package com.wasem.mysteriousquestions.Views;

import static androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasem.mysteriousquestions.Fragments.QuestionInteractionListener;
import com.wasem.mysteriousquestions.ViewPager.DepthPageTransformer;
import com.wasem.mysteriousquestions.Fragments.DialogInteractionListener;
import com.wasem.mysteriousquestions.DataBase.Models.Question;
import com.wasem.mysteriousquestions.ViewPager.PagerAdapter;
import com.wasem.mysteriousquestions.databinding.ActivityGameBinding;

import java.lang.reflect.Type;
import java.util.List;

public class GameActivity extends AppCompatActivity implements DialogInteractionListener, QuestionInteractionListener {
    ActivityGameBinding binding;
    PagerAdapter pagerAdapter;
    int currentIndex;

    public static final int PATTERN_TRUE_FALSE = 1;
    public static final int PATTERN_SELECT_CHOICE = 2;
    public static final int PATTERN_COMPLETION = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeMethods();
    }


    @Override
    public void onOkButtonClicked() {
        currentIndex = binding.pager.getCurrentItem();
        binding.pager.setCurrentItem(currentIndex + 1,false);
        Log.d("PAGER-INDEX", "onOkButtonClicked: "+ currentIndex);
    }


    @Override
    public void onQuestionInteractionListener() {
        currentIndex = binding.pager.getCurrentItem();
        binding.pager.setCurrentItem(currentIndex + 1,false);
    }

    private void initializeMethods() {
        getQuestionsList();
        pagerInitializer();
    }

    private List<Question> getQuestionsList() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Question>>() {}.getType();
        List<Question> questions = gson.fromJson(getIntent().getStringExtra("currentLevelQuestions"),listType);
        Log.d("LEVEL-QUESTION", "onChanged: " + questions.size());
        return questions;
    }

    private void pagerInitializer(){
        pagerAdapter = new PagerAdapter(GameActivity.this,getQuestionsList());
        binding.pager.setAdapter(pagerAdapter);
        binding.pager.setPageTransformer(new DepthPageTransformer());
        binding.pager.setUserInputEnabled(false);
    }


}
