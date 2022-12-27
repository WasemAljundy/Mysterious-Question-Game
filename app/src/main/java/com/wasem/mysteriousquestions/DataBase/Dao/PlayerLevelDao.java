package com.wasem.mysteriousquestions.DataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerLevel;
import java.util.List;

@Dao
public interface PlayerLevelDao {
    @Insert
    long insertPlayerLevel (PlayerLevel playerLevel);
    @Delete
    int deletePlayerLevel (PlayerLevel playerLevel);
    @Query("select * from PlayerLevel")
    LiveData<List<PlayerLevel>> getAllPlayerLevelInfo();
}
