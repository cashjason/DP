package com.dp.diamyn.diamynperformance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText email, emailVerify, password, code;
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
        code = findViewById(R.id.secretCode);
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
            if (code.getText().toString().equals("45324")){
                if (em.equals(emv)){
                    try {
                        mAuth.createUserWithEmailAndPassword(em, pass)
                                .addOnCompleteListener(this, task -> {
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
    }
    public void login(){
        progress.setVisibility(View.VISIBLE);
        try {
            mAuth.signInWithEmailAndPassword(em, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            String ID;
                            FirebaseUser user;
                            DatabaseReference mDatabase;
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            ID = user.getUid();
                            mDatabase.child("users").child(ID).child("PlayerInformation").child("Team").setValue("WSU");
                            mDatabase.child("users").child(ID).child("PlayerInformation").child("Year").setValue("Freshman");
                            mDatabase.child("users").child(ID).child("PlayerInformation").child("Position").setValue("Hitter");
                            Intent home = new Intent(getApplicationContext(), SetupActivity.class);
                            startActivity(home);
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Invalid Login",
                                    Toast.LENGTH_LONG).show();
                            progress.setVisibility(View.GONE);
                        }
                    });
        }catch(Exception e){
            Toast.makeText(CreateAccountActivity.this, "Please enter your information",
                    Toast.LENGTH_LONG).show();
            progress.setVisibility(View.GONE);
        }
    }
}
