package com.example.lkwan.chineseflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int correct = getIntent().getExtras().getInt("Correct");
        int total = getIntent().getExtras().getInt("Total");
        float score = correct / (float) total;

        TextView result_text = (TextView) findViewById(R.id.result);
        result_text.setText(getString(R.string.result, correct, total));

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        ratingBar.setRating(score * 5);

        TextView eval_text = (TextView) findViewById(R.id.eval);
        int textScore = (int) (score * 5);
        switch (textScore) {
            case 0:
            case 1:
            case 2:
                eval_text.setText(R.string.rate0);
                break;
            case 3:
                eval_text.setText(R.string.rate1);
                break;
            case 4:
            case 5:
                eval_text.setText(R.string.rate2);
                break;
        }
    }

    public void restart(View view) {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
