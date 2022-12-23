package com.wasem.mysteriousquestions.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.DataBase.Models.Question;
import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.Views.GameActivity;
import com.wasem.mysteriousquestions.databinding.CustomCompletionQuestionBinding;
import com.wasem.mysteriousquestions.databinding.CustomSelectChoiceQuestionBinding;
import com.wasem.mysteriousquestions.databinding.CustomTrueFalseQuestionBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionPagerFragment extends Fragment {

    private static final String ARG_QUESTION_ID = "question_id";
    private static final String ARG_PATTERN = "pattern";

    private int question_id;
    private int pattern;
    private List<Question> questions = new ArrayList<>();
    private QuestionInteractionListener listener;

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
        getQuestionList();
    }

    private void getQuestionList() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Question>>() {
        }.getType();
        questions = gson.fromJson(getActivity().getIntent().getStringExtra("currentLevelQuestions"), listType);
        Log.d("LEVEL-QUESTION-FRAGMENT", "onChanged: " + questions.size());
    }

    private Question fillQuestionByPattern() {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).pattern == pattern) {
                String title = questions.get(i).title;
                String answer1 = questions.get(i).answer1;
                String answer2 = questions.get(i).answer2;
                String answer3 = questions.get(i).answer3;
                String answer4 = questions.get(i).answer4;
                String trueAnswer = questions.get(i).trueAnswer;
                int points = questions.get(i).points;
                int pattern = questions.get(i).pattern;
                int duration = questions.get(i).duration;
                String hint = questions.get(i).hint;
                return new Question(questions.get(i).level_no, title, answer1, answer2, answer3, answer4, trueAnswer, points, pattern, duration, hint);
            }
        }
        return new Question();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (pattern == GameActivity.PATTERN_TRUE_FALSE) {
            CustomTrueFalseQuestionBinding binding = CustomTrueFalseQuestionBinding.inflate(inflater, container, false);
            binding.tvQuestionTitleTrueFalse.setText(fillQuestionByPattern().title);
            binding.tvSkipQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onQuestionInteractionListener();
                }
            });

            binding.btnTrueSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.btnTrueSubmit.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    } else {
                        FancyToast.makeText(getContext(), "Wrong Answer !", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
            });

            binding.btnFalseSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.btnFalseSubmit.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    } else {
                        FancyToast.makeText(getContext(), "Wrong Answer !", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
            });

            return binding.getRoot();
        } else if (pattern == GameActivity.PATTERN_SELECT_CHOICE) {
            CustomSelectChoiceQuestionBinding binding = CustomSelectChoiceQuestionBinding.inflate(inflater, container, false);
            binding.tvQuestionTitleSelectChoice.setText(fillQuestionByPattern().title);
            binding.rbChoice1.setText(fillQuestionByPattern().answer1);
            binding.rbChoice2.setText(fillQuestionByPattern().answer2);
            binding.rbChoice3.setText(fillQuestionByPattern().answer3);
            binding.rbChoice4.setText(fillQuestionByPattern().answer4);
            binding.tvSkipQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onQuestionInteractionListener();
                }
            });

            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.rbChoice1.isChecked() && binding.rbChoice1.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                    else if (binding.rbChoice2.isChecked() && binding.rbChoice2.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                    else if (binding.rbChoice3.isChecked() && binding.rbChoice3.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                    else if (binding.rbChoice4.isChecked() && binding.rbChoice4.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                    else {
                        FancyToast.makeText(getContext(), "Wrong Answer !", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
            });
            return binding.getRoot();
        }
        else if (pattern == GameActivity.PATTERN_COMPLETION) {
            CustomCompletionQuestionBinding binding = CustomCompletionQuestionBinding.inflate(inflater, container, false);
            binding.tvQuestionTitleCompleteQuestion.setText(fillQuestionByPattern().title);
            binding.tvSkipQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onQuestionInteractionListener();
                }
            });
            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.etAnswer.getText().toString().equals(fillQuestionByPattern().trueAnswer)) {
                        FancyToast.makeText(getContext(), "Well Done !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                    else {
                        FancyToast.makeText(getContext(), "Wrong Answer !", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
            });
            return binding.getRoot();
        }
        return inflater.inflate(R.layout.fragment_question_pager, container, false);
    }
}