package com.hou.recruitment.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hou.recruitment.bean.Score;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @author Fred Liu (liuxiaokun0410@gmail.com)
 * @version 5.0
 * @since 2015/11/20 17:27
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "my.db3";
    private static final int DATABASE_VERSION = 1;

    private Dao<Score, Integer> scoreDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Score.class);

            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三1', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三2', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三3', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三4', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三5', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三6', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三7', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三8', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三9', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三11', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三12', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三13', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三14', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三15', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三16', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三17', 90, 100, 0)");
            db.execSQL("INSERT INTO score (name, first_score, final_score, sync) VALUES('张三18', 90, 100, 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

    }

    public Dao<Score, Integer> getScoreDao() {

        if (scoreDao == null) {
            try {
                scoreDao = getDao(Score.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return scoreDao;
    }


    @Override
    public void close() {
        super.close();
        scoreDao = null;
    }

}
