package com.wasem.mysteriousquestions.DataBase.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"playerId","level_no"},

        foreignKeys = {@ForeignKey(entity = Player.class,
                parentColumns = {"playerId"},
                childColumns = {"playerId"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Level.class,
                        parentColumns = {"level_no"},
                        childColumns = {"level_no"},
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE)}
)
public class PlayerLevel {

    public int playerId;
    public int level_no;
    public int totalScore;
    public int levelRating;

    public PlayerLevel() {
    }

    public PlayerLevel(int playerId, int level_no, int totalScore, int levelRating) {
        this.playerId = playerId;
        this.level_no = level_no;
        this.totalScore = totalScore;
        this.levelRating = levelRating;
    }

    public PlayerLevel(int level_no, int totalScore, int levelRating) {
        this.level_no = level_no;
        this.totalScore = totalScore;
        this.levelRating = levelRating;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getLevel_no() {
        return level_no;
    }

    public void setLevel_no(int level_no) {
        this.level_no = level_no;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getLevelRating() {
        return levelRating;
    }

    public void setLevelRating(int levelRating) {
        this.levelRating = levelRating;
    }
}
