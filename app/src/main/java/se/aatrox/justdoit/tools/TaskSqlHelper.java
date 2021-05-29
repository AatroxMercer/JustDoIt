package se.aatrox.justdoit.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
* Tool Class
* Single Instance Model
* */

public class TaskSqlHelper extends SQLiteOpenHelper {
    private static SQLiteOpenHelper taskSqlHelper;
    public static synchronized SQLiteOpenHelper getTaskSqlHelper(Context context) {
        if (taskSqlHelper == null) {
            taskSqlHelper = new TaskSqlHelper(context, "noteboard.db", null, 1);
        }
        return taskSqlHelper;
    }

    private TaskSqlHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // init tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tasks(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "task VARCHAR(64) NOT NULL," +
            "deadline TIMESTAMP, " +
            "makespan TIMESTAMP" +
        ");";

        db.execSQL(sql);
    }

    // upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
