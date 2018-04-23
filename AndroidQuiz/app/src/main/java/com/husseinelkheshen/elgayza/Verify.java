package com.husseinelkheshen.elgayza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Verify extends AppCompatActivity {

    private TextView email;
    private TextView status;

    private Button verify;
    private Button logout;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        email = findViewById(R.id.email);
        status = findViewById(R.id.status);

        verify = findViewById(R.id.verify);
        logout = findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(!user.isEmailVerified())
        {
            verify.setVisibility(View.VISIBLE);
            status.setText(R.string.not_verified);
        }
        else
        {
            status.setText(R.string.verified);
        }

        email.setText(user.getEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
