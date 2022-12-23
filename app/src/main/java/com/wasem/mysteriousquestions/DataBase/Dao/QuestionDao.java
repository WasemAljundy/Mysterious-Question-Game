package com.wasem.mysteriousquestions.DataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.Models.Question;

import java.util.List;
@Dao
public interface QuestionDao {
    @Insert
    long insertQuestion (Question question);
    @Query("select * from Question")
    LiveData<List<Question>> getAllQuestions();
    @Query("select * from Question where level_no = :level_no")
    LiveData<List<Question>> getAllLevelQuestions(int level_no);
    @Query("select * from Question inner join PlayerQuestion on Question.question_id = PlayerQuestion.question_id where playerId =:playerId")
    LiveData<List<Question>> getPlayerQuestionsDetails(int playerId);
}
