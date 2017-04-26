
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

public  class      DbLogic{
    public DbHelper mDbHelper;
    public SQLiteDatabase mDb;

    public DbLogic(Context context) {
        mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DbLogic.db";

    private static final String TABLE_NAME = "logic";
    private static final String COLUMN_NAME_PLAYERS = "players";
    private static final String COLUMN_NAME_TUTORIAL = "tutorial";
    private static final String COLUMN_NAME_REPEATS = "repeats";
    private static final String COLUMN_NAME_ACTIVE_NOW = "activeNow";

    private static final String _ID = "_id";
    private static final String TEXT_TYPE = " INT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME_ACTIVE_NOW + TEXT_TYPE + COMMA_SEP
                    + COLUMN_NAME_PLAYERS    + TEXT_TYPE + COMMA_SEP
                    + COLUMN_NAME_TUTORIAL   + TEXT_TYPE + COMMA_SEP
                    + COLUMN_NAME_REPEATS    + TEXT_TYPE
                    + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;




    public void insertDataToLogic (int _id, int activeNow, int players, int tutorial, int repeats) {
        ContentValues cv = new ContentValues();
        cv.put(_ID, _id);
        cv.put(COLUMN_NAME_ACTIVE_NOW, activeNow);
        cv.put(COLUMN_NAME_PLAYERS, players);
        cv.put(COLUMN_NAME_TUTORIAL, tutorial);
        cv.put(COLUMN_NAME_REPEATS, repeats);
        Log.d(TAG, "insertDataToLogic LOGIC" + cv);
        mDb.insert(TABLE_NAME, null, cv);
    }

        public Cursor getActiveNow (int teamId) {

        String buildSQL = "SELECT " + COLUMN_NAME_ACTIVE_NOW + " FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getActiveNow LOGIC: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }

        public Cursor getRepeats (int teamId) {

        String buildSQL = "SELECT " + COLUMN_NAME_REPEATS + " FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getCurrentTrack SQL: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }

    public Cursor getTutorialCondition (int teamId) {

        String buildSQL = "SELECT " + COLUMN_NAME_TUTORIAL + " FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getActiveNow LOGIC: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }


        public Cursor getAllData (int teamId) {

        String buildSQL = "SELECT * FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getAllData LOGIC: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }

    public void setRepeats (int teamId, int teamRepeats) {

        String buildSQL = "UPDATE " + TABLE_NAME + " SET repeats = '" + teamRepeats + "' WHERE _id = '" + teamId + "'";

        Log.d(TAG, "setRepeats LOGIC: " + buildSQL);

        mDb.execSQL(buildSQL);
    }


    public void setActiveNow (int teamId, int activeNow) {

        String buildSQL = "UPDATE " + TABLE_NAME + " SET activeNow = '" + activeNow + "' WHERE _id = '" + teamId + "'";

        Log.d(TAG, "setActiveNow LOGIC: " + buildSQL);

        mDb.execSQL(buildSQL);
    }

    public void setTutorialAgree (int teamId, int tutorialAgree) {

        String buildSQL = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_TUTORIAL + " = '" + tutorialAgree + "' WHERE _id = '" + teamId + "'";

        Log.d(TAG, "setActiveNow LOGIC: " + buildSQL);

        mDb.execSQL(buildSQL);
    }



    public Cursor getActivePlayers (int teamId) {

        String buildSQL = "SELECT " + COLUMN_NAME_PLAYERS + " FROM " + TABLE_NAME + " WHERE _id = '" + teamId + "'";

        Log.d(TAG, "getAllData LOGIC: " + buildSQL);

        return mDb.rawQuery(buildSQL, null);
    }


    public void deleteTable () {

        String buildSQL = "DELETE FROM " + TABLE_NAME;

        Log.d(TAG, "deleteTable" + buildSQL);

        mDb.execSQL(buildSQL);
    }



    public Cursor getAllRow (int trackId) {

        String buildSQL = "SELECT * FROM " + TABLE_NAME + " WHERE _id = '" + trackId + "'";

        Log.d(TAG, "getAllRow SQL: " + buildSQL);

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
