package com.wasem.mysteriousquestions.DataBase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wasem.mysteriousquestions.DataBase.Listeners.InsertListener;
import com.wasem.mysteriousquestions.DataBase.Listeners.UpdateDeleteListener;
import com.wasem.mysteriousquestions.DataBase.Models.Level;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerLevel;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;
import com.wasem.mysteriousquestions.DataBase.Models.Question;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private Repository repository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

//---------------------------------------------- *** Player Methods *** -------------------------------------------------------------------//////


    public void insertPlayer (Player player, InsertListener listener){
        repository.insertPlayer(player,listener);
    }

    public void updatePlayer (int playerId, String username , String email , String password , String dateOfBirth , String countryName , String gender, UpdateDeleteListener listener){
        repository.updatePlayer(playerId, username , email , password , dateOfBirth , countryName , gender, listener);
    }

    public void deletePlayer(Player player,UpdateDeleteListener listener){
        repository.deletePlayer(player,listener);
    }

    public LiveData<List<Player>> getAllPlayers (){
        return repository.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers (int playerId){
        return repository.getAllPlayers(playerId);
    }


//---------------------------------------------- *** Levels Methods *** -------------------------------------------------------------------//////


    public void insertLevel(Level level , InsertListener listener){
        repository.insertLevel(level,listener);
    }

    public void deleteLevel(Level level , UpdateDeleteListener listener){
        repository.deleteLevel(level,listener);
    }

    public LiveData<List<Level>> getAllLevels(){
        return repository.getAllLevels();
    }

    public LiveData<List<Level>> getPlayerLevelDetails(int playerId){
        return repository.getPlayerLevelDetails(playerId);
    }

//---------------------------------------------- *** Questions Methods *** -------------------------------------------------------------------//////


    public void insertQuestion(Question question , InsertListener listener){
        repository.insertQuestion(question,listener);
    }

    public LiveData<List<Question>> getAllQuestions(){
        return repository.getAllQuestions();
    }

    public LiveData<List<Question>> getAllLevelQuestions(int level_no){
        return repository.getAllLevelQuestions(level_no);
    }


//---------------------------------------------- *** Player Level Methods *** -------------------------------------------------------------------//////

    public void insertPlayerLevel(PlayerLevel playerLevel){
        repository.insertPlayerLevel(playerLevel);
    }

    public void deletePlayerLevel(PlayerLevel playerLevel){
        repository.deletePlayerLevel(playerLevel);
    }

    public LiveData<List<PlayerLevel>> getAllPlayerLevelInfo(int level_no){
        return repository.getAllPlayerLevelInfo(level_no);
    }

//---------------------------------------------- *** Player Question Methods *** -------------------------------------------------------------------//////

    public void insertPlayerQuestion(PlayerQuestion playerQuestion){
        repository.insertPlayerQuestion(playerQuestion);
    }

    public void deletePlayerQuestion(PlayerQuestion playerQuestion){
        repository.deletePlayerQuestion(playerQuestion);
    }

    public LiveData<List<PlayerQuestion>> getAllPlayerQuestionInfo(int playerId){
        return repository.getAllPlayerQuestionInfo(playerId);
    }

    public LiveData<List<PlayerQuestion>> getAllPlayerQuestionProgress(int playerId){
        return repository.getAllPlayerQuestionProgress(playerId);
    }


}
