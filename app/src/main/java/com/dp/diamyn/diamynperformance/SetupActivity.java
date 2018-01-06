package com.dp.diamyn.diamynperformance;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SetupActivity extends AppCompatActivity implements View.OnClickListener{
    String ID;
    FirebaseUser user;
    FirebaseDatabase database;
    Button done;
    EditText pName, pNum, pPhone, pHeight, pWeight;
    Spinner pTeam, pYear, pPosition;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        done = findViewById(R.id.doneBtn);
        done.setOnClickListener(this);
        pName = findViewById(R.id.profName);
        pNum = findViewById(R.id.profNum);
        pPhone = findViewById(R.id.profPhone);
        pHeight = findViewById(R.id.profHeight);
        pWeight = findViewById(R.id.profWeight);
        pTeam = findViewById(R.id.teamSpinner);
        pYear = findViewById(R.id.yearSpinner);
        pPosition = findViewById(R.id.positionSpinner);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ID = user.getUid();
    }


    @Override
    public void onClick(View v) {
        //Create the account
        if(pName.length() > 0 && pNum.length() > 0 && pPhone.length() > 0 && pHeight.length() > 0 && pWeight.length() > 0 &&
                pTeam.toString().length() > 0 && pPosition.toString().length() > 0 && pYear.toString().length() > 0){
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Name").setValue(pName.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Number").setValue(pNum.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Phone").setValue(pPhone.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Team").setValue(pTeam.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Year").setValue(pYear.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Position").setValue(pPosition.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Height").setValue(pHeight.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Weight").setValue(pWeight.getText().toString());
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(home);
        }
        else{
            Toast.makeText(this, "Please fill out every box", Toast.LENGTH_SHORT).show();
        }


    }
}