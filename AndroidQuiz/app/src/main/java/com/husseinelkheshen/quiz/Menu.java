package com.husseinelkheshen.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button playButton, quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = findViewById(R.id.play);
        quitButton = findViewById(R.id.quit);

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playQuiz();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void playQuiz()
    {
        startActivity(new Intent(getApplicationContext(), Quiz.class));
    }
}

