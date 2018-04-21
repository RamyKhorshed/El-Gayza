package com.husseinelkheshen.elgayza;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Quiz extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4;

    TextView score, question;

    private Questions  qObject = new Questions();

    private String mAnswer;
    private int mScore = 0;
    private int qLength = qObject.getLength();

    private FirebaseDatabase database;

    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        startActivity(new Intent(getApplicationContext(), Video.class));

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        database = FirebaseDatabase.getInstance();

        score = findViewById(R.id.score);
        question = findViewById(R.id.question);

        updateQuestion(n++);

        answer1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (answer1.getText() == mAnswer) {
                    mScore++;
                    score.setText(Integer.toString(mScore));
                    updateQuestion(n++);
                    if(n-1 >= qLength){
                        gameOver();
                        finish();
                    }
                } else {
                    gameOver();
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (answer2.getText() == mAnswer) {
                    mScore++;
                    score.setText(Integer.toString(mScore));
                    updateQuestion(n++);
                    if(n-1 >= qLength){
                        gameOver();
                        finish();
                    }
                } else {
                    gameOver();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (answer3.getText() == mAnswer) {
                    mScore++;
                    score.setText(Integer.toString(mScore));
                    updateQuestion(n++);
                    if(n-1 >= qLength){
                        gameOver();
                        finish();
                    }
                } else {
                    gameOver();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (answer4.getText() == mAnswer) {
                    mScore++;
                    score.setText(Integer.toString(mScore));
                    updateQuestion(n++);
                    if(n-1 >= qLength){
                        gameOver();
                        finish();
                    }
                } else {
                    gameOver();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void updateQuestion(int n) {
        question.setText(qObject.getQuestion(n));

        answer1.setText(qObject.getChoice1(n));
        answer2.setText(qObject.getChoice2(n));
        answer3.setText(qObject.getChoice3(n));
        answer4.setText(qObject.getChoice4(n));

        mAnswer = qObject.getCorrect(n);
    }

    private void gameOver() {
        AlertDialog.Builder aDB = new AlertDialog.Builder(Quiz.this);
        aDB
                .setMessage("Game Over! Your score is " + mScore + " points!")
                .setCancelable(false)
                .setPositiveButton("NEW GAME",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), Menu.class));
                            }
                        })
                .setNegativeButton("EXIT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
    }
}
