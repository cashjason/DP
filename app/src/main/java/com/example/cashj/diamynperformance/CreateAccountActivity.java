package com.example.cashj.diamynperformance;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText email, emailVerify, password;
    Button create;
    String em, emv, pass;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        create = findViewById(R.id.btnCreateAccount);
        create.setOnClickListener(this);
        email = findViewById(R.id.textEmail);
        emailVerify = findViewById(R.id.textEmailVerify);
        password = findViewById(R.id.textPass);
        mAuth = FirebaseAuth.getInstance();
        progress = (ProgressBar) findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnCreateAccount) {
            em = email.getText().toString();
            emv = emailVerify.getText().toString();
            pass = password.getText().toString();
            progress.setVisibility(View.VISIBLE);
            if (em.equals(emv)){
                try {
                    mAuth.createUserWithEmailAndPassword(em, pass)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CreateAccountActivity.this, "Account Created",
                                                Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                        login();
                                    }else{
                                        Toast.makeText(CreateAccountActivity.this, "Account Not Created",
                                                Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                    }
                                }
                            });
                }catch(Exception e){
                    Toast.makeText(CreateAccountActivity.this, "Please enter your information",
                            Toast.LENGTH_LONG).show();
                    progress.setVisibility(View.GONE);
                }
            }else{
                Toast.makeText(CreateAccountActivity.this, "Emails do not match",
                        Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        }
    }
    public void login(){
        progress.setVisibility(View.VISIBLE);
        try {
            mAuth.signInWithEmailAndPassword(em, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress.setVisibility(View.GONE);
                                Intent prof = new Intent(getApplicationContext(), ContactsContract.Profile.class);
                                startActivity(prof);
                            } else {
                                Toast.makeText(CreateAccountActivity.this, "Invalid Login",
                                        Toast.LENGTH_LONG).show();
                                progress.setVisibility(View.GONE);
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(CreateAccountActivity.this, "Please enter your information",
                    Toast.LENGTH_LONG).show();
            progress.setVisibility(View.GONE);
        }
    }
}
