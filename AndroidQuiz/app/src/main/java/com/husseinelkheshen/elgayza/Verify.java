package com.husseinelkheshen.elgayza;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Verify extends AppCompatActivity {

    private static final String TAG = "Verify";

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
                finish();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailVerification();
            }
        });
    }

    private void EmailVerification() {
        findViewById(R.id.verify).setEnabled(false);

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        findViewById(R.id.verify).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(Verify.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Verify.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
