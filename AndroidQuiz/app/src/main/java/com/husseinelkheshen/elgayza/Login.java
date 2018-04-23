package com.husseinelkheshen.elgayza;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button login, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        login = findViewById(R.id.login);
        back = findViewById(R.id.back);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == login){
                    LoginUser();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == back){
                    startActivity(new Intent(getApplicationContext(),
                            Register.class));
                    finish();
                }
            }
        });
    }

    public void LoginUser(){
        String Email = emailText.getText().toString().trim();
        String Password = passwordText.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            currentUser = mAuth.getCurrentUser();
                            finish();
                            if (currentUser.isEmailVerified()) {
                                startActivity(new Intent(getApplicationContext(),
                                        Menu.class));
                            }
                            else {
                                startActivity(new Intent(getApplicationContext(),
                                        Verify.class));
                            }
                        }else {
                            Toast.makeText(Login.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
