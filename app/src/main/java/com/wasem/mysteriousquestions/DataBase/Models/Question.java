package com.wasem.mysteriousquestions.DataBase.Models;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity (foreignKeys = {@ForeignKey(entity = Level.class,
        parentColumns = {"level_no"},
        childColumns = {"level_no"},
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)})

public class Question {
    public int id;
    @PrimaryKey (autoGenerate = true)
    @SerializedName("question_id")
    @Expose
    public int question_id;
    @SerializedName("level_no")
    @Expose
    public int level_no;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("answer_1")
    @Expose
    public String answer1;
    @SerializedName("answer_2")
    @Expose
    public String answer2;
    @SerializedName("answer_3")
    @Expose
    public String answer3;
    @SerializedName("answer_4")
    @Expose
    public String answer4;
    @SerializedName("true_answer")
    @Expose
    public String trueAnswer;
    @SerializedName("points")
    @Expose
    public int points;
    @SerializedName("duration")
    @Expose
    public int duration;
    @SerializedName("pattern")
    @Expose
    public int pattern;
    @SerializedName("hint")
    @Expose
    public String hint;

    public Question() {
    }

    public Question(int question_id, int level_no, String title, String answer1, String answer2, String answer3, String answer4, String trueAnswer, int points, int duration, int pattern, String hint) {
        this.question_id = question_id;
        this.level_no = level_no;
        this.title = title;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.trueAnswer = trueAnswer;
        this.points = points;
        this.duration = duration;
        this.pattern = pattern;
        this.hint = hint;
    }

    public Question(int level_no, String title, String answer1, String answer2, String answer3, String answer4, String trueAnswer, int points, int duration, int pattern, String hint) {
        this.level_no = level_no;
        this.title = title;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.trueAnswer = trueAnswer;
        this.points = points;
        this.duration = duration;
        this.pattern = pattern;
        this.hint = hint;
    }



    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getLevel_no() {
        return level_no;
    }

    public void setLevel_no(int level_no) {
        this.level_no = level_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
