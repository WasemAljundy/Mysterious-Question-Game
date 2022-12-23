package com.wasem.mysteriousquestions.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.wasem.mysteriousquestions.DataBase.Models.Question;
import com.wasem.mysteriousquestions.Fragments.QuestionPagerFragment;

import java.util.List;

public class PagerAdapter extends FragmentStateAdapter {

    private List<Question> questions;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int id = questions.get(position).question_id;
        int pattern = questions.get(position).pattern;
        return QuestionPagerFragment.newInstance(id,pattern);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
