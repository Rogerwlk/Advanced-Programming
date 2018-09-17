package com.example.lkwan.chineseflashcards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private int mQuizType;
    private int mTotalQuestion;

    private int mCurrentQuestion;
    private int mCurrentWord;
    private int mCurrentCorrect;
    private int getCorrect;

    private ArrayList<Word> words = new ArrayList<Word>();
    private ArrayList<Integer> choices_index = new ArrayList<Integer>(4);

    private TextView question;
    private TextView[] choices = new TextView[4];

    private ImageButton nextIcon;
    private ImageButton likeIcon;
    private ImageButton dislikeIcon;

    private Random rn = new Random();

    private static final String PROGRESS_FILE = "Progress.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setActionBarTitle();

        //Read words from file
        BufferedReader mBR = new BufferedReader(
                new InputStreamReader(
                        getResources().openRawResource(R.raw.dict)));

        try {
            String line = mBR.readLine();
            String[] ar;
            while (line != null) {
                ar = line.split(",");
                if (ar.length == 3) {
                    words.add(new Word(ar[2], ar[0], ar[1]));
                } else {
                    throw new IOException("Wrong file format. Should be Character,Pinyin,Meaning");
                }
                line = mBR.readLine();
            }
            mBR.close();
        } catch (IOException ex) {
            Log.e("QuizActivity", ex.getMessage());
        }
        //Finish reading words

        //Find related question and choices
        nextIcon = (ImageButton) findViewById(R.id.next_icon);
        likeIcon = (ImageButton) findViewById(R.id.like);
        dislikeIcon = (ImageButton) findViewById(R.id.dislike);
        question = (TextView) findViewById(R.id.question);

        choices[0] = (TextView) findViewById(R.id.choice1);
        choices[1] = (TextView) findViewById(R.id.choice2);
        choices[2] = (TextView) findViewById(R.id.choice3);
        choices[3] = (TextView) findViewById(R.id.choice4);

        //Initialize choices_index
        choices_index.add(0);
        choices_index.add(1);
        choices_index.add(2);
        choices_index.add(3);

        choices[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentCorrect == 0) {
                    getCorrect++;
                    Toast.makeText(QuizActivity.this, getString(R.string.correct),
                            Toast.LENGTH_SHORT).show();
                    choices[0].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerCorrect();
                } else {
                    Toast.makeText(QuizActivity.this, getString(R.string.wrong),
                            Toast.LENGTH_SHORT).show();
                    choices[0].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.wrong));
                    choices[mCurrentCorrect].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerIncorrect();
                }
                nextIcon.setVisibility(View.VISIBLE);
                disableAllChoices();
            }
        });

        choices[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentCorrect == 1) {
                    getCorrect++;
                    Toast.makeText(QuizActivity.this, getString(R.string.correct),
                            Toast.LENGTH_SHORT).show();
                    choices[1].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerCorrect();
                } else {
                    Toast.makeText(QuizActivity.this, getString(R.string.wrong),
                            Toast.LENGTH_SHORT).show();

                    choices[1].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.wrong));
                    choices[mCurrentCorrect].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerIncorrect();
                }
                nextIcon.setVisibility(View.VISIBLE);
                disableAllChoices();
            }
        });

        choices[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentCorrect == 2) {
                    getCorrect++;
                    Toast.makeText(QuizActivity.this, getString(R.string.correct),
                            Toast.LENGTH_SHORT).show();
                    choices[2].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerCorrect();
                } else {
                    Toast.makeText(QuizActivity.this, getString(R.string.wrong),
                            Toast.LENGTH_SHORT).show();
                    choices[2].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.wrong));
                    choices[mCurrentCorrect].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerIncorrect();
                }
                nextIcon.setVisibility(View.VISIBLE);
                disableAllChoices();
            }
        });

        choices[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentCorrect == 3) {
                    getCorrect++;
                    Toast.makeText(QuizActivity.this, getString(R.string.correct),
                            Toast.LENGTH_SHORT).show();
                    choices[3].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerCorrect();
                } else {
                    Toast.makeText(QuizActivity.this, getString(R.string.wrong),
                            Toast.LENGTH_SHORT).show();
                    choices[3].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.wrong));
                    choices[mCurrentCorrect].setBackgroundColor(
                            ContextCompat.getColor(QuizActivity.this, R.color.correct));
                    words.get(mCurrentWord).answerIncorrect();
                }
                nextIcon.setVisibility(View.VISIBLE);
                disableAllChoices();
            }
        });

        //Get info from Intent
        mQuizType = getIntent().getExtras().getInt("QuizType");
        mTotalQuestion = getIntent().getExtras().getInt("QuestionNumber");

        mCurrentQuestion = 0;
        getCorrect = 0;

        //Read progress from file
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(PROGRESS_FILE)));
            for (int i = 0; i < words.size(); i++) {
                words.get(i).setPreference(Byte.valueOf(br.readLine()));
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("QuizActivity", ex.getMessage());
        }

        Comparator<Word> comparator = Collections.reverseOrder();
        Collections.sort(words, comparator);

        //Start quiz
        startQuiz();
    }

    private void setActionBarTitle() {
        switch (mQuizType) {
            case R.id.quiz_meaning:
                getSupportActionBar().setTitle(R.string.quiz_meaning);
                break;
            case R.id.quiz_pinyin:
                getSupportActionBar().setTitle(R.string.quiz_pinyin);
                break;
            case R.id.quiz_character:
                getSupportActionBar().setTitle(R.string.quiz_character);
                break;
            case R.id.quiz_random:
                getSupportActionBar().setTitle(R.string.quiz_random);
        }
    }

    private void startQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i].setEnabled(true);
        }
        likeIcon.setBackgroundColor(
                ContextCompat.getColor(QuizActivity.this, R.color.like));
        dislikeIcon.setBackgroundColor(
                ContextCompat.getColor(QuizActivity.this, R.color.dislike));
        likeIcon.setEnabled(true);
        dislikeIcon.setEnabled(true);
        nextIcon.setVisibility(View.INVISIBLE);
        for (int i = 0; i < 4; i++) {
            choices[i].setBackgroundColor(
                    ContextCompat.getColor(QuizActivity.this, R.color.origin));
        }
        switch (mQuizType) {
            case R.id.quiz_meaning:
                quizMeaning();
                break;
            case R.id.quiz_pinyin:
                quizPinyin();
                break;
            case R.id.quiz_character:
                quizCharacter();
                break;
            case R.id.quiz_random:
                switch (rn.nextInt(3)) {
                    case 0:
                        quizCharacter();
                        break;
                    case 1:
                        quizPinyin();
                        break;
                    case 2:
                        quizMeaning();
                        break;
                }
                break;
        }
    }

    private void disableAllChoices() {
        for (int i = 0; i < 4; i++) {
            choices[i].setEnabled(false);
        }
    }

    private void quizMeaning() {
        String temp = words.get(mCurrentWord).getPinyin() + "\n" +
                words.get(mCurrentWord).getCharacter();
        question.setText(temp);

        Collections.shuffle(choices_index);
        for (int i = 0; i < 4; i++) {
            choices[i].setText(words.get(
                    (mCurrentWord + choices_index.get(i)) % words.size()).getMeaning());
            if (choices_index.get(i) == 0) {
                mCurrentCorrect = i;
            }
        }
    }

    private void quizPinyin() {
        String temp = words.get(mCurrentWord).getCharacter() + "\n" +
                words.get(mCurrentWord).getMeaning();
        question.setText(temp);

        Collections.shuffle(choices_index);
        for (int i = 0; i < 4; i++) {
            choices[i].setText(words.get((
                    mCurrentWord + choices_index.get(i)) % words.size()).getPinyin());
            if (choices_index.get(i) == 0) {
                mCurrentCorrect = i;
            }
        }
    }

    private void quizCharacter() {
        String temp = words.get(mCurrentWord).getPinyin() + "\n" +
                words.get(mCurrentWord).getMeaning();
        question.setText(temp);

        Collections.shuffle(choices_index);
        for (int i = 0; i < 4; i++) {
            choices[i].setText(words.get(
                    (mCurrentWord + choices_index.get(i)) % words.size()).getCharacter());
            if (choices_index.get(i) == 0) {
                mCurrentCorrect = i;
            }
        }
    }

    private void showResult() {
        Intent showResult = new Intent(QuizActivity.this, ResultActivity.class);
        showResult.putExtra("Correct", getCorrect);
        showResult.putExtra("Total", mTotalQuestion);
        startActivity(showResult);
    }

    public void onClickNext(View view) {
        mCurrentWord = (mCurrentWord + 1) % words.size();
        mCurrentQuestion++;
        if (mCurrentQuestion >= mTotalQuestion) {
            showResult();
        } else {
            startQuiz();
        }
    }

    public void onClickLike(View view) {
        Toast.makeText(QuizActivity.this, R.string.like,
                Toast.LENGTH_SHORT).show();
        words.get(mCurrentWord).like();
        likeIcon.setEnabled(false);
        dislikeIcon.setEnabled(false);
        likeIcon.setBackgroundColor(Color.GRAY);
        dislikeIcon.setBackgroundColor(Color.GRAY);
    }

    public void onClickDislike(View view) {
        Toast.makeText(QuizActivity.this, R.string.dislike,
                Toast.LENGTH_SHORT).show();
        words.get(mCurrentWord).dislike();
        likeIcon.setEnabled(false);
        dislikeIcon.setEnabled(false);
        likeIcon.setBackgroundColor(Color.GRAY);
        dislikeIcon.setBackgroundColor(Color.GRAY);
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            FileOutputStream os = openFileOutput(PROGRESS_FILE, Context.MODE_PRIVATE);

            for (int i = 0; i < words.size(); i++) {
                os.write(words.get(i).getPreference());
                os.write('\n');
            }
            os.close();
            Log.v("QuizActivity", "onPause: write file success.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("QuizActivity", "onPause: " + e.getMessage());
        }
    }
}
