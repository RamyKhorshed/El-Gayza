package com.husseinelkheshen.elgayza;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Quiz extends AppCompatActivity {

    String UID;

    Button answer1, answer2, answer3, answer4;

    TextView score, question;

    String Q1, Q2, Q3, Q4, Q;

    private Questions  qObject = new Questions();

    private String mAnswer;
    private int mScore = 0;
    private int qLength = qObject.getLength();

    private DatabaseReference qDatabase;
    private DatabaseReference uDatabase;
    private DatabaseReference activityDB;

    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

//        startActivity(new Intent(getApplicationContext(), Video.class));

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            UID = currentUser.getUid();
        }
        
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        score = findViewById(R.id.score);
        question = findViewById(R.id.question);

        updateQuestion(n++);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        qDatabase = root.child("20180428").child("questions");
        uDatabase = root.child("20180428").child("users");
        activityDB = root.child("activity");

        activityDB.child(UID).setValue(ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        getDate(UID);
                    }
                });

        getQuestion(1);

        answer1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                submitAnswer(UID, n,answer1.getText().toString());
                disableButtons();

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
                submitAnswer(UID, n,answer2.getText().toString());
                disableButtons();

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
                submitAnswer(UID, n,answer3.getText().toString());
                disableButtons();

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
                submitAnswer(UID, n,answer4.getText().toString());
                disableButtons();

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

    private void submitAnswer(String userId, int n, String answer) {
        uDatabase.child(userId).child(Integer.toString(n)).setValue(answer);
    }

    private void getDate(String UID) {
        activityDB.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateObj = dataSnapshot.getValue().toString();
                long date = Long.parseLong(dateObj) + 7200000;
                String dateString = new Date(date).toString();

                score.setText(dateString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void disableButtons() {
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        answer4.setEnabled(false);
    }
}
