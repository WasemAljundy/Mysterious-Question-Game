package com.wasem.mysteriousquestions.DataBase.Models;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity (indices = {@Index(value = {"level_no"},unique = true)})

public class Level {
    @PrimaryKey (autoGenerate = true)
    public int id;
    @SerializedName("level_no")
    @Expose
    public int level_no;
    @SerializedName("unlock_points")
    @Expose
    public int unlockPoints;


    public Level() {
    }

    public Level(int id, int level_no, int unlockPoints) {
        this.id = id;
        this.level_no = level_no;
        this.unlockPoints = unlockPoints;
    }

    public Level(int level_no, int unlockPoints) {
        this.level_no = level_no;
        this.unlockPoints = unlockPoints;
    }

    public int getLevelNo() {
        return level_no;
    }

    public void setLevelNo(int level_no) {
        this.level_no = level_no;
    }

    public int getUnlockPoints() {
        return unlockPoints;
    }

    public void setUnlockPoints(int unlockPoints) {
        this.unlockPoints = unlockPoints;
    }

}
