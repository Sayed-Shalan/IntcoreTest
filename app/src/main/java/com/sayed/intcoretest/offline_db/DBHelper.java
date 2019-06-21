package com.sayed.intcoretest.offline_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "intcore_test"; //DB name
    private static final int DATABASE_VERSION = 1; //DB version

    //create movie table query
    private static final String TABLE_NAME_MOVIE = "movie";
    static final String CREATE_MOVIE_TABLE =
            " CREATE TABLE " + TABLE_NAME_MOVIE +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " movie_id INTEGER NOT NULL," +
                    " movie_title TEXT NOT NULL," +
                    " poster_path TEXT NOT NULL);" ;

    //Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_MOVIE); // Drop older table if existed
        onCreate(db); // Create tables again
    }
}
