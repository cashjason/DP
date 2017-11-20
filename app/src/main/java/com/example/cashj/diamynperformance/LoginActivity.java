package com.example.cashj.diamynperformance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by cashj on 11/20/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(this);

        Button create = findViewById(R.id.btnCreateAccount);
        create.setOnClickListener(this);

        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPass);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(home);
        } else {
            // No user is signed in
        }
    }

    @Override
    public void onClick(View v) {
        String em = email.getText().toString();
        String pass = password.getText().toString();
        int i = v.getId();
        if (i == R.id.btnLogin) {
            try {
                mAuth.signInWithEmailAndPassword(em, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(home);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Login",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }catch(Exception e){
                Toast.makeText(LoginActivity.this, "Please enter your information",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (i == R.id.btnCreateAccount){
            Intent account = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(account);
        }
    }
}