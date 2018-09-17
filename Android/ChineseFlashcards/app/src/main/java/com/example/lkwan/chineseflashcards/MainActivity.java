package com.example.lkwan.chineseflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView quiz_meaning = (TextView) findViewById(R.id.quiz_meaning);
        TextView quiz_character = (TextView) findViewById(R.id.quiz_character);
        TextView quiz_pinyin = (TextView) findViewById(R.id.quiz_pinyin);
        TextView quiz_random = (TextView) findViewById(R.id.quiz_random);

        quiz_meaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent(R.id.quiz_meaning);
            }
        });

        quiz_character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent(R.id.quiz_character);
            }
        });

        quiz_pinyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent(R.id.quiz_pinyin);
            }
        });

        quiz_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent(R.id.quiz_random);
            }
        });
    }

    private void sendIntent(int quizType) {

        //Get number of questions
        EditText number_text = (EditText) findViewById(R.id.question_number);

        if (!number_text.getText().toString().isEmpty()) {
            //Create and send intent

            int questionNumber = Integer.parseInt(number_text.getText().toString());

            Intent startQuiz = new Intent(MainActivity.this, QuizActivity.class);
            startQuiz.putExtra("QuizType", quizType);
            startQuiz.putExtra("QuestionNumber", questionNumber);
            startActivity(startQuiz);
        } else {
            //Display toast message
            Toast.makeText(MainActivity.this, "Quiz needs at least one question.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
