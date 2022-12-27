package com.wasem.mysteriousquestions.DataBase.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (foreignKeys = {@ForeignKey(entity = Player.class,
                parentColumns = {"playerId"},
                childColumns = {"playerId"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)})

public class PlayerQuestion {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public int playerId;
    public int playerPoints;
    public int skipTimes;
    public int correctAnswers;
    public int wrongAnswers;

    public PlayerQuestion() {
    }

    public PlayerQuestion(int playerId, int playerPoints, int skipTimes, int correctAnswers, int wrongAnswers) {
        this.playerId = playerId;
        this.playerPoints = playerPoints;
        this.skipTimes = skipTimes;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public PlayerQuestion(int playerPoints, int skipTimes, int correctAnswers, int wrongAnswers) {
        this.playerPoints = playerPoints;
        this.skipTimes = skipTimes;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }
}
