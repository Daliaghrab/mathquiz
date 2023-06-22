package com.codingwithzo.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textResult = findViewById(R.id.textResult);

        Intent intent = getIntent();
        int rightAnswers = intent.getIntExtra("RA", 0);

        textResult.setText("You answered " + rightAnswers + " out of 10 questions correctly!");
    }
}
