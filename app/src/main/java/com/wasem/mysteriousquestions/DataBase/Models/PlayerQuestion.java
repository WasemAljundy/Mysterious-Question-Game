package com.wasem.mysteriousquestions.DataBase.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"playerId", "question_id"},

        foreignKeys = {@ForeignKey(entity = Player.class,
                parentColumns = {"playerId"},
                childColumns = {"playerId"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Question.class,
                        parentColumns = {"question_id"},
                        childColumns = {"question_id"},
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE)}
)

public class PlayerQuestion {
    private int playerId;
    private int question_id;
    private int level_no;
    private int playerPoints;
    private int skipTimes;
    private int correctAnswers;
    private int wrongAnswers;

    public PlayerQuestion() {
    }

    public PlayerQuestion(int playerId, int question_id, int level_no, int playerPoints, int skipTimes, int correctAnswers, int wrongAnswers) {
        this.playerId = playerId;
        this.question_id = question_id;
        this.level_no = level_no;
        this.playerPoints = playerPoints;
        this.skipTimes = skipTimes;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public PlayerQuestion(int question_id, int level_no, int playerPoints, int skipTimes, int correctAnswers, int wrongAnswers) {
        this.question_id = question_id;
        this.level_no = level_no;
        this.playerPoints = playerPoints;
        this.skipTimes = skipTimes;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public int getSkipTimes() {
        return skipTimes;
    }

    public void setSkipTimes(int skipTimes) {
        this.skipTimes = skipTimes;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }
}
