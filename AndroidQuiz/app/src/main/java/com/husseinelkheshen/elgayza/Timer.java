package com.husseinelkheshen.elgayza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer extends AppCompatActivity {

    String UID;

    long date, startTime;

    Button backButton;

    TextView nextTime;

    private DatabaseReference activityDB, root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        nextTime = findViewById(R.id.nextTime);

        backButton = findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
                finish();
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            UID = currentUser.getUid();
        }

        root = FirebaseDatabase.getInstance().getReference();
        activityDB = root.child("activity");

        logActivity();
    }

    private void getDate(String UID) {
        activityDB.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateObj = dataSnapshot.getValue().toString();
                date = Long.parseLong(dateObj);
                getStart("20180428");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getStart(String start) {
        root.child(start).child("start").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String startObj = dataSnapshot.getValue().toString();
                startTime = Long.parseLong(startObj);

                Date dateStart = new Date(startTime + 7200000);

                nextTime.setText(dateStart.toString());

                long diff = startTime - date;

                if(diff <= 0){
                    startActivity(new Intent(getApplicationContext(), Quiz.class));
                }
                else{
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            logActivity();
                        }
                    }, diff);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void logActivity() {
        activityDB.child(UID).setValue(ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                getDate(UID);
            }
        });
    }
}
