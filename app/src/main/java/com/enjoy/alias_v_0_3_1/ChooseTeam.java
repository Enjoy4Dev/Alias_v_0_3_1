package com.enjoy.alias_v_0_3_1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam0;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam1;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam2;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam3;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam4;
import static com.enjoy.alias_v_0_3_1.R.id.contentPlayersBtnTeam5;
import static com.enjoy.alias_v_0_3_1.R.id.tutorialText;


public class ChooseTeam extends AppCompatActivity {

    private static final String PUT_EXTRA_KEY_TUTORIAL_AGREE = "tutorialAgree";

    Button btnTeam0, btnTeam1, btnTeam2, btnTeam3, btnTeam4, btnTeam5,  btnStart;
    boolean activeTeam[], tutorialAgree;
    public int activePlayers, activeNow, repeats, rowNumber, tutorialCondition;
    RelativeLayout tutorialLayout;
    TextView tutorialTV;


    private static long back_pressed;

    private static final int ID_DB_LOGIC = 1;

    private com.enjoy.alias_v_0_3_1.DB mDb;
    private com.enjoy.alias_v_0_3_1.DbLogic mDbLogic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team);

        // Определяем базы данных
        mDb = new com.enjoy.alias_v_0_3_1.DB(this);
        mDbLogic = new com.enjoy.alias_v_0_3_1.DbLogic(this);
        // Отчищаем БД от всей раннее вбитой информации, если таковая была
        mDb.deleteTable();
        mDbLogic.deleteTable();

        // Определяем масив активных пользователей, присваиваем все состояние неактивных
        activeTeam = new boolean[6];
        for (int i = 0; i < 6; ++i) {
            activeTeam[i] = false;
        }

        tutorialAgree = false;
        tutorialAgree = getIntent().getExtras().getBoolean(PUT_EXTRA_KEY_TUTORIAL_AGREE);
        tutorialCondition = 0;

        // Определяем компоненты Layout-a
        btnTeam0 = (Button) findViewById(contentPlayersBtnTeam0);
        btnTeam1 = (Button) findViewById(contentPlayersBtnTeam1);
        btnTeam2 = (Button) findViewById(contentPlayersBtnTeam2);
        btnTeam3 = (Button) findViewById(contentPlayersBtnTeam3);
        btnTeam4 = (Button) findViewById(contentPlayersBtnTeam4);
        btnTeam5 = (Button) findViewById(contentPlayersBtnTeam5);
        btnStart = (Button) findViewById(R.id.btnStart);
        tutorialLayout = (RelativeLayout) findViewById(R.id.chooseTeamTutorialLayout);
        tutorialTV = (TextView) findViewById(R.id.chooseTeamTutorialText);

        // Проверка на готовность обучения
        if (tutorialAgree){
            tutorialCondition = 1;
            showTutorial();
        }

        // Присваиваем обрабочик нажатия для каждой кнопки
//        if (!tutorialAgree) {
            btnTeam0.setOnClickListener(positiveButtonListener);
            btnTeam1.setOnClickListener(positiveButtonListener);
            btnTeam2.setOnClickListener(positiveButtonListener);
            btnTeam3.setOnClickListener(positiveButtonListener);
            btnTeam4.setOnClickListener(positiveButtonListener);
            btnTeam5.setOnClickListener(positiveButtonListener);
            btnStart.setOnClickListener(startButtonListener);
//        }


        // Задаем переменной суммы активных пользователей нулевое значение
        activePlayers = 0;

    }


    // Обработчик нажатия, когда игрок не активен
    View.OnClickListener positiveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // Проверка нажатой кнопки по ID
                case R.id.contentPlayersBtnTeam0:
                    // Присваиваем картинку активного игрока
                    btnTeam0.setBackgroundResource(R.drawable.icon_bear);
                    // Присваиваем соответствующему игроку состояние активного
                    activeTeam[0] = true;
                    // Задаем этой кнопке следующий обработчик, который при повторном нажатии сделает игрока неактивным
                    btnTeam0.setOnClickListener(negativeButtonListener);
                    break;

                case R.id.contentPlayersBtnTeam1:
                    btnTeam1.setBackgroundResource(R.drawable.icon_raccoon);
                    activeTeam[1] = true;
                    btnTeam1.setOnClickListener(negativeButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam2:
                    btnTeam2.setBackgroundResource(R.drawable.icon_wolf);
                    activeTeam[2] = true;
                    btnTeam2.setOnClickListener(negativeButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam3:
                    btnTeam3.setBackgroundResource(R.drawable.icon_panda);
                    activeTeam[3] = true;
                    btnTeam3.setOnClickListener(negativeButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam4:
                    btnTeam4.setBackgroundResource(R.drawable.icon_fox);
                    activeTeam[4] = true;
                    btnTeam4.setOnClickListener(negativeButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam5:
                    btnTeam5.setBackgroundResource(R.drawable.icon_cat);
                    activeTeam[5] = true;
                    btnTeam5.setOnClickListener(negativeButtonListener);
                    break;
            }
        }
    };

    // Обработчик нажатия, когда игрок активен
    View.OnClickListener negativeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // Проверка нажатой кнопки по ID
                case R.id.contentPlayersBtnTeam0:
                    // Присваиваем соответствующему игроку состояние неактивного
                    btnTeam0.setBackgroundResource(R.drawable.icon_bear_unpressed);
                    // Присваиваем соответствующему игроку состояние неактивного
                    activeTeam[0] = false;
                    // Задаем этой кнопке следующий обработчик, который при повторном нажатии сделает игрока активным
                    btnTeam0.setOnClickListener(positiveButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam1:
                    btnTeam1.setBackgroundResource(R.drawable.icon_raccoon_unpressed);
                    activeTeam[1] = false;
                    btnTeam1.setOnClickListener(positiveButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam2:
                    btnTeam2.setBackgroundResource(R.drawable.icon_wolf_unpressed);
                    activeTeam[2] = false;
                    btnTeam2.setOnClickListener(positiveButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam3:
                    btnTeam3.setBackgroundResource(R.drawable.icon_panda_unpressed);
                    activeTeam[3] = false;
                    btnTeam3.setOnClickListener(positiveButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam4:
                    btnTeam4.setBackgroundResource(R.drawable.icon_fox_unpressed);
                    activeTeam[4] = false;
                    btnTeam4.setOnClickListener(positiveButtonListener);
                    break;
                case R.id.contentPlayersBtnTeam5:
                    btnTeam5.setBackgroundResource(R.drawable.icon_cat_unpressed);
                    activeTeam[5] = false;
                    btnTeam5.setOnClickListener(positiveButtonListener);
                    break;
            }
        }
    };

    // Обработчик нажатия кнопки "Старт"
    View.OnClickListener startButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Создаем intent со ссылкой на следующую активность
            Intent intent = new Intent(ChooseTeam.this, com.enjoy.alias_v_0_3_1.Game.class);
            // Запускаем цикл, который проверяет количество активных пользователей и заносит эти данные в соответствующуб переменную
            for (int i = 0; i < 6; ++i) {
                if (activeTeam[i]) {
                    ++activePlayers;
                }
            }

            // Проводим проверку на наличие хотябы одного активного игрока
            // Если такой игрок имеется
            if (activePlayers > 0) {
                // Сохраняем всю информацию в наши БД
                insertAllData();
                // Запускаем следующую активность
                startActivity(intent);
                finish();
            // Если нет ни одного активного игрока вызываем тост с соответствующей информацией
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Не выбранна ни 1 команда", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };


    // Вставляем всю информацию в БД
    private void insertAllData () {
        rowNumber = 0;
        // Задаем соответсвующему игроку его номер строки в БД
        for (int i = 0; i < 6; ++i) {
            if (activeTeam[i]) {
                ++rowNumber;
                mDb.insertId(i, 0, rowNumber);
            }
        }

        // Находим первого активного игрока и передаем его данные в activeNow, после чего останавливаем цикл
        for (int i = 0; i < 6; ++i) {
            if (activeTeam[i]) { activeNow = i; break; }
        }

        // Устанавливаем количество кругов игры из расчета: 4 круга на команду, 2 на каждого игрока
        repeats = activePlayers;
        // Вставляем всю накопленную ранее информацию в логическую БД
        mDbLogic.insertDataToLogic(ID_DB_LOGIC, activeNow, activePlayers, tutorialCondition, repeats );
    }

    // Показать обучение
    private void showTutorial (){
        tutorialLayout.setVisibility(View.VISIBLE);
        tutorialTV.setText("Alias - это командная игра,\nгде побеждает тот, кто\nлучше объясняет слова");
        tutorialLayout.setOnClickListener(tutorialFirstClickListener);
    }
    // Первое окно
    View.OnClickListener tutorialFirstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tutorialTV.setText("Для начала разделитесь \nна команды по два \nчеловека в каждой");
            tutorialLayout.setOnClickListener(tutorialSecondClickListener);
        }
    };
    // Второе окно
    View.OnClickListener tutorialSecondClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tutorialLayout.setVisibility(View.GONE);
        }
    };

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            if (tutorialCondition == 1) {
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

