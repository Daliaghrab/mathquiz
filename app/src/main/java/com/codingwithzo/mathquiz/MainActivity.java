package com.codingwithzo.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MathQuizPrefs";
    private static final String KEY_SCORE = "score";

    TextView textLevel;
    TextView textRightAnswered;
    TextView textQuestion;

    Button buttonOp1;
    Button buttonOp2;
    Button buttonOp3;

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    List<Question> questionList;

    int level = 0;
    int great = 0;
    int rightAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        textLevel = findViewById(R.id.textQuestionNumber);
        textRightAnswered = findViewById(R.id.textRightAnswered);
        textQuestion = findViewById(R.id.textQuestion);

        buttonOp1 = findViewById(R.id.buttonOption1);
        buttonOp2 = findViewById(R.id.buttonOption2);
        buttonOp3 = findViewById(R.id.buttonOption3);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionList = new ArrayList<>();
        adapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(adapter);

        textLevel.setText("Q: " + level + " / 10");
        textRightAnswered.setText("RA: " + great + " / 10");

        loadScore();

        if (level < 10) {
            generateRandomQuestion();
        }
    }

    private void generateRandomQuestion() {
        buttonOp1.setBackgroundResource(R.drawable.buttons_option_bg);
        buttonOp2.setBackgroundResource(R.drawable.buttons_option_bg);
        buttonOp3.setBackgroundResource(R.drawable.buttons_option_bg);

        int firstNumber = new Random().nextInt(10);
        int secondNumber = new Random().nextInt(10);

        int operation = new Random().nextInt(3) + 1;

        int optionA = new Random().nextInt(100);
        int optionB = new Random().nextInt(100);

        String realOperation;
        int rightAnswer;

        if (operation == 1) {
            realOperation = "+";
            rightAnswer = firstNumber + secondNumber;
            textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
        } else if (operation == 2) {
            realOperation = "*";
            rightAnswer = firstNumber * secondNumber;
            textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
        } else {
            realOperation = "-";
            if (firstNumber < secondNumber) {
                rightAnswer = secondNumber - firstNumber;
                textQuestion.setText(secondNumber + " " + realOperation + " " + firstNumber + " = ?");
            } else {
                rightAnswer = firstNumber - secondNumber;
                textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
            }
        }

        int position = new Random().nextInt(3) + 1;

        List<Integer> options = new ArrayList<>();
        options.add(rightAnswer);
        options.add(optionA);
        options.add(optionB);

        List<Integer> randomizedOptions = new ArrayList<>();
        while (!options.isEmpty()) {
            int index = new Random().nextInt(options.size());
            randomizedOptions.add(options.get(index));
            options.remove(index);
        }

        Question question = new Question(firstNumber, secondNumber, realOperation, rightAnswer, randomizedOptions, position);
        questionList.add(question);
        adapter.notifyDataSetChanged();

        buttonOp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerButtonClick(question, 1);
            }
        });

        buttonOp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerButtonClick(question, 2);
            }
        });

        buttonOp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerButtonClick(question, 3);
            }
        });
    }

    private void handleAnswerButtonClick(Question question, int selectedOption) {
        Button selectedButton;
        int selectedPosition;

        if (selectedOption == 1) {
            selectedButton = buttonOp1;
            selectedPosition = 1;
        } else if (selectedOption == 2) {
            selectedButton = buttonOp2;
            selectedPosition = 2;
        } else {
            selectedButton = buttonOp3;
            selectedPosition = 3;
        }

        if (question.getPosition() == selectedPosition) {
            selectedButton.setBackgroundResource(R.drawable.right_answer_bg);
            great++;
        } else {
            selectedButton.setBackgroundResource(R.drawable.wrong_answer_bg);
        }

        level++;
        textLevel.setText("Q: " + level + " / 10");
        textRightAnswered.setText("RA: " + great + " / 10");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                questionList.remove(question);
                adapter.notifyDataSetChanged();

                if (level < 10) {
                    generateRandomQuestion();
                } else {
                    saveScore();
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("RA", great);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000); // 1 sec
    }

    private void saveScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SCORE, great);
        editor.apply();
    }

    private void loadScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        great = sharedPreferences.getInt(KEY_SCORE, 0);
        textRightAnswered.setText("RA: " + great + " / 10");
    }
}
