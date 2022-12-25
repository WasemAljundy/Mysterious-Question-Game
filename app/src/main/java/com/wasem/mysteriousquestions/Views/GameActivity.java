package com.wasem.mysteriousquestions.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
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

    public static final int PATTERN_TRUE_FALSE = 1;
    public static final int PATTERN_SELECT_CHOICE = 2;
    public static final int PATTERN_COMPLETION = 3;
    int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Question>>() {}.getType();
        List<Question> questions = gson.fromJson(getIntent().getStringExtra("currentLevelQuestions"),listType);
        Log.d("LEVEL-QUESTION", "onChanged: " + questions.size());

        pagerAdapter = new PagerAdapter(GameActivity.this,questions);
        binding.pager.setAdapter(pagerAdapter);

        binding.pager.setPageTransformer(new DepthPageTransformer());

        binding.pager.setUserInputEnabled(false);


        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("PAGER-INDEX", "INDEX: "+position);
                currentIndex = position;
            }
        });


    }

    @Override
    public void onOkButtonClicked() {
        Toast.makeText(this, "Next Question", Toast.LENGTH_SHORT).show();
        onQuestionInteractionListener();
    }


    @Override
    public void onQuestionInteractionListener() {
        currentIndex ++;
        binding.pager.setCurrentItem(currentIndex);
        Log.d("QuestionListener", "onQuestionInteractionListener: "+ currentIndex);
    }
}
