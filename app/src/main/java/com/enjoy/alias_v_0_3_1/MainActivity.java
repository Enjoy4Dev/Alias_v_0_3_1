package com.enjoy.alias_v_0_3_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String PUT_EXTRA_KEY_TUTORIAL_AGREE = "tutorialAgree";

    Button btnStart, btnTutorialAgree, btnTutorialDisagree, btnTutorialTest;
    RelativeLayout tutorialLayout, tutorialGradientView;
    TextView tutorialText;
    boolean tutorialAgree;
    static boolean tutorialDisagree;

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Определяем компоненты Layout-a
        btnStart = (Button) findViewById(R.id.myButton);
        btnTutorialAgree = (Button) findViewById(R.id.mainActivityTutorialButtonAgree);
        btnTutorialDisagree = (Button) findViewById(R.id.mainActivityTutorialButtonDisagree);
        tutorialLayout = (RelativeLayout) findViewById(R.id.mainActivityTutorialLayout);
        tutorialText = (TextView) findViewById(R.id.mainActivityTutorialText);
        btnTutorialTest = (Button) findViewById(R.id.testTutorialButton);

        // Обработчик нажатия кнопки "Играть"


        // Обработчик тестовой кнопки обучения
        btnTutorialTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tutorialLayout.setVisibility(View.VISIBLE);
                secondTutorialMessage();
                tutorialAgree = true;
            }
        });

        // Отказ от обучения
        btnTutorialDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorialAgree = false;
                btnStart.setOnClickListener(oclTvStart);
                tutorialLayout.setVisibility(View.GONE);
            }
        });

        // Принять обучение
        btnTutorialAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorialAgree = true;
                secondTutorialMessage();
            }
        });
        // Присваеваем обработчик соответствующему компоненту
//        btnStart.setOnClickListener(oclTvStart);

        // Анимация колеса *Надо доработать*
//        ImageView image = (ImageView) findViewById(R.id.flat_circle);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_center);
//        image.startAnimation(animation);

        // Если игрок зашел впервые
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            firstTutorialMessage();
            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else {
            btnStart.setOnClickListener(oclTvStart);
        }
        if (tutorialDisagree){
            tutorialAgree = false;
        }
    }
    // Обработчик кнопки "Старт"
    View.OnClickListener oclTvStart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, com.enjoy.alias_v_0_3_1.ChooseTeam.class);
            // Отправляем информацию о готовности обучения
            intent.putExtra(PUT_EXTRA_KEY_TUTORIAL_AGREE, tutorialAgree);
            startActivity(intent);

            tutorialLayout.setVisibility(View.GONE);
        }
    };

    private void firstTutorialMessage (){
        tutorialLayout.setVisibility(View.VISIBLE);
        tutorialText.setText("Вы зашли впервые \nХотите пройти обучение?");
    }
    private void secondTutorialMessage (){
        tutorialText.setText("Тогда начнем \nНажмите кнопку ИГРАТЬ");
        tutorialLayout.setTranslationZ(1);
        btnTutorialAgree.setVisibility(View.GONE);
        btnTutorialDisagree.setVisibility(View.GONE);
        btnStart.setOnClickListener(oclTvStart);
    }

    public static void setTutorialDisagree(){
        tutorialDisagree = true;
    }

    @Override
    protected void onResume() {

        if (tutorialDisagree){
            tutorialAgree = false;
        }
        btnStart.setOnClickListener(oclTvStart);
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(getBaseContext(), "Нажмите дважды для выхода",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}