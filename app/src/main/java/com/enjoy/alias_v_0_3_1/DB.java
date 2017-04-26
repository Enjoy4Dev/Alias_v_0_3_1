package com.enjoy.alias_v_0_3_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.id;
import static android.R.attr.rating;
import static android.content.ContentValues.TAG;
import static android.icu.text.MessagePattern.ArgType.SELECT;

public  class DB{
    public DbHelper mDbHelper;
    public SQLiteDatabase mDb;

    public DB(Context context) {
        mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedReader.db";

    private static final String TABLE_NAME = "players";
    private static final String COLUMN_NAME_SCORE = "score";
    private static final String COLUMN_NAME_ROW_NUMBER = "rowNumber";

    private static final String _ID = "_id";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME_SCORE + INT_TYPE + COMMA_SEP
                    + COLUMN_NAME_ROW_NUMBER       + INT_TYPE
                    + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;




    public void insertId (int _id, int score, int rowNumber) {
        ContentValues cv = new ContentValues();
        cv.put(_ID, _id);
//        cv.put(COLUMN_NAME_ACTIVE_TEAM, activeTeam);
        cv.put(COLUMN_NAME_SCORE, score);
        cv.put(COLUMN_NAME_ROW_NUMBER, rowNumber);
        Log.d(TAG, "insertId " + cv);
        mDb.insert(TABLE_NAME, null, cv);
    }


    public void deleteTable () {

        String buildSQL = "DELETE FROM " + TABLE_NAME;

        Log.d(TAG, "deleteTable" + buildSQL);

        mDb.execSQL(buildSQL);
    }


    public void setScore (int teamId, int teamScore) {

        String buildSQL = "UPDATE " + TABLE_NAME + " SET score = '" + teamScore + "' WHERE _id = '" + teamId + "'";

        Log.d(TAG, "setScore: " + buildSQL);

        mDb.execSQL(buildSQL);
    }
    public Cursor getScoreFromRow (int myRow) {

        String buildSQL = "SELECT * FROM " + TABLE_NAME + " WHERE rowNumber = '" + myRow + "'";

        Log.d(TAG, "getScoreFromRow SQL: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }

    public Cursor getScore (int teamId) {

        String buildSQL = "SELECT " + COLUMN_NAME_SCORE + " FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getScore SQL: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }
    public Cursor getId (int rowNumber) {

        String buildSQL = "SELECT " + _ID + " FROM " + TABLE_NAME + " WHERE rowNumber = '" + rowNumber + "'";

        Log.d(TAG, "getId: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }

    public Cursor getRow (int teamId) {

        String buildSQL = "SELECT " + COLUMN_NAME_ROW_NUMBER + " FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getAllRow SQL: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }

    public Cursor getAllRow (int trackId) {

        String buildSQL = "SELECT * FROM " + TABLE_NAME + " WHERE _id = '" + trackId + "'";

        Log.d(TAG, "getAllRow : " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }


    public class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            this.onCreate(db);
        }
    }
}
