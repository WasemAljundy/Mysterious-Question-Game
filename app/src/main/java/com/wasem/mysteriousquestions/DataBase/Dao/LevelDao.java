package com.wasem.mysteriousquestions.DataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wasem.mysteriousquestions.DataBase.Models.Level;
import com.wasem.mysteriousquestions.DataBase.Models.Player;

import java.util.List;
@Dao
public interface LevelDao {
    @Insert
    long insertLevel (Level level);
    @Delete
    int deleteLevel (Level level);
    @Query("select * from Level order by level_no asc ")
    LiveData<List<Level>> getAllLevels();
    @Query("select * from Level inner join PlayerLevel on Level.level_no = PlayerLevel.level_no where playerId =:playerId")
    LiveData<List<Level>> getPlayerLevelDetails(int playerId);
}
