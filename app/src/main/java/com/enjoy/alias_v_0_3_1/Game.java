package com.enjoy.alias_v_0_3_1;


import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.enjoy.alias_v_0_3_1.R.id.btnNone;
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
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView0;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView1;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView4;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersTextView5;
import static com.enjoy.alias_v_0_3_1.R.id.gameTutorialSecondIV;
import static com.enjoy.alias_v_0_3_1.R.id.none;
import static com.enjoy.alias_v_0_3_1.R.id.wrap_content;


public class Game extends AppCompatActivity {

    private static final int MILLIS_PER_SECOND = 1000;
    private static final int SECONDS_TO_COUNTDOWN = 50;
    private static final int SCORE_FOR_WIN = 30;
    private static final int ID_DB_LOGIC = 1;
    private static final boolean MY_TIMER_KEY_SHOW = true;
    private static final boolean MY_TIMER_KEY_NONE = false;
    private static final int LAYOUT_KEY_GAME = 3;
    private static final String PUT_EXTRA_KEY_LAST_WORD = "lastWord";
    private static final String PUT_EXTRA_KEY_LAYOUT = "layoutKey";

    private CountDownTimer timer;
    private static long back_pressed;

    private int score, variableScore, totalScore, activeNow, activePlayers,
            coinYesCounter, coinNoCounter, rowNumber, tutorialAgree, activityCondition;
    private int activeTeam[];
    int myButtonKey0, myButtonKey1, myButtonKey2, myButtonKey3, myButtonKey4, myButtonKey5;
    private int timeLeft;

    private String[] words;

    private boolean myTimerKey;

    private RelativeLayout  RLOnStart, RLInGame, RLShowScore, RLGameButtonRight, RLGameButtonWrong,
            RLTutorial, RLSecondTutorial, RLTimeDisplayBox, RLTvWords, RLCoin;
    LinearLayout LLTeam1, LLTeam2, LLTeam3, LLTeam4, LLTeam5, LLRow2, LLRow3, LLProgressBar;

    Button btnStartIcon, btnBottom, btnRight, btnWrong, btnShowScore, btnAfterGameIcon,
            btnTutorialTeamIcon, btnTutorialShowScore;
    Button btnTeam0, btnTeam1, btnTeam2, btnTeam3, btnTeam4, btnTeam5, btnNone;

    private ImageView coinYes1, coinYes2, coinYes3, coinYes4, coinYes5, coinNo1, coinNo2, coinNo3, coinNo4, coinNo5, arrow, coinImage,
            gameTutorialIV, gameTutorialSecondIV;

    TextView tvWords, tvCountdownDisplay, tvFinalWord, tvScore, tvFinalScore, tvMaxScore, tvTutorial,tvSecondTutorial, tvWordsCopy;
    private TextView tvTeam0, tvTeam1, tvTeam2, tvTeam3, tvTeam4, tvTeam5;

    private ProgressBar progressBar;

    private DB mDb;
    private  DbLogic mDbLogic;

    Animation coinAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Обнуляем временную переменную для сумированния счета
        variableScore = 0;
        coinNoCounter = 0;
        coinYesCounter = 0;
        activityCondition = 0;

        // Определяем базы данных
        mDb = new DB(this);
        mDbLogic = new DbLogic(this);

        // Получаем курсоры
        Cursor myCursorActiveNow = mDbLogic.getActiveNow(ID_DB_LOGIC);
        while(myCursorActiveNow.moveToNext()) {
            activeNow = myCursorActiveNow.getInt(myCursorActiveNow.getColumnIndex("activeNow"));
        }
        Cursor myCursorActivePlayers = mDbLogic.getActivePlayers(ID_DB_LOGIC);
        while(myCursorActivePlayers.moveToNext()) {
            activePlayers = myCursorActivePlayers.getInt(myCursorActivePlayers.getColumnIndex("players"));
                    Log.i("activePlayers", Integer.toString(activePlayers));
        }
        Cursor myCursorTutorial = mDbLogic.getTutorialCondition(ID_DB_LOGIC);
        while(myCursorTutorial.moveToNext()) {
            tutorialAgree = myCursorTutorial.getInt(myCursorTutorial.getColumnIndex("tutorial"));
        }


        // Определяем компоненты layout-a
        btnBottom           = (Button) findViewById(R.id.bottomButton);
        btnRight            = (Button) findViewById(R.id.btnRight);
        btnWrong            = (Button) findViewById(R.id.btnWrong);
        btnStartIcon        = (Button) findViewById(R.id.btnTeam);
        btnShowScore        = (Button) findViewById(R.id.btnShowScore);
        btnTutorialShowScore= (Button) findViewById(R.id.btnShowScoreTutorial);
        btnAfterGameIcon    = (Button) findViewById(R.id.teamIconAfterGame);
        btnTutorialTeamIcon = (Button) findViewById(R.id.gameTutorialButtonTeam);
        btnTeam0            = (Button) findViewById(contentPlayersBtnTeam0);
        btnTeam1            = (Button) findViewById(contentPlayersBtnTeam1);
        btnTeam2            = (Button) findViewById(contentPlayersBtnTeam2);
        btnTeam3            = (Button) findViewById(contentPlayersBtnTeam3);
        btnTeam4            = (Button) findViewById(contentPlayersBtnTeam4);
        btnTeam5            = (Button) findViewById(contentPlayersBtnTeam5);
        btnNone             = (Button) findViewById(R.id.btnNone);

        RLOnStart           = (RelativeLayout) findViewById(R.id.onStart);
        RLInGame            = (RelativeLayout) findViewById(R.id.inGame);
        RLShowScore         = (RelativeLayout) findViewById(R.id.RLSmallScore);
        RLGameButtonRight   = (RelativeLayout) findViewById(R.id.gameButtonRightRL);
        RLGameButtonWrong   = (RelativeLayout) findViewById(R.id.gameButtonWrongRL);
        RLTutorial          = (RelativeLayout) findViewById(R.id.gameTutorialLayout);
        RLSecondTutorial    = (RelativeLayout) findViewById(R.id.gameTutorialSecondLayout);
        RLTimeDisplayBox    = (RelativeLayout) findViewById(R.id.time_display_box_RL);
        RLTvWords           = (RelativeLayout) findViewById(R.id.tvWords_RL);
        RLCoin              = (RelativeLayout) findViewById(R.id.coinRL);

        LLTeam1             = (LinearLayout) findViewById(contentPlayersLayoutTeam1);
        LLTeam2             = (LinearLayout) findViewById(contentPlayersLayoutTeam2);
        LLTeam3             = (LinearLayout) findViewById(contentPlayersLayoutTeam3);
        LLTeam4             = (LinearLayout) findViewById(contentPlayersLayoutTeam4);
        LLTeam5             = (LinearLayout) findViewById(contentPlayersLayoutTeam5);
        LLRow2              = (LinearLayout) findViewById(contentPlayersLayoutRow2);
        LLRow3              = (LinearLayout) findViewById(contentPlayersLayoutRow3);
        LLProgressBar       = (LinearLayout) findViewById(R.id.layoutProgressBar);

        coinYes1            = (ImageView) findViewById(R.id.coin_yes1);
        coinYes2            = (ImageView) findViewById(R.id.coin_yes2);
        coinYes3            = (ImageView) findViewById(R.id.coin_yes3);
        coinYes4            = (ImageView) findViewById(R.id.coin_yes4);
        coinYes5            = (ImageView) findViewById(R.id.coin_yes5);
        coinNo1             = (ImageView) findViewById(R.id.coin_no1);
        coinNo2             = (ImageView) findViewById(R.id.coin_no2);
        coinNo3             = (ImageView) findViewById(R.id.coin_no3);
        coinNo4             = (ImageView) findViewById(R.id.coin_no4);
        coinNo5             = (ImageView) findViewById(R.id.coin_no5);
        arrow               = (ImageView) findViewById(R.id.arrow);
        coinImage           = (ImageView) findViewById(R.id.coinImage);
        gameTutorialIV      = (ImageView) findViewById(R.id.gameTutorialIV);
        gameTutorialSecondIV= (ImageView) findViewById(R.id.gameTutorialSecondIV);

        tvScore             = (TextView) findViewById(R.id.tvScore);
        tvWords             = (TextView) findViewById(R.id.tvWords);
        tvFinalWord         = (TextView) findViewById(R.id.tvFinalWord);
        tvCountdownDisplay  = (TextView) findViewById(R.id.time_display_box);
        tvFinalScore        = (TextView) findViewById(R.id.tvResultScore);
        tvMaxScore          = (TextView) findViewById(R.id.maxScore);
        tvTutorial          = (TextView) findViewById(R.id.gameTutorialText);
        tvSecondTutorial    = (TextView) findViewById(R.id.gameTutorialSecondText);
        tvWordsCopy         = (TextView) findViewById(R.id.twSubWords);
        tvTeam0             = (TextView) findViewById(contentPlayersTextView0);
        tvTeam1             = (TextView) findViewById(contentPlayersTextView1);
        tvTeam2             = (TextView) findViewById(contentPlayersTextView2);
        tvTeam3             = (TextView) findViewById(contentPlayersTextView3);
        tvTeam4             = (TextView) findViewById(contentPlayersTextView4);
        tvTeam5             = (TextView) findViewById(contentPlayersTextView5);

        progressBar         = (ProgressBar) findViewById(R.id.progressBar);

        // Вставляем картинку соответствующей команды
        setImage(activeNow, btnStartIcon);

        // Если игрок подтвердил обучение - запускаем
        if (tutorialAgree == 1){
            showTutorial();
        }

//        getTotalScore();
//        setProgressBarOptions(totalScore);

        // Определяем массив
        activeTeam = new int[6];
        displayScoreBox();

        // Наполняем игу словами
        Resources res = getResources();
        words = res.getStringArray(R.array.words);

        // Вносим счет
        setTextWithParams(tvScore, score);

        // Обработчик кнопки "Показать счет"
        btnShowScore.setOnClickListener(showScoreOnClickListener);

        // Задаем обработчик нажатия кнопке "Старт"
        btnBottom.setOnClickListener(btnBottomFirstOnClickListener);

    }









    //*****************************************************ПРОЦЕСС ИГРЫ*****************************************************

    // Обработчик таймера
    private void showTimer(int countdownMillis) {

        // Создаем архитектуру таймера
        if(timer != null) { timer.cancel(); }


        // Устанавливаем шаг для таймера длиной в одну секунду
        timer = new CountDownTimer(countdownMillis, MILLIS_PER_SECOND) {
            @Override
            // Каждый тик (1 секунда)
            public void onTick(long millisUntilFinished) {
                // Показываем игроку оставшееся время
                tvCountdownDisplay.setText("" + millisUntilFinished / MILLIS_PER_SECOND);
                // За три секунды до окончания времени задаем ключу состояние true
                // Если напарник не угадывает это слово - оно выносится на общее угадывание
                if (millisUntilFinished / MILLIS_PER_SECOND == 2){myTimerKey = MY_TIMER_KEY_SHOW;}

                long timeLeftLong;
                timeLeftLong = millisUntilFinished / MILLIS_PER_SECOND;
                timeLeft = (int) (long) timeLeftLong;
            }

            // Когда время закончилось
            @Override
            public void onFinish() {
                getTotalScore();
                if (myTimerKey == MY_TIMER_KEY_SHOW && totalScore >= SCORE_FOR_WIN) {
                    myTimerKey = MY_TIMER_KEY_NONE;
                }
                // Курсоры с БД игроков
                Cursor myCursorDB = mDb.getRow(activeNow);
                while (myCursorDB.moveToNext()) {
                    rowNumber = myCursorDB.getInt(myCursorDB.getColumnIndex("rowNumber"));
                }
                // Если слово выносится на общее уганывание, то вызываем класс ScoreTable.java
                if (myTimerKey == MY_TIMER_KEY_SHOW && totalScore < SCORE_FOR_WIN){
                    Intent intent = new Intent(Game.this, ScoreTable.class);
                    intent.putExtra(PUT_EXTRA_KEY_LAST_WORD, tvWords.getText());
                    intent.putExtra(PUT_EXTRA_KEY_LAYOUT, LAYOUT_KEY_GAME);
                    // Устанавливаем нового активного игрока и заносим в БД
                    getNewRowNumber();
                    getNewActiveTeam(rowNumber);
                    Log.i("rowNumber RESULT", Integer.toString(rowNumber));
                    mDbLogic.setActiveNow(ID_DB_LOGIC, activeNow);

                    startActivity(intent);
                    finish();

                }
                // Если слово не выносится на общее уганывание, то заканчиваем ход
                else if (myTimerKey == MY_TIMER_KEY_NONE){
                    visibilityAfterGame();
                    btnBottom.setText("Далее");
                    setImage(activeNow, btnAfterGameIcon);
                    // Устанавливаем нового активного игрока и заносим в БД
//                    getTotalScore();



                    setProgressBarOptions(totalScore);
//                    mDb.setScore(activeNow, totalScore);
                    getNewRowNumber();
                    getNewActiveTeam(rowNumber);
                    Log.i("rowNumber RESULT", Integer.toString(rowNumber));
                    mDbLogic.setActiveNow(ID_DB_LOGIC, activeNow);
                    btnBottom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Если победителя еще нет
                            if (totalScore < SCORE_FOR_WIN) {
                                // Задаем Intent
                                Intent intent = new Intent(Game.this, Game.class);
                                // Запускаем игру заново
                                startActivity(intent);
                                finish();
                            }
                            //Если есть победитель
                            else if (totalScore >= SCORE_FOR_WIN){
                                Intent intent = new Intent(Game.this, Result.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        // Активируем таймер
        }.start();
    }





    private void visibilityAfterGame(){
        tvCountdownDisplay.setVisibility(View.INVISIBLE);
        tvWords.setVisibility(View.INVISIBLE);
        RLGameButtonRight.setVisibility(View.GONE);
        RLGameButtonWrong.setVisibility(View.GONE);
        btnBottom.setVisibility(View.VISIBLE);
        btnAfterGameIcon.setVisibility(View.VISIBLE);
        coinImage.setVisibility(View.GONE);
        tvScore.setVisibility(View.GONE);
    }

    private void setProgressBarOptions(int progressScore){

        setTextWithParams(tvFinalScore, progressScore);
        LLProgressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(progressScore);
        progressBar.setMax(SCORE_FOR_WIN);
        tvMaxScore.setText(Integer.toString(SCORE_FOR_WIN));
    }

    private void getTotalScore(){
        // Получаем счет уже набранный этой командой
        Cursor myCursorScore = mDb.getScore(activeNow);
        while(myCursorScore.moveToNext()) {
            // Заносим его в переменную временного счета
            variableScore = myCursorScore.getInt(myCursorScore.getColumnIndex("score"));
        }
        // Суммируем счет этой игрый со старым и заносим новый общий счет в БД
        totalScore = variableScore + score;
        mDb.setScore(activeNow, totalScore);
    }
    // Задаем новую строку, для определения activePlayer
    private void getNewRowNumber() {
        // Если в таблице есть еще игроки то выбираем следующего
        if (rowNumber < activePlayers) {
            ++rowNumber;
            Log.i("rowNumber < Players", Integer.toString(rowNumber));
        }
        // Если это последний игрок, то выбираем первого
        else if (rowNumber == activePlayers) {
            rowNumber = 1;
            Log.i("rowNumber = Players", Integer.toString(rowNumber));
        }
    }

    // Получаем нового активного игрока через новую строку полученную ранее
    private void getNewActiveTeam(int newRowNumber) {
        Cursor myCursorId = mDb.getId(newRowNumber);
        while (myCursorId.moveToNext()) {
            activeNow = myCursorId.getInt(myCursorId.getColumnIndex("_id"));
        }
        Log.i("New active team", Integer.toString(activeNow));
    }

    public void setCoin (View myView){
        switch (myView.getId()) {
            // Проверка нажатой кнопки по ID
            case R.id.btnRight:
                coinAnimation = AnimationUtils.loadAnimation(this, R.anim.coin_yes);
                myCoin(coinYesCounter, true).startAnimation(coinAnimation);
                break;
            case R.id.btnWrong:
                coinAnimation = AnimationUtils.loadAnimation(this, R.anim.coin_no);
                myCoin(coinNoCounter, false).startAnimation(coinAnimation);
                break;

        }
    }

    public ImageView myCoin (int myCoin, boolean myCounter){
        if      (myCoin == 1 && myCounter){Log.i("coinYes1", Integer.toString(myCoin)); return coinYes1;}
        else if (myCoin == 2 && myCounter){return coinYes2;}
        else if (myCoin == 3 && myCounter){return coinYes3;}
        else if (myCoin == 4 && myCounter){return coinYes4;}
        else if (myCoin == 5 && myCounter){return coinYes5;}
        else if (myCoin == 1 && !myCounter){Log.i("coinNo1", Integer.toString(myCoin)); return coinNo1;}
        else if (myCoin == 2 && !myCounter){Log.i("coinNo2", Integer.toString(myCoin)); return coinNo2;}
        else if (myCoin == 3 && !myCounter){Log.i("coinNo3", Integer.toString(myCoin)); return coinNo3;}
        else if (myCoin == 4 && !myCounter){Log.i("coinNo4", Integer.toString(myCoin)); return coinNo4;}
        else if (myCoin == 5 && !myCounter){Log.i("coinNo5", Integer.toString(myCoin)); return coinNo5;}
        else{Log.i("coinYes", "none"); return null;}
    }












    //*****************************************************ВСПЛЫВАЮЩЕЕ ОКНО*****************************************************

    // Обработчик присвоения последнего очка
    public void displayScoreBox(){
        // Создаем цикл по числу игроков
        for (int i = 0; i < activePlayers; i++) {
            // Получаем ID игрока через его строку
            Cursor myCursorId = mDb.getId(i + 1);
            while(myCursorId.moveToNext()) {
                activeTeam[i] = myCursorId.getInt(myCursorId.getColumnIndex("_id"));
                Log.i("Мой ID", Integer.toString(activeTeam[i]));
            }
            // Присваиваем кнопке изображение соответствующего игрока
            setImage(activeTeam[i], myButton(i, activeTeam[i]));
            setScore(activeTeam[i], i);
        }

        // Скрываем неактивных игроков
        hideTeams();
    }

    // Настройка связи кнопка - команда
    // В зависимости от количества активных игроков меняется разметка таблицы
    public Button myButton(int myRow, int myId){
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

    // Скрываем лишние команды
    public void hideTeams(){
        if (activePlayers == 1){
            RLShowScore.getLayoutParams().height = (int) getResources().getDimension(R.dimen.game_small_score_height_100dp);
            LLTeam1.setVisibility(View.GONE);
            LLTeam2.setVisibility(View.GONE);
            LLRow2.setVisibility(View.GONE);
            LLRow3.setVisibility(View.GONE);
        }
        else if (activePlayers == 2){
            RLShowScore.getLayoutParams().height = (int) getResources().getDimension(R.dimen.game_small_score_height_200dp);
            LLTeam1.setVisibility(View.GONE);
            LLTeam3.setVisibility(View.GONE);
            LLRow3.setVisibility(View.GONE);
        }
        else if (activePlayers == 3){
            RLShowScore.getLayoutParams().height = (int) getResources().getDimension(R.dimen.game_small_score_height_300dp);
            LLTeam1.setVisibility(View.GONE);
            LLTeam3.setVisibility(View.GONE);
            LLTeam5.setVisibility(View.GONE);
        }
        else if (activePlayers == 4){
            RLShowScore.getLayoutParams().height = (int) getResources().getDimension(R.dimen.game_small_score_height_200dp);
            LLRow3.setVisibility(View.GONE);
        }
        else if (activePlayers == 5){
            RLShowScore.getLayoutParams().height = (int) getResources().getDimension(R.dimen.game_small_score_height_300dp);
            LLTeam5.setVisibility(View.INVISIBLE);
        }
        else if (activePlayers == 6){
            RLShowScore.getLayoutParams().height = (int) getResources().getDimension(R.dimen.game_small_score_height_300dp);
        }

    }

    public void setScore (int myId, int myRow){
        Cursor myCursorScore = mDb.getScore(myId);
        while(myCursorScore.moveToNext()) {
            // Заносим его в переменную временного счета
            score = myCursorScore.getInt(myCursorScore.getColumnIndex("score"));
        }
        setTextWithParams(myTextView(myRow), score);
//        myTextView(myRow).setText(Integer.toString(score));
        myTVColor(myTextView(myRow));
        score = 0;
    }
    // Изменяем размер окна в зависимости от количества игроков
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
    private void myTVColor (TextView tv){
        tv.setTextColor(getResources().getColor(R.color.textColorWhite));
    }
    View.OnClickListener showScoreOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tutorialAgree ==1){
                tutorialThirdClickListener();
            }
            Animation scoreOpenAnimation = AnimationUtils.loadAnimation(Game.this, R.anim.show_score_open);
            RLShowScore.setVisibility(View.VISIBLE);
            RLShowScore.startAnimation(scoreOpenAnimation);
            scoreOpenAnimation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {

                    Animation arrowHideAnimation = AnimationUtils.loadAnimation(Game.this, R.anim.arrow_alpha_hide);
                    getBtnShowScore().startAnimation(arrowHideAnimation);
                    getBtnShowScore().setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    Animation arrowShowAnimation = AnimationUtils.loadAnimation(Game.this, R.anim.arrow_alpha_show);
                    arrow.startAnimation(arrowShowAnimation);
                    arrow.setVisibility(View.VISIBLE);
                    RLShowScore.setOnClickListener(hideScoreOnClickListener);
                    btnTeam0.setOnClickListener(hideScoreOnClickListener);
                    btnTeam1.setOnClickListener(hideScoreOnClickListener);
                    btnTeam2.setOnClickListener(hideScoreOnClickListener);
                    btnTeam3.setOnClickListener(hideScoreOnClickListener);
                    btnTeam4.setOnClickListener(hideScoreOnClickListener);
                    btnTeam5.setOnClickListener(hideScoreOnClickListener);
                }
            });
        }
    };
    // Анимация
    View.OnClickListener hideScoreOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideScore();
        }
    };
    private void hideScore(){
        if (tutorialAgree ==1){
            tutorialForthClickListener();
            btnTutorialShowScore.setOnClickListener(null);
        }
        RLShowScore.setOnClickListener(null);
        Animation scoreCloseAnimation = AnimationUtils.loadAnimation(Game.this, R.anim.show_score_close);
        RLShowScore.startAnimation(scoreCloseAnimation);
        scoreCloseAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                Animation arrowHideAnimation = AnimationUtils.loadAnimation(Game.this, R.anim.arrow_alpha_hide);
                arrow.startAnimation(arrowHideAnimation);
                arrow.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                RLShowScore.setVisibility(View.GONE);
                Animation arrowShowAnimation = AnimationUtils.loadAnimation(Game.this, R.anim.arrow_alpha_show);
                btnShowScore.startAnimation(arrowShowAnimation);
                btnShowScore.setVisibility(View.VISIBLE);
            }
        });
    }
    private Button getBtnShowScore(){
        if (tutorialAgree == 0){return btnShowScore;}
        else if (tutorialAgree == 1){return btnTutorialShowScore;}
        else{return null;}
    }















    //*****************************************************ОБУЧЕНИЕ*****************************************************
    private void showTutorial (){
        RLTutorial.setVisibility(View.VISIBLE);
        tvTutorial.setText("Тут можно узнать всю\nинформацию о игре");
        RLTutorial.setOnClickListener(tutorialFirstClickListener);
    }
    private View.OnClickListener tutorialFirstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tvTutorial.setText("Это активный игрок");

            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(px, px);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            layoutParams.setMargins(marginPx, 0, 0, 0);
            btnTutorialTeamIcon.setLayoutParams(layoutParams);
            setImage(activeNow, btnTutorialTeamIcon);

            btnTutorialTeamIcon.setOnClickListener(tutorialSecondClickListener);
            RLTutorial.setOnClickListener(tutorialSecondClickListener);
        }
    };

    private View.OnClickListener tutorialSecondClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int pxZero = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(pxZero, pxZero);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            btnTutorialTeamIcon.setLayoutParams(layoutParams);

            gameTutorialIV.setVisibility(View.GONE);
            tvTutorial.setText("Здесь можно посмотреть\nобщий счет");
            btnShowScore.setVisibility(View.GONE);
            btnTutorialShowScore.setVisibility(View.VISIBLE);
            btnTutorialShowScore.setOnClickListener(showScoreOnClickListener);
            RLTutorial.setOnClickListener(null);
        }
    };

    private void tutorialThirdClickListener(){
        if (activePlayers == 3 || activePlayers ==5 || activePlayers == 6){
            int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            layoutParams.addRule(RelativeLayout.BELOW, 0);
            layoutParams.addRule(RelativeLayout.ABOVE, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.topMargin = marginPx;
            tvTutorial.setLayoutParams(layoutParams);
        }

        tvTutorial.setText("Нажмите на окно\nчтобы его скрыть");
    }

    private void tutorialForthClickListener(){
        RLTutorial.setTranslationZ(1);
        if (activePlayers == 3 || activePlayers ==5 || activePlayers == 6) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            layoutParams.addRule(RelativeLayout.ABOVE, 0);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.gameTutorialButtonTeam);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            tvTutorial.setLayoutParams(layoutParams);
        }
        tvTutorial.setText("Здесь все\nможно начинать игру");
        RLTutorial.setOnClickListener(null);
    }

    private void tutorialFifthClickListener() {

        RLSecondTutorial.setVisibility(View.VISIBLE);
        tvSecondTutorial.setVisibility(View.VISIBLE);
        gameTutorialSecondIV.setVisibility(View.VISIBLE);

        tvSecondTutorial.setText("Ваша задача объяснить\nкак можно больше слов\nсвоему товарищу по команде");
        RLSecondTutorial.setOnClickListener(tutorialSixthClickListener);
    }


    private View.OnClickListener tutorialSixthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tvSecondTutorial.setText("Но нельзя использовать\nоднокоренные слова,\nаналогичные слова из\nдругих языков и\nявно показывать жестами");
            RLSecondTutorial.setOnClickListener(tutorialSeventhClickListener);
        }
    };

    private View.OnClickListener tutorialSeventhClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            showTutorialTimer(SECONDS_TO_COUNTDOWN * MILLIS_PER_SECOND);

            tvWordsCopy.setText(tvWords.getText());
            RLTimeDisplayBox.setVisibility(View.VISIBLE);
            gameTutorialSecondIV.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.time_display_box);
            tvSecondTutorial.setLayoutParams(layoutParams);

            int dp70 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
            int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dp70, dp70);
            params.addRule(RelativeLayout.BELOW, R.id.time_display_box);
            params.topMargin = marginPx;
            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            gameTutorialSecondIV.setLayoutParams(params);

            tvSecondTutorial.setText("Это оставшееся время");
            RLSecondTutorial.setOnClickListener(tutorialEighthClickListener);
        }
    };


    private View.OnClickListener tutorialEighthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RLTimeDisplayBox.setVisibility(View.GONE);
            RLTvWords.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.tvWords_RL);
            tvSecondTutorial.setLayoutParams(layoutParams);


            int dp70 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
            int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dp70, dp70);
            params.addRule(RelativeLayout.BELOW, R.id.coinRL);
            params.topMargin = marginPx;

            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            gameTutorialSecondIV.setLayoutParams(params);

            tvSecondTutorial.setText("Слово, которое\nнужно объяснить");
            RLSecondTutorial.setOnClickListener(tutorialNinthClickListener);
        }
    };

    private View.OnClickListener tutorialNinthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RLTvWords.setVisibility(View.GONE);
            gameTutorialSecondIV.setVisibility(View.GONE);
            RLGameButtonRight.setTranslationZ(R.dimen.translation_z_4);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.tvWords);
            tvSecondTutorial.setLayoutParams(layoutParams);

            tvSecondTutorial.setText("Если напарник угадал\nжмите сюда");
            RLSecondTutorial.setOnClickListener(null);
            btnRight.setOnClickListener(tutorialTenthClickListener);

        }
    };


    private View.OnClickListener tutorialTenthClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            gameTutorialSecondIV.setVisibility(View.VISIBLE);
            btnRight.setOnClickListener(btnRightClickListener);
            // Увеличиваем счет на 1 очко
            ++score;
            // Переводим наш ключ в состояние false (Понадобится далее)
            myTimerKey = MY_TIMER_KEY_NONE;
            // Вставляем новое слово
            random(words);
            if (coinYesCounter <5){coinYesCounter++;}
            else {coinYesCounter = 1;}
            setCoin(btnRight);
            setTextWithParams(tvScore, score);
            tvWordsCopy.setText(tvWords.getText());


            RLCoin.setTranslationZ(R.dimen.translation_z_4);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.time_display_box);
            tvSecondTutorial.setLayoutParams(layoutParams);


            tvSecondTutorial.setText("За верный ответ\nдобавляется очко и\nдается следующее слово\nна угадывание");

            RLSecondTutorial.setOnClickListener(tutorialEleventhClickListener);
        }

    };
    private View.OnClickListener tutorialEleventhClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            gameTutorialSecondIV.setVisibility(View.GONE);

            RLGameButtonWrong.setTranslationZ(R.dimen.translation_z_4);



            tvSecondTutorial.setText("Слово можно пропустить,\nно Вы потеряете очко");
            RLSecondTutorial.setOnClickListener(null);
            btnWrong.setOnClickListener(tutorialTwelfthClickListener);
        }
    };

    private View.OnClickListener tutorialTwelfthClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            gameTutorialSecondIV.setVisibility(View.VISIBLE);
            btnWrong.setOnClickListener(btnWrongClickListener);

            if (coinNoCounter <5){coinNoCounter++;}
            else {coinNoCounter = 1;}
            setCoin(btnWrong);

            // Переводим наш ключ в состояние false
            myTimerKey = MY_TIMER_KEY_NONE;
            // Отнимаем 1 очко
            --score;
            // Вставляем новое слово
            random(words);
            setTextWithParams(tvScore, score);


            tvWordsCopy.setText(tvWords.getText());

            tvSecondTutorial.setText("Победит та команда,\n которая первая\n наберет " + (Integer.toString(SCORE_FOR_WIN)) +" очков");

            RLSecondTutorial.setOnClickListener(tutorialThirteenthClickListener);
        }
    };

    private View.OnClickListener tutorialThirteenthClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {


            visibilityAfterGame();
            btnBottom.setVisibility(View.GONE);
            setImage(activeNow, btnAfterGameIcon);


            setProgressBarOptions(score);
            LLProgressBar.setTranslationZ(R.dimen.translation_z_4);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
            int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());

            layoutParams.topMargin = marginPx;

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            tvSecondTutorial.setLayoutParams(layoutParams);

            tvSecondTutorial.setText("После окончания раунда игроки меняются ролями");

            RLSecondTutorial.setOnClickListener(tutorialFourteenthClickListener);

        }
    };

    private View.OnClickListener tutorialFourteenthClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            MainActivity.setTutorialDisagree();
            finish();
        }
    };


    // Обработчик таймера во время обучения
    private void showTutorialTimer(int countdownMillis) {

        // Создаем архитектуру таймера
        if(timer != null) { timer.cancel(); }

        // Устанавливаем шаг для таймера длиной в одну секунду
        timer = new CountDownTimer(countdownMillis, MILLIS_PER_SECOND) {
            @Override
            // Каждый тик (1 секунда)
            public void onTick(long millisUntilFinished) {
                // Показываем игроку оставшееся время
                tvCountdownDisplay.setText("" + millisUntilFinished / MILLIS_PER_SECOND);
            }

            // Когда время закончилось
            @Override
            public void onFinish() {
            }
            // Активируем таймер
        }.start();
    }











    //*****************************************************ОБЩЕЕ*****************************************************


    private View.OnClickListener btnBottomFirstOnClickListener = new View.OnClickListener() {

        public void onClick(View view) {
            if (tutorialAgree == 1 ){
                RLTutorial.setVisibility(View.GONE);
            }
            if (RLShowScore.getVisibility() == View.VISIBLE){
                hideScore();
            }
            // Скрываем старое окно, открываем окно самой игры
            RLOnStart.setVisibility(View.GONE);
            RLInGame.setVisibility(View.VISIBLE);
            btnBottom.setVisibility(View.GONE);
            activityCondition = 1;
//                activityGame.setBackgroundResource(R.drawable.background_game_in_game);

            tvWords.setText("Коснитесь\nчтобы начать");

            RLInGame.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (tutorialAgree == 1 ){
                        tutorialFifthClickListener();
                    }
                    // Применяем функию random к нашему списку слов
                    random(words);
                    RLInGame.setOnClickListener(null);
                    if (tutorialAgree == 0){
                        // запускаем таймер
                        showTimer(SECONDS_TO_COUNTDOWN * MILLIS_PER_SECOND);
                    }
                    RLGameButtonRight.setVisibility(View.VISIBLE);
                    RLGameButtonWrong.setVisibility(View.VISIBLE);
                    coinImage.setVisibility(View.VISIBLE);
                    tvScore.setVisibility(View.VISIBLE);
                    btnRight.setOnClickListener(btnRightClickListener);
                    btnWrong.setOnClickListener(btnWrongClickListener);
                }
            });

        }
    };


    // Задаем обработчик нажатия кнопке "Верно"
    private View.OnClickListener btnRightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Увеличиваем счет на 1 очко
            ++score;
            // Переводим наш ключ в состояние false (Понадобится далее)
            myTimerKey = MY_TIMER_KEY_NONE;
            // Вставляем новое слово
            random(words);
            // Нужно для верного отображения обучения
            if (tutorialAgree == 1){
                tvWordsCopy.setText(tvWords.getText());
            }
            // Чтоб монетки вылетали друг за другом
            if (coinYesCounter <5){coinYesCounter++;}
            else {coinYesCounter = 1;}
            setCoin(btnRight);
            setTextWithParams(tvScore, score);


        }
    };


    // Задаем обработчик нажатия кнопке "Пропустить"
    private View.OnClickListener btnWrongClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Чтоб монетки вылетали друг за другом
            if (coinNoCounter <5){coinNoCounter++;}
            else {coinNoCounter = 1;}
            setCoin(btnWrong);

            // Переводим наш ключ в состояние false
            myTimerKey = MY_TIMER_KEY_NONE;
            // Отнимаем 1 очко
            --score;
            // Вставляем новое слово
            random(words);
            setTextWithParams(tvScore, score);

            // Нужно для верного отображения обучения
            if (tutorialAgree == 1){
                tvWordsCopy.setText(tvWords.getText());
            }
        }
    };



    // Функция рандомных слов
    private void random(String randomWords[]) {
        Random rand = new Random();
        tvWords.setText(randomWords[rand.nextInt(randomWords.length)]);
    }

    // Обрабочтик картинки соответствующей команды
    public static void setImage(int imageTeam, Button myButton){
        if (imageTeam == 0){ myButton.setBackgroundResource(R.drawable.icon_bear);}
        if (imageTeam == 1){ myButton.setBackgroundResource(R.drawable.icon_raccoon);}
        if (imageTeam == 2){ myButton.setBackgroundResource(R.drawable.icon_wolf);}
        if (imageTeam == 3){ myButton.setBackgroundResource(R.drawable.icon_panda);}
        if (imageTeam == 4){ myButton.setBackgroundResource(R.drawable.icon_fox);}
        if (imageTeam == 5){ myButton.setBackgroundResource(R.drawable.icon_cat);}
    }
    public static void setTextWithParams(TextView tv, int intScore){
        if (intScore <= -100){
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tv.setText(Integer.toString(intScore));
        }
        else if (intScore >= 100){
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tv.setText(Integer.toString(intScore));
        }
        else{
            tv.setText(Integer.toString(intScore));
        }
    }

    @Override
    protected void onPause() {

        if (activityCondition == 1 && timer != null) {
            this.timer.cancel();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (activityCondition == 1 && timer != null) {
            showTimer(timeLeft * MILLIS_PER_SECOND);
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (tutorialAgree == 1){
                MainActivity.setTutorialDisagree();
            }
            finish();
        }
        else
            Toast.makeText(getBaseContext(), "Нажмите дважды для выхода",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }


}
