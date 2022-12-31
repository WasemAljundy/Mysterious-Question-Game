package com.wasem.mysteriousquestions.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wasem.mysteriousquestions.DataBase.Dao.LevelDao;
import com.wasem.mysteriousquestions.DataBase.Dao.PlayerDao;
import com.wasem.mysteriousquestions.DataBase.Dao.PlayerLevelDao;
import com.wasem.mysteriousquestions.DataBase.Dao.PlayerQuestionDao;
import com.wasem.mysteriousquestions.DataBase.Dao.QuestionDao;
import com.wasem.mysteriousquestions.DataBase.Models.Level;
import com.wasem.mysteriousquestions.DataBase.Models.Player;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerLevel;
import com.wasem.mysteriousquestions.DataBase.Models.PlayerQuestion;
import com.wasem.mysteriousquestions.DataBase.Models.Question;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class, Level.class, Question.class, PlayerLevel.class, PlayerQuestion.class}, version = 1, exportSchema = false)
public abstract class MyRoomDataBase extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract LevelDao levelDao();
    public abstract QuestionDao questionDao();
    public abstract PlayerLevelDao playerLevelDao();
    public abstract PlayerQuestionDao playerQuestionDao();

    private static volatile MyRoomDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDataBase.class, "MysteriousQuestion_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
