package com.husseinelkheshen.elgayza;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.Date;
import java.text.SimpleDateFormat;

public class Quiz extends AppCompatActivity {

    String UID;

    Button answer1, answer2, answer3, answer4;

    TextView score, question;

    String Q1, Q2, Q3, Q4, Q;

    int qLength;

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

        if (currentUser != null) {
            UID = currentUser.getUid();
        }

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        score = findViewById(R.id.score);
        question = findViewById(R.id.question);

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

        qDatabase.child("length").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                qLength = Integer.parseInt(dataSnapshot.getValue().toString());
                getQuestion(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        answer1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                disableButtons();
                submitAnswer(UID, n++, "0");
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                disableButtons();
                submitAnswer(UID, n++, "1");
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                disableButtons();
                submitAnswer(UID, n++, "2");
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                disableButtons();
                submitAnswer(UID, n++, "3");
            }
        });
    }

    @Override
    public void onBackPressed() {

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

    private void submitAnswer(final String userId, final int n, final String answer) {
        uDatabase.child(userId).child(Integer.toString(n)).setValue(answer,
                new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                checkAnswer(n, answer);
            }
        });
    }

    private void checkAnswer(final int n, final String answer) {
        qDatabase.child(Integer.toString(n)).child("correct")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String solution = dataSnapshot.getValue().toString();

                if (answer.equals(solution)) {
                    enableButtons();
                }

                int q = n+1;

                if(q < qLength) {
                    getQuestion(q);
                }
                else {
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDate(String UID) {
        activityDB.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateObj = dataSnapshot.getValue().toString();
                long date = Long.parseLong(dateObj) + 7200000;
                Date dateFull = new Date(date);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyyMMdd");

                String dateString = sdf.format(dateFull);

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

    private void enableButtons() {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
    }
}
