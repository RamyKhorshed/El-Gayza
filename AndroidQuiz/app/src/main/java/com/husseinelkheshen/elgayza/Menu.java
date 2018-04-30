package com.husseinelkheshen.elgayza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {


    Button playButton, quitButton;
    LoginButton loginButton;

    private CallbackManager callbackManager;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = findViewById(R.id.play);
        quitButton = findViewById(R.id.quit);
        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playQuiz();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                mAuth.getInstance().signOut();
//                LoginManager.getInstance().logOut();
                finish();
            }
        });

    }

    public void playQuiz()
    {
        startActivity(new Intent(getApplicationContext(), Timer.class));
    }

}

