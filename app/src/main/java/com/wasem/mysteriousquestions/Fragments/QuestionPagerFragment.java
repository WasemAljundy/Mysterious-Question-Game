package com.wasem.mysteriousquestions.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;
import com.wasem.mysteriousquestions.DataBase.Models.Question;
import com.wasem.mysteriousquestions.DataBase.PlayerViewModel;
import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.Views.GameActivity;
import com.wasem.mysteriousquestions.Views.LoginActivity;
import com.wasem.mysteriousquestions.databinding.CustomCompletionQuestionBinding;
import com.wasem.mysteriousquestions.databinding.CustomSelectChoiceQuestionBinding;
import com.wasem.mysteriousquestions.databinding.CustomTrueFalseQuestionBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionPagerFragment extends Fragment {

    private static final String ARG_QUESTION_ID = "question_id";
    private static final String ARG_PATTERN = "pattern";

    private int question_id;
    private int pattern;
    private QuestionInteractionListener listener;
    private PlayerViewModel viewModel;
    private Timer timer;
    private int timerSeconds;
    private int score = 0;
    private int skipTimes = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private boolean isFinishedLevel;

    public QuestionPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof QuestionInteractionListener)
            listener = (QuestionInteractionListener) context;
    }

    public static QuestionPagerFragment newInstance(int question_id, int pattern) {
        QuestionPagerFragment fragment = new QuestionPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_ID, question_id);
        args.putInt(ARG_PATTERN, pattern);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question_id = getArguments().getInt(ARG_QUESTION_ID);
            pattern = getArguments().getInt(ARG_PATTERN);
        }
    }

    private List<Question> getQuestionList() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(getActivity().getIntent().getStringExtra("currentLevelQuestions"), listType);
    }

    private Question fillQuestionByPattern() {
        for (int i = 0; i < getQuestionList().size(); i++) {
            if (getQuestionList().get(i).pattern == pattern && getQuestionList().get(i).question_id == question_id) {
                String title = getQuestionList().get(i).title;
                String answer1 = getQuestionList().get(i).answer1;
                String answer2 = getQuestionList().get(i).answer2;
                String answer3 = getQuestionList().get(i).answer3;
                String answer4 = getQuestionList().get(i).answer4;
                String trueAnswer = getQuestionList().get(i).trueAnswer;
                int points = getQuestionList().get(i).points;
                int pattern = getQuestionList().get(i).pattern;
                int duration = getQuestionList().get(i).duration;
                String hint = getQuestionList().get(i).hint;
                isFinishedLevel = getQuestionList().size()-1 == i;

                return new Question(getQuestionList().get(i).level_no, title, answer1, answer2, answer3, answer4, trueAnswer, points, duration, pattern, hint);
            }
        }
        return new Question();
    }

    private int levelRating (int levelNo) {
        if ( levelNo == fillQuestionByPattern().level_no && AppSharedPreferences.getInstance(getContext()).getScore() >= 6) {
            return R.drawable.img_three_stars;
        }
        else if ( levelNo == fillQuestionByPattern().level_no && AppSharedPreferences.getInstance(getContext()).getScore() >= 3) {
            return R.drawable.img_two_stars;
        }
        else {
            return R.drawable.img_no_stars;
        }
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (pattern == GameActivity.PATTERN_TRUE_FALSE) {

            CustomTrueFalseQuestionBinding binding = CustomTrueFalseQuestionBinding.inflate(inflater, container, false);

            binding.tvLevel.setText(String.valueOf(fillQuestionByPattern().level_no));

            binding.tvQuestionTitleTrueFalse.setText(fillQuestionByPattern().title);

            binding.tvTotalPoints.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));

            countDownTimer(binding.tvTimerSeconds,binding.tvTotalPoints);

            binding.tvSkipQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skipQuestionValidation(binding.tvTotalPoints);
                    listener.onQuestionInteractionListener();
                }
            });

            binding.btnTrueSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.btnTrueSubmit.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else {
                        wrongAnswerValidation(binding.tvTotalPoints);
                    }
                }
            });

            binding.btnFalseSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.btnFalseSubmit.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else {
                        wrongAnswerValidation(binding.tvTotalPoints);
                    }
                }
            });
            return binding.getRoot();
        }
//--------------------------------------- 2.   Select Pattern   -----------------------------------------------------------------------//

        else if (pattern == GameActivity.PATTERN_SELECT_CHOICE) {

            CustomSelectChoiceQuestionBinding binding = CustomSelectChoiceQuestionBinding.inflate(inflater, container, false);

            Toast.makeText(getContext(), fillQuestionByPattern().title, Toast.LENGTH_SHORT).show();

            binding.tvLevel.setText(String.valueOf(fillQuestionByPattern().level_no));

            binding.tvQuestionTitleSelectChoice.setText(fillQuestionByPattern().title);

            binding.tvTotalPoints.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));

            binding.rbChoice1.setText(fillQuestionByPattern().answer1);
            binding.rbChoice2.setText(fillQuestionByPattern().answer2);
            binding.rbChoice3.setText(fillQuestionByPattern().answer3);
            binding.rbChoice4.setText(fillQuestionByPattern().answer4);

            countDownTimer(binding.tvTimerSeconds,binding.tvTotalPoints);

            binding.tvSkipQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skipQuestionValidation(binding.tvTotalPoints);
                    listener.onQuestionInteractionListener();
                }
            });

            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.rbChoice1.isChecked() && binding.rbChoice1.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else if (binding.rbChoice2.isChecked() && binding.rbChoice2.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else if (binding.rbChoice3.isChecked() && binding.rbChoice3.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else if (binding.rbChoice4.isChecked() && binding.rbChoice4.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else {
                        wrongAnswerValidation(binding.tvTotalPoints);
                    }
                }
            });
            return binding.getRoot();
        }

//--------------------------------------- 3.   Completion Pattern     -----------------------------------------------------------------------//

         else if (pattern == GameActivity.PATTERN_COMPLETION) {


            CustomCompletionQuestionBinding binding = CustomCompletionQuestionBinding.inflate(inflater, container, false);

            Toast.makeText(getContext(), fillQuestionByPattern().title, Toast.LENGTH_SHORT).show();

            binding.tvLevel.setText(String.valueOf(fillQuestionByPattern().level_no));

            binding.tvQuestionTitleCompleteQuestion.setText(fillQuestionByPattern().title);

            binding.tvTotalPoints.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));

            countDownTimer(binding.tvTimerSeconds,binding.tvTotalPoints);

            binding.tvSkipQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skipQuestionValidation(binding.tvTotalPoints);
                    listener.onQuestionInteractionListener();
                }
            });

            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.etAnswer.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        rightAnswerValidation(binding.tvTotalPoints);
                    }
                    else {
                        wrongAnswerValidation(binding.tvTotalPoints);
                    }
                }
            });
            return binding.getRoot();
        }
        return inflater.inflate(R.layout.fragment_question_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFinishedLevel){
            Toast.makeText(getContext(), "LEVEL FINISHED!", Toast.LENGTH_SHORT).show();
            insertPlayerQuestionDetails();
        }

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        insertPlayerQuestionDetails();
//    }


    private void countDownTimer(TextView timerView, TextView scoreView){
        timerSeconds = fillQuestionByPattern().duration / 1000;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerView.setText(String.valueOf(timerSeconds));
                            timerSeconds --;
                            if (timerSeconds == -1){
                                timerIsUpValidation(scoreView);
                            }
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    private void rightAnswerValidation(TextView scoreView){
        DialogFragment dialogFragment = DialogFragment.newInstance("Good Job! 😍",fillQuestionByPattern().hint, R.drawable.true_ans_btn_shape);
        dialogFragment.show(getActivity().getSupportFragmentManager(),"Right Answer Dialog");
        int oldScore = Integer.parseInt(scoreView.getText().toString());
        score = oldScore + fillQuestionByPattern().points;
        AppSharedPreferences.getInstance(getContext()).scoreSave(score);
        scoreView.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));
        Log.d("SCORE", "FINAL-SCORE: "+ AppSharedPreferences.getInstance(getContext()).getScore());
        correctAnswers += 1;
        timer.cancel();
    }

    private void wrongAnswerValidation(TextView scoreView){
        DialogFragment dialogFragment = DialogFragment.newInstance("Wrong Answer 🙁",fillQuestionByPattern().hint, R.drawable.img_wrong_answer_red);
        dialogFragment.show(getActivity().getSupportFragmentManager(),"Wrong Answer Dialog");
        int oldScore = Integer.parseInt(scoreView.getText().toString());
        score = oldScore - fillQuestionByPattern().points;
        AppSharedPreferences.getInstance(getContext()).scoreSave(score);
        scoreView.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));
        Log.d("SCORE", "FINAL-SCORE: "+ AppSharedPreferences.getInstance(getContext()).getScore());
        wrongAnswers += 1;
        timer.cancel();
    }

    private void timerIsUpValidation(TextView scoreView){
        DialogFragment dialogFragment = DialogFragment.newInstance("Time is Up!",fillQuestionByPattern().hint,R.drawable.img_wrong_answer_red);
        dialogFragment.show(getActivity().getSupportFragmentManager(),"Time Up Dialog");
        int oldScore = Integer.parseInt(scoreView.getText().toString());
        score = oldScore - fillQuestionByPattern().points;
        AppSharedPreferences.getInstance(getContext()).scoreSave(score);
        scoreView.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));
        Log.d("SCORE", "FINAL-SCORE: "+ AppSharedPreferences.getInstance(getContext()).getScore());
        wrongAnswers += 1;
        timer.cancel();
    }

    private void skipQuestionValidation(TextView scoreView){
        int oldScore = Integer.parseInt(scoreView.getText().toString());
        score = oldScore - fillQuestionByPattern().points;
        AppSharedPreferences.getInstance(getContext()).scoreSave(score);
        scoreView.setText(String.valueOf(AppSharedPreferences.getInstance(getContext()).getScore()));
        Log.d("SCORE", "FINAL-SCORE: "+ AppSharedPreferences.getInstance(getContext()).getScore());
        skipTimes += 1;
        timer.cancel();
    }

    private void insertPlayerQuestionDetails(){
        PlayerQuestion playerQuestion = new PlayerQuestion(LoginActivity.currentPlayerId,score,skipTimes,correctAnswers,wrongAnswers);
        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        viewModel.insertPlayerQuestion(playerQuestion);
    }


}