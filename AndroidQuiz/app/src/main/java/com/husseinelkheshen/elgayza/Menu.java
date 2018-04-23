package com.husseinelkheshen.elgayza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

public class Menu extends AppCompatActivity {


    Button playButton, quitButton;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = findViewById(R.id.play);
        quitButton = findViewById(R.id.quit);

        if (AccessToken.getCurrentAccessToken() == null) {
            startActivity(new Intent(getApplicationContext(),
                    Register.class));
            finish();
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playQuiz();
            }
        });

    }

    public void playQuiz()
    {
        startActivity(new Intent(getApplicationContext(), Quiz.class));
    }

}

