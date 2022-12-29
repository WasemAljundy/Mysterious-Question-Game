package com.wasem.mysteriousquestions.DataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;

import java.util.List;

@Dao
public interface PlayerQuestionDao {
    @Insert
    long insertPlayerQuestion (PlayerQuestion playerQuestion);
    @Delete
    int deletePlayerQuestion (PlayerQuestion playerQuestion);
    @Query("select * from PlayerQuestion where playerId = :playerId")
    LiveData<List<PlayerQuestion>> getAllPlayerQuestionInfo(int playerId);

    @Query("select id , playerId,playerPoints,sum(skipTimes) as skipTimes , sum(correctAnswers) as correctAnswers, sum(wrongAnswers) as wrongAnswers from PlayerQuestion where playerId = :playerId")
    LiveData<List<PlayerQuestion>> getAllPlayerQuestionProgress(int playerId);

}
