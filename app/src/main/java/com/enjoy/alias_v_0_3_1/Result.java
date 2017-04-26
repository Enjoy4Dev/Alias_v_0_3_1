package com.enjoy.alias_v_0_3_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    int activePlayers, variableScore, variableId, bestScore, bestId;
    Button btnPlayAgain, teamIcon;
    private DB mDb;
    private  DbLogic mDbLogic;
    private static final String PUT_EXTRA_KEY_LAYOUT = "layoutKey";
    private static final int LAYOUT_KEY_RESULT = 4;
    private static final int ID_DB_LOGIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Определяем базы данных
        mDb = new DB(this);
        mDbLogic = new DbLogic(this);


        // Определяем компоненты layout-a
        teamIcon       = (Button) findViewById(R.id.btnTeam);
        btnPlayAgain    = (Button) findViewById(R.id.btnPlayAgain);


        Cursor myCursorActivePlayers = mDbLogic.getActivePlayers(ID_DB_LOGIC);
        while(myCursorActivePlayers.moveToNext()) {
            activePlayers = myCursorActivePlayers.getInt(myCursorActivePlayers.getColumnIndex("players"));
            Log.i("activePlayers", Integer.toString(activePlayers));
        }

        getBestTeam();
        Game.setImage(bestId, teamIcon);
        // Задаем обработчик нажатия, показываем общий счет
        btnPlayAgain.setText("Счет");
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, ScoreTable.class);
                intent.putExtra(PUT_EXTRA_KEY_LAYOUT, LAYOUT_KEY_RESULT);
                startActivity(intent);
            }
        });
    }

    private void getBestTeam (){
        bestScore = 0;
        Log.i("test", Integer.toString(bestScore));
        for (int i = 0; i < activePlayers; i++) {
            Log.i("test2", Integer.toString(i));
            // Получаем данные из БД, через строку
            Cursor myCursorDB = mDb.getScoreFromRow(i + 1);
            while (myCursorDB.moveToNext()) {
                // Заносим данные, игрок - счет
                variableScore = myCursorDB.getInt(myCursorDB.getColumnIndex("score"));
                Log.i("variableScore", Integer.toString(variableScore));
                variableId = myCursorDB.getInt(myCursorDB.getColumnIndex("_id"));
                Log.i("variableId", Integer.toString(variableId));
            }
            if (bestScore < variableScore) {
                Log.i("bestId", Integer.toString(variableId));
                bestScore = variableScore;
                bestId = variableId;
            }
        }
    }

//    // Получаем курсоры
//    public void getCursors (){
//        // Курсоры с логической БД
//        Cursor myCursorDbLogic = mDbLogic.getAllData(ID_DB_LOGIC);
//        while(myCursorDbLogic.moveToNext()) {
//            activeNow = myCursorDbLogic.getInt(myCursorDbLogic.getColumnIndex("activeNow"));
//            repeats = myCursorDbLogic.getInt(myCursorDbLogic.getColumnIndex("repeats"));
//            activePlayers = myCursorDbLogic.getInt(myCursorDbLogic.getColumnIndex("players"));
//        }
//        Log.i("activeNow RESULT", Integer.toString(activeNow));
//        Log.i("repeats RESULT", Integer.toString(repeats));
//        Log.i("activePlayers RESULT", Integer.toString(activePlayers));
//
//        // Курсоры с БД игроков
//        Cursor myCursorDB = mDb.getAllRow(activeNow);
//        while(myCursorDB.moveToNext()) {
//            score = myCursorDB.getInt(myCursorDB.getColumnIndex("score"));
//            rowNumber = myCursorDB.getInt(myCursorDB.getColumnIndex("rowNumber"));
//        }
//        Log.i("score RESULT", Integer.toString(score));
//        Log.i("rowNumber RESULT", Integer.toString(rowNumber));
//    }
}