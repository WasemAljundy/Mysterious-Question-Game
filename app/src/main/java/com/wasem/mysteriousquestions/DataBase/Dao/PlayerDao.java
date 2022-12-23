package com.wasem.mysteriousquestions.DataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wasem.mysteriousquestions.DataBase.Models.Player;

import java.util.List;
@Dao
public interface PlayerDao {
    @Insert
    long insertPlayer (Player player);
    @Delete
    int deletePlayer (Player player);
    @Query("select * from Player")
    LiveData<List<Player>> getAllPlayers();
    @Query("select * from Player where playerId =:playerId")
    LiveData<List<Player>> getAllPlayers (int playerId);

    @Query("update Player set username = :username, email = :email, password = :password, dateOfBirth = :dateOfBirth, countryName = :countryName, gender = :gender where playerId = :playerId" )
    int updatePlayer(int playerId, String username , String email , String password , String dateOfBirth , String countryName , String gender);
}
