package com.dp.diamyn.diamynperformance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText email, password;
    Button login, create;
    TextView forgotPassword;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
        create = findViewById(R.id.btnCreateAccount);
        create.setOnClickListener(this);
        forgotPassword = findViewById(R.id.btnForgotPass);
        forgotPassword.setOnClickListener(this);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPass);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);


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
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        String em = email.getText().toString();
        String pass = password.getText().toString();

        int i = v.getId();
        if (i == R.id.btnLogin) {
            progress.setVisibility(View.VISIBLE);
            try {
                mAuth.signInWithEmailAndPassword(em, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(home);
                                    progress.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Login",
                                            Toast.LENGTH_LONG).show();
                                    progress.setVisibility(View.GONE);
                                }
                            }
                        });
            }catch(Exception e){
                Toast.makeText(LoginActivity.this, "Please enter your information",
                        Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        }
        if (i == R.id.btnCreateAccount){
            Intent account = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(account);
        }
        if (i == R.id.btnForgotPass){
            progress.setVisibility(View.VISIBLE);
            mAuth = FirebaseAuth.getInstance();
            mAuth.sendPasswordResetEmail(email.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email Sent!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this, "No account with the email address entered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}