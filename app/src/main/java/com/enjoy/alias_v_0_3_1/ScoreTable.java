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

import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam0;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam1;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam4;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam5;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutRow2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutRow3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutTeam1;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutTeam2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutTeam3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutTeam4;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersLayoutTeam5;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersProgressBar0;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersProgressBar1;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersProgressBar2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersProgressBar3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersProgressBar4;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersProgressBar5;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView0;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView1;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView4;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView5;

public class ScoreTable extends AppCompatActivity {

    public Button btnTeam0, btnTeam1, btnTeam2, btnTeam3, btnTeam4, btnTeam5,  btnNone;


    LinearLayout LLTeam1, LLTeam2, LLTeam3, LLTeam4, LLTeam5, LLRow2, LLRow3;
    String lastWord;

    int score, variableScore, wrongButtonCounter, activeNow, activePlayers, layoutKey, coinYesCounter, coinNoCounter, rowNumber;
    int activeTeam[];

    private DB mDb;
    private  DbLogic mDbLogic;

    int myButtonKey0, myButtonKey1, myButtonKey2, myButtonKey3, myButtonKey4, myButtonKey5;

    private static final int ID_DB_LOGIC = 1;

    private static final int LAYOUT_KEY_GAME = 3;
    private static final int LAYOUT_KEY_RESULT = 4;
    private static final String PUT_EXTRA_KEY_LAST_WORD = "lastWord";
    private static final String PUT_EXTRA_KEY_LAYOUT = "layoutKey";

    TextView tvTeam0, tvTeam1, tvTeam2, tvTeam3, tvTeam4, tvTeam5, tvFinalWord, tvMyTitle;


    ProgressBar progressBar0, progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        // Обнуляем временную переменную для сумированния счета
        variableScore = 0;

        layoutKey = getIntent().getExtras().getInt(PUT_EXTRA_KEY_LAYOUT);
        if (layoutKey == LAYOUT_KEY_GAME) {
            lastWord = getIntent().getExtras().getString(PUT_EXTRA_KEY_LAST_WORD);
        }

        // Определяем базы данных
        mDb = new DB(this);
        mDbLogic = new DbLogic(this);

        // Определяем компоненты layout-a
        LLTeam1             = (LinearLayout) findViewById(contentPlayersLayoutTeam1);
        LLTeam2             = (LinearLayout) findViewById(contentPlayersLayoutTeam2);
        LLTeam3             = (LinearLayout) findViewById(contentPlayersLayoutTeam3);
        LLTeam4             = (LinearLayout) findViewById(contentPlayersLayoutTeam4);
        LLTeam5             = (LinearLayout) findViewById(contentPlayersLayoutTeam5);
        LLRow2              = (LinearLayout) findViewById(contentPlayersLayoutRow2);
        LLRow3              = (LinearLayout) findViewById(contentPlayersLayoutRow3);



        btnTeam0            = (Button) findViewById(contentPlayersBtnTeam0);
        btnTeam1            = (Button) findViewById(contentPlayersBtnTeam1);
        btnTeam2            = (Button) findViewById(contentPlayersBtnTeam2);
        btnTeam3            = (Button) findViewById(contentPlayersBtnTeam3);
        btnTeam4            = (Button) findViewById(contentPlayersBtnTeam4);
        btnTeam5            = (Button) findViewById(contentPlayersBtnTeam5);
        btnNone             = (Button) findViewById(R.id.btnNone);

        tvFinalWord         = (TextView) findViewById(R.id.tvFinalWord);
        tvMyTitle         = (TextView) findViewById(R.id.myTitle);
        tvTeam0         = (TextView) findViewById(contentPlayersTextView0);
        tvTeam1         = (TextView) findViewById(contentPlayersTextView1);
        tvTeam2         = (TextView) findViewById(contentPlayersTextView2);
        tvTeam3         = (TextView) findViewById(contentPlayersTextView3);
        tvTeam4         = (TextView) findViewById(contentPlayersTextView4);
        tvTeam5         = (TextView) findViewById(contentPlayersTextView5);

        progressBar0    = (ProgressBar) findViewById(contentPlayersProgressBar0);
        progressBar1    = (ProgressBar) findViewById(contentPlayersProgressBar1);
        progressBar2    = (ProgressBar) findViewById(contentPlayersProgressBar2);
        progressBar3    = (ProgressBar) findViewById(contentPlayersProgressBar3);
        progressBar4    = (ProgressBar) findViewById(contentPlayersProgressBar4);
        progressBar5    = (ProgressBar) findViewById(contentPlayersProgressBar5);


        if (layoutKey == LAYOUT_KEY_RESULT){
            tvMyTitle.setVisibility(View.GONE);
            tvFinalWord.setVisibility(View.GONE);
        }

        // Определяем массив
        activeTeam = new int[6];

//        Log.i("activePlayers", Integer.toString(activePlayers));

        // Получаем курсоры
        Cursor myCursorActiveNow = mDbLogic.getActiveNow(ID_DB_LOGIC);
        while(myCursorActiveNow.moveToNext()) {
            activeNow = myCursorActiveNow.getInt(myCursorActiveNow.getColumnIndex("activeNow"));
        }
        Cursor myCursorActivePlayers = mDbLogic.getActivePlayers(ID_DB_LOGIC);
        while(myCursorActivePlayers.moveToNext()) {
            activePlayers = myCursorActivePlayers.getInt(myCursorActivePlayers.getColumnIndex("players"));
        }

//        Cursor myCursorDB = mDb.getRow(activeNow);
//        while (myCursorDB.moveToNext()) {
//            rowNumber = myCursorDB.getInt(myCursorDB.getColumnIndex("rowNumber"));
//        }
        Log.i("rowNumber RESULT", Integer.toString(rowNumber));
        wordForAll();

    }

    // Обработчик присвоения последнего очка
    public void wordForAll(){
        // Скрываем окно игры, открываем окно с игроками

        // Показываем последнее слово на общее угадывание
        tvFinalWord.setText(lastWord);



        // Создаем цикл по числу игроков
        for (int i = 0; i < activePlayers; i++) {
            // Получаем ID игрока через его строку
            Cursor myCursorId = mDb.getId(i + 1);
            while(myCursorId.moveToNext()) {
                activeTeam[i] = myCursorId.getInt(myCursorId.getColumnIndex("_id"));
                Log.i("Мой ID", Integer.toString(activeTeam[i]));
            }
            // Присваиваем кнопке изображение соответствующего игрока
            Game.setImage(activeTeam[i], myButton1(i, activeTeam[i]));
            setScore(activeTeam[i], i);
            if (layoutKey == LAYOUT_KEY_GAME) {
                setScoreToProgressBar(activeTeam[i], i);
            }
            // Задаем кнопкам обработчик нажатия
            if (layoutKey == LAYOUT_KEY_GAME) {
                btnTeam0.setOnClickListener(teamButtonListener);
                btnTeam1.setOnClickListener(teamButtonListener);
                btnTeam2.setOnClickListener(teamButtonListener);
                btnTeam3.setOnClickListener(teamButtonListener);
                btnTeam4.setOnClickListener(teamButtonListener);
                btnTeam5.setOnClickListener(teamButtonListener);
                btnNone.setOnClickListener(teamButtonListener);
            }
            else if (layoutKey == LAYOUT_KEY_RESULT){
                //Игра завершена, выходим
                btnNone.setText("Конец");
                btnNone.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(ScoreTable.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            }
        }
        // Скрываем неактивных игроков
        hideTeams();
    }

    // Настройка связи кнопка - команда
    // В зависимости от количества активных игроков меняется разметка таблицы
    public Button myButton1(int myRow, int myId){
        if (activePlayers == 1) {
            if      (myRow == 0){myButtonKey0 = myId; return btnTeam0;}
            else{return null;}
        }
        if (activePlayers == 2) {
            if      (myRow == 0){myButtonKey0 = myId; return btnTeam0;}
            else if (myRow == 1){myButtonKey2 = myId; return btnTeam2;}
            else{return null;}
        }
        if (activePlayers == 3){
            if      (myRow == 0){myButtonKey0 = myId; return btnTeam0;}
            else if (myRow == 1){myButtonKey2 = myId; return btnTeam2;}
            else if (myRow == 2){myButtonKey4 = myId; return btnTeam4;}
            else{return null;}
        }
        if (activePlayers >= 4  && activePlayers <= 6){
            if      (myRow == 0){myButtonKey0 = myId; return btnTeam0;}
            else if (myRow == 1){myButtonKey1 = myId; return btnTeam1;}
            else if (myRow == 2){myButtonKey2 = myId; return btnTeam2;}
            else if (myRow == 3){myButtonKey3 = myId; return btnTeam3;}
            else if (myRow == 4){myButtonKey4 = myId; return btnTeam4;}
            else if (myRow == 5){myButtonKey5 = myId; return btnTeam5;}
            else{return null;}
        }
        else{return null;}
    }

    public TextView myTextView(int myRow){
        if (activePlayers == 1) {
            if      (myRow == 0){return tvTeam0;}
            else{return null;}
        }
        if (activePlayers == 2) {
            if      (myRow == 0){return tvTeam0;}
            else if (myRow == 1){return tvTeam2;}
            else{return null;}
        }
        if (activePlayers == 3){
            if      (myRow == 0){return tvTeam0;}
            else if (myRow == 1){return tvTeam2;}
            else if (myRow == 2){return tvTeam4;}
            else{return null;}
        }
        if (activePlayers >= 4  && activePlayers <= 6){
            if      (myRow == 0){return tvTeam0;}
            else if (myRow == 1){return tvTeam1;}
            else if (myRow == 2){return tvTeam2;}
            else if (myRow == 3){return tvTeam3;}
            else if (myRow == 4){return tvTeam4;}
            else if (myRow == 5){return tvTeam5;}
            else{return null;}
        }
        else{return null;}
    }

    public ProgressBar myProgressBar (int myRow){
        if (activePlayers == 1) {
            if      (myRow == 0){progressBar0.setVisibility(View.VISIBLE); return progressBar0;}
            else{return null;}
        }
        if (activePlayers == 2) {
            if      (myRow == 0){progressBar0.setVisibility(View.VISIBLE); return progressBar0;}
            else if (myRow == 1){progressBar2.setVisibility(View.VISIBLE); return progressBar2;}
            else{return null;}
        }
        if (activePlayers == 3){
            if      (myRow == 0){progressBar0.setVisibility(View.VISIBLE); return progressBar0;}
            else if (myRow == 1){progressBar2.setVisibility(View.VISIBLE); return progressBar2;}
            else if (myRow == 2){progressBar4.setVisibility(View.VISIBLE); return progressBar4;}
            else{return null;}
        }
        if (activePlayers >= 4  && activePlayers <= 6){
            if      (myRow == 0){progressBar0.setVisibility(View.VISIBLE); return progressBar0;}
            else if (myRow == 1){progressBar1.setVisibility(View.VISIBLE); return progressBar1;}
            else if (myRow == 2){progressBar2.setVisibility(View.VISIBLE); return progressBar2;}
            else if (myRow == 3){progressBar3.setVisibility(View.VISIBLE); return progressBar3;}
            else if (myRow == 4){progressBar4.setVisibility(View.VISIBLE); return progressBar4;}
            else if (myRow == 5){progressBar5.setVisibility(View.VISIBLE); return progressBar5;}
            else{return null;}
        }
        else{return null;}
    }

    public void setScore (int myId, int myRow){
        Cursor myCursorScore = mDb.getScore(myId);
        while(myCursorScore.moveToNext()) {
            // Заносим его в переменную временного счета
            score = myCursorScore.getInt(myCursorScore.getColumnIndex("score"));
        }
//        myTextView(myRow).setText(Integer.toString(score));
        Game.setTextWithParams(myTextView(myRow), score);
    }

    public void setScoreToProgressBar (int myId, int myRow){
        Cursor myCursorScore = mDb.getScore(myId);
        while(myCursorScore.moveToNext()) {
            // Заносим его в переменную временного счета
            score = myCursorScore.getInt(myCursorScore.getColumnIndex("score"));
        }
        myProgressBar(myRow).setProgress(score);
    }



    // Обрабочтик картинки соответствующей команды
//    public static void setImage(int imageTeam, Button myButton){
//        if (imageTeam == 0){ myButton.setBackgroundResource(R.drawable.icon_bear);}
//        if (imageTeam == 1){ myButton.setBackgroundResource(R.drawable.icon_raccoon);}
//        if (imageTeam == 2){ myButton.setBackgroundResource(R.drawable.icon_wolf);}
//        if (imageTeam == 3){ myButton.setBackgroundResource(R.drawable.icon_panda);}
//        if (imageTeam == 4){ myButton.setBackgroundResource(R.drawable.icon_fox);}
//        if (imageTeam == 5){ myButton.setBackgroundResource(R.drawable.icon_cat);}
//    }

    // Скрываем лишние команды
    public void hideTeams(){
        if (activePlayers == 1){
            LLTeam1.setVisibility(View.GONE);
            LLTeam2.setVisibility(View.GONE);
            LLRow2.setVisibility(View.GONE);
            LLRow3.setVisibility(View.GONE);
        }
        else if (activePlayers == 2){
            LLTeam1.setVisibility(View.GONE);
            LLTeam3.setVisibility(View.GONE);
            LLRow3.setVisibility(View.GONE);
        }
        else if (activePlayers == 3){
            LLTeam1.setVisibility(View.GONE);
            LLTeam3.setVisibility(View.GONE);
            LLTeam5.setVisibility(View.GONE);
        }
        else if (activePlayers == 4){
            LLRow3.setVisibility(View.GONE);
        }
        else if (activePlayers == 5){
            LLTeam5.setVisibility(View.INVISIBLE);
        }

    }

    //Обработчик присвоения последнего очка
    View.OnClickListener teamButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // Проверка нажатой кнопки по ID
                case R.id.contentPlayersBtnTeam0:
                    listenerContent(myButtonKey0, true);
                    break;
                case R.id.contentPlayersBtnTeam1:
                    listenerContent(myButtonKey1, true);
                    break;
                case R.id.contentPlayersBtnTeam2:
                    listenerContent(myButtonKey2, true);
                    break;
                case R.id.contentPlayersBtnTeam3:
                    listenerContent(myButtonKey3, true);
                    break;
                case R.id.contentPlayersBtnTeam4:
                    listenerContent(myButtonKey4, true);
                    break;
                case R.id.contentPlayersBtnTeam5:
                    listenerContent(myButtonKey5, true);
                    break;
                // Если никто не угадал просто открываем след. активность
                case R.id.btnNone:
                    listenerContent(99, false);
                    break;
            }
        }
    };
    private void listenerContent (int myButtonKey, boolean playerKey){
        Log.i("listenerContent1", "1");
        Intent intent = new Intent(ScoreTable.this, Game.class);
        Intent resultIntent = new Intent(ScoreTable.this, Result.class);
        Log.i("listenerContent2", "2");
        if (playerKey) {
            Log.i("listenerContent3", "3");
            // Получаем общий счет через его ID
            Cursor myCursorScore1 = mDb.getScore(myButtonKey);
            while (myCursorScore1.moveToNext()) {
                variableScore = myCursorScore1.getInt(myCursorScore1.getColumnIndex("score"));
            }
            // Добавляем 1 очко угадавшему
            ++variableScore;
            // Заносим новый счет в БД
            mDb.setScore(myButtonKey, variableScore);
            // Если есть победитель отправляем на Result.class

        }
        if (variableScore < 50){
            Log.i("listenerContent4", "4");
            startActivity(intent);
            finish();
        }
        else if (variableScore >= 50) {
            Log.i("listenerContent5", "5");
            startActivity(resultIntent);
            finish();
        }

    }
//    // Задаем новую строку, для определения activePlayer
//    private void getNewRowNumber() {
//        // Если в таблице есть еще игроки то выбираем следующего
//        if (rowNumber < activePlayers) {
//            ++rowNumber;
//            Log.i("rowNumber < Players", Integer.toString(rowNumber));
//        }
//        // Если это последний игрок, то выбираем первого
//        else if (rowNumber == activePlayers) {
//            rowNumber = 1;
//            Log.i("rowNumber = Players", Integer.toString(rowNumber));
//        }
//    }
//    // Получаем нового активного игрока через новую строку полученную ранее
//    private void getNewActiveTeam(int newRowNumber) {
//        Cursor myCursorId = mDb.getId(newRowNumber);
//        while (myCursorId.moveToNext()) {
//            activeNow = myCursorId.getInt(myCursorId.getColumnIndex("_id"));
//        }
//        Log.i("New active team", Integer.toString(activeNow));
//    }
}
