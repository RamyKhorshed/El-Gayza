package com.husseinelkheshen.elgayza;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4;

    TextView score, question;

    String Q1, Q2, Q3, Q4, Q;

    private Questions  qObject = new Questions();

    private String mAnswer;
    private int mScore = 0;
    private int qLength = qObject.getLength();

    private DatabaseReference qDatabase;

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

        score = findViewById(R.id.score);
        question = findViewById(R.id.question);

        updateQuestion(n++);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        qDatabase = FirebaseDatabase.getInstance().getReference().child("questions");

        getAnswers(0);
        getQuestion(0);

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

//    private void showData(DataSnapshot dataSnapshot) {
//        FirebaseQ fQ = new FirebaseQ();
//        fQ.setQuestion(dataSnapshot.child("0").getValue(FirebaseQ.class).getQuestion());
//
//        Q1 = fQ.getQuestion();
//    }

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
//        AlertDialog.Builder aDB = new AlertDialog.Builder(Quiz.this);
//        aDB
//                .setMessage("Game Over! Your score is " + mScore + " points!")
//                .setCancelable(false)
//                .setPositiveButton("NEW GAME",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                startActivity(new Intent(getApplicationContext(), Menu.class));
//                            }
//                        })
//                .setNegativeButton("EXIT",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//                        });
//        aDB.create();
//        aDB.show();
    }

    private void getAnswers(int n) {
        qDatabase.child(Integer.toString(n)).child("solutions").
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Q1 = dataSnapshot.child("0").getValue().toString();
                answer1.setText(Q1);

                Q2 = dataSnapshot.child("1").getValue().toString();
                answer2.setText(Q2);

                Q3 = dataSnapshot.child("2").getValue().toString();
                answer3.setText(Q3);

                Q4 = dataSnapshot.child("3").getValue().toString();
                answer4.setText(Q4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getQuestion(int n) {
        qDatabase.child(Integer.toString(n)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Q = dataSnapshot.child("question").getValue().toString();
                        question.setText(Q);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
