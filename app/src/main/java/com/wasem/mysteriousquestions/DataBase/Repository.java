package com.wasem.mysteriousquestions.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wasem.mysteriousquestions.DataBase.Dao.LevelDao;
import com.wasem.mysteriousquestions.DataBase.Dao.PlayerDao;
import com.wasem.mysteriousquestions.DataBase.Dao.QuestionDao;
import com.wasem.mysteriousquestions.DataBase.Listeners.InsertListener;
import com.wasem.mysteriousquestions.DataBase.Listeners.UpdateDeleteListener;
import com.wasem.mysteriousquestions.DataBase.Models.Level;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.Models.Question;

import java.util.List;

public class Repository {

    PlayerDao playerDao;
    LevelDao levelDao;
    QuestionDao questionDao;

    public Repository(Application application){
        MyRoomDataBase db = MyRoomDataBase.getDatabase(application);
        playerDao = db.playerDao();
        levelDao = db.levelDao();
        questionDao = db.questionDao();
    }


//---------------------------------------------- *** Player Methods *** -------------------------------------------------------------------//////


    public void insertPlayer(Player player , InsertListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long id = playerDao.insertPlayer(player);
                listener.onInsertListener(id);
            }
        });
    }

    public void updatePlayer(int playerId, String username , String email , String password , String dateOfBirth , String countryName , String gender, UpdateDeleteListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int id =  playerDao.updatePlayer(playerId,username,email,password,dateOfBirth,countryName,gender);
                listener.onUpdateDeleteListener(id);
            }
        });
    }

    public void deletePlayer(Player player , UpdateDeleteListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int id = playerDao.deletePlayer(player);
                listener.onUpdateDeleteListener(id);
            }
        });
    }

    public LiveData<List<Player>> getAllPlayers(){
        return playerDao.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers(int playerId){
        return playerDao.getAllPlayers(playerId);
    }

//---------------------------------------------- *** Levels Methods *** -------------------------------------------------------------------//////


    public void insertLevel(Level level , InsertListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long id = levelDao.insertLevel(level);
                listener.onInsertListener(id);
            }
        });
    }

    public void deleteLevel(Level level , UpdateDeleteListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int id = levelDao.deleteLevel(level);
                listener.onUpdateDeleteListener(id);
            }
        });
    }

    public LiveData<List<Level>> getAllLevels(){
        return levelDao.getAllLevels();
    }

    public LiveData<List<Level>> getPlayerLevelDetails(int playerId){
        return levelDao.getPlayerLevelDetails(playerId);
    }


//---------------------------------------------- *** Questions Methods *** -------------------------------------------------------------------//////

    public void insertQuestion(Question question , InsertListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long id = questionDao.insertQuestion(question);
                listener.onInsertListener(id);
            }
        });
    }

    public LiveData<List<Question>> getAllQuestions(){
        return questionDao.getAllQuestions();
    }

    public LiveData<List<Question>> getAllLevelQuestions(int level_no){
        return questionDao.getAllLevelQuestions(level_no);
    }

    public LiveData<List<Question>> getPlayerQuestionsDetails(int playerId){
        return questionDao.getPlayerQuestionsDetails(playerId);
    }


}
