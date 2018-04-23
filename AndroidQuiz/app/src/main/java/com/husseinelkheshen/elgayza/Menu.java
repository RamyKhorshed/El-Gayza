package com.husseinelkheshen.elgayza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class Menu extends AppCompatActivity {


    Button playButton, quitButton, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = findViewById(R.id.play);
        quitButton = findViewById(R.id.quit);
        logout = findViewById(R.id.logout);

        if (AccessToken.getCurrentAccessToken() == null) {
            startActivity(new Intent(getApplicationContext(),
                    Register.class));
            finish();
        }
        else {
            logout.setVisibility(View.VISIBLE);
        }

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });
    }

    public void playQuiz()
    {
        startActivity(new Intent(getApplicationContext(), Quiz.class));
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }
}

