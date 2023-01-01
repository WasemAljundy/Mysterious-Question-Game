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
    @Query("delete from PlayerLevel")
    void deletePlayerLevel ();
    @Query("select * from PlayerLevel where playerId = :playerId order by level_no")
    LiveData<List<PlayerLevel>> getAllPlayerLevel(int playerId);
    @Query("select * from PlayerLevel where level_no = :level_no")
    LiveData<List<PlayerLevel>> getAllPlayerLevelInfo(int level_no);
}
