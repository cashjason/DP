package com.example.cashj.diamynperformance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class DisplayEvaluations extends AppCompatActivity implements View.OnClickListener {
    String ID;
    TextView title, q1, q2, q3, q4, q5,q6, bq1, bull1, bull2, bull3, bull4, abr1, abr2,
            pitch1Q, pitch2Q, pitch3Q, pitch4Q, pitch5Q, pitch6Q;
    SeekBar rSeek, aSeek, iSeek, dSeek, eSeek, r2Seek, bSeek, ab1Seek, ab2Seek, pitch1Seek, pitch2Seek;
    EditText notes, bull1Notes, bull2Notes, bull3Notes, bull4Notes, ab1Notes, ab2Notes, ab3Notes,
            ab4Notes, ab5Notes, ab6Notes, ab7Notes, pgn1, pgn2, pgn3, pgn4, aNotes;
    LinearLayout general, postGamePitcher, postGame, postBullpen;
    SimpleDateFormat simpledateFormat;
    String eval, date;
    DatabaseReference mDatabase;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_evaluations);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            eval = extras.getString("eval");
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ID = user.getUid();
        simpledateFormat = new SimpleDateFormat("MM-dd-yyyy");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        date = simpledateFormat.format(System.currentTimeMillis());
        title = (TextView) findViewById(R.id.evalTitle);
        title.setText(eval);
        q1 = (TextView) findViewById(R.id.q1);
        q2 = (TextView) findViewById(R.id.q2);
        q3 = (TextView) findViewById(R.id.q3);
        q4 = (TextView) findViewById(R.id.q4);
        q5 = (TextView) findViewById(R.id.q5);
        q6 = (TextView) findViewById(R.id.q6);
        bq1 = (TextView) findViewById(R.id.bq1);
        bull1 = (TextView) findViewById(R.id.bull1);
        bull2 = (TextView) findViewById(R.id.bull2);
        bull3 = (TextView) findViewById(R.id.bull3);
        bull4 = (TextView) findViewById(R.id.bull4);
        abr1 = (TextView) findViewById(R.id.abd1);
        abr2 = (TextView) findViewById(R.id.abd2);
        pitch1Q = (TextView) findViewById(R.id.pq1);
        pitch2Q = (TextView) findViewById(R.id.pq2);
        pitch3Q = (TextView) findViewById(R.id.pitch1Q);
        pitch4Q = (TextView) findViewById(R.id.pitch2Q);
        pitch5Q = (TextView) findViewById(R.id.pitch3Q);
        pitch6Q = (TextView) findViewById(R.id.pitch4Q);
        rSeek = (SeekBar) findViewById(R.id.Rseek);
        aSeek = (SeekBar) findViewById(R.id.Aseek);
        iSeek = (SeekBar) findViewById(R.id.Iseek);
        dSeek = (SeekBar) findViewById(R.id.Dseek);
        eSeek = (SeekBar) findViewById(R.id.Eseek);
        r2Seek = (SeekBar) findViewById(R.id.R2seek);
        bSeek = (SeekBar) findViewById(R.id.bpSeek);
        ab1Seek = (SeekBar) findViewById(R.id.abSeek);
        ab2Seek = (SeekBar) findViewById(R.id.ab2Seek);
        pitch1Seek = (SeekBar) findViewById(R.id.pitch1Seek);
        pitch2Seek = (SeekBar) findViewById(R.id.pitch2Seek);
        notes = (EditText) findViewById(R.id.notesText);
        bull1Notes = (EditText) findViewById(R.id.b1Notes);
        bull2Notes = (EditText) findViewById(R.id.b2Notes);
        bull3Notes = (EditText) findViewById(R.id.b3Notes);
        bull4Notes = (EditText) findViewById(R.id.b4Notes);
        ab1Notes = (EditText) findViewById(R.id.ab1Notes);
        ab2Notes = (EditText) findViewById(R.id.ab2Notes);
        ab3Notes = (EditText) findViewById(R.id.ab3Notes);
        ab4Notes = (EditText) findViewById(R.id.ab4Notes);
        ab5Notes = (EditText) findViewById(R.id.ab5Notes);
        ab6Notes = (EditText) findViewById(R.id.ab6Notes);
        ab7Notes = (EditText) findViewById(R.id.ab7Notes);
        pgn1 = (EditText) findViewById(R.id.pitch1A);
        pgn2 = (EditText) findViewById(R.id.pitch2A);
        pgn3 = (EditText) findViewById(R.id.pitch3A);
        pgn4 = (EditText) findViewById(R.id.pitch4A);
        aNotes = (EditText) findViewById(R.id.aNotes);
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setOnClickListener(this);
        general = (LinearLayout) findViewById(R.id.general);
        postGame = (LinearLayout) findViewById(R.id.postGameHitter);
        postGamePitcher = (LinearLayout) findViewById(R.id.postGamePitcher);
        postBullpen = (LinearLayout) findViewById(R.id.postBullpen);
        postGame.setVisibility(View.GONE);
        postGamePitcher.setVisibility(View.GONE);
        postBullpen.setVisibility(View.GONE);
        mDatabase.child("users/"+ID+"/PlayerInformation/Position").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("Hitter")){
                    if (eval.equals("PostGameEval")){
                        postGame.setVisibility(View.VISIBLE);
                    }
                }else if (dataSnapshot.getValue().toString().equals("Pitcher")){
                    if (eval.equals("PostGameEval")){
                        postGamePitcher.setVisibility(View.VISIBLE);
                    }
                    else if (eval.equals("PostBullpenEval")){
                        postBullpen.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child("Questions/General").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getKey().equals("question1")){
                        q1.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("question2")){
                        q2.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("question3")){
                        q3.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("question4")){
                        q4.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("question5")){
                        q5.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("question6")){
                        q6.setText(snap.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child("Questions/Batter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getKey().equals("atBat1")){
                        abr1.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("atBat2")){
                        abr2.setText(snap.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child("Questions/Pitcher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getKey().equals("bullpen")){
                        bull1.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("bullpen1")){
                        bull2.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("bullpen2")){
                        bull3.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("bullpen3")){
                        bull4.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("game1")){
                        pitch1Q.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("game2")){
                        pitch2Q.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("game3")){
                        pitch3Q.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("game4")){
                        pitch4Q.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("game5")){
                        pitch5Q.setText(snap.getValue().toString());
                    }else if (snap.getKey().equals("game6")){
                        pitch6Q.setText(snap.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        
        //Add all of the post Practice stuff here
        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("question1Answer").setValue(rSeek.getProgress()+1);

        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("question2Answer").setValue(aSeek.getProgress()+1);

        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("question3Answer").setValue(iSeek.getProgress()+1);

        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("question4Answer").setValue(dSeek.getProgress()+1);

        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("question5Answer").setValue(eSeek.getProgress()+1);

        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("question6Answer").setValue(r2Seek.getProgress()+1);

        mDatabase.child("users").child(ID).child(eval)
                .child(date).child("notes").setValue(notes.getText().toString());

        mDatabase.child("users/"+ID+"/PlayerInformation/Position").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("Hitter")){
                    if (eval.equals("PostGameEval")){
                        //Add hitter post game eval section to database
                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat1Answer").setValue(ab1Seek.getProgress()+1);

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat2Answer").setValue(ab2Seek.getProgress()+1);

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat1Notes").setValue(ab1Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat2Notes").setValue(ab2Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat3Notes").setValue(ab3Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat4Notes").setValue(ab4Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat5Notes").setValue(ab5Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat6Notes").setValue(ab6Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("atBat7Notes").setValue(ab7Notes.getText().toString());
                    }
                }else if (dataSnapshot.getValue().toString().equals("Pitcher")){
                    if (eval.equals("PostGameEval")){
                        //Add pitcher post game eval section to database
                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("pitchQuestion1Answer").setValue(pitch1Seek.getProgress()+1);

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("pitchQuestion2Answer").setValue(pitch2Seek.getProgress()+1);

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("pitchQuestion3NotesAnswer").setValue(pgn1.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("pitchQuestion4NotesAnswer").setValue(pgn2.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("pitchQuestion5NotesAnswer").setValue(pgn3.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("pitchQuestion6NotesAnswer").setValue(pgn4.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("additionalNotes").setValue(aNotes.getText().toString());
                    }
                    else if (eval.equals("PostBullpenEval")){
                        //Add pitcher post bullpen eval section to database
                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("bullpenQuestion1Answer").setValue(bSeek.getProgress()+1);

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("bullpenQuestion1Notes").setValue(bull1Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("bullpenQuestion2Notes").setValue(bull2Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("bullpenQuestion3Notes").setValue(bull3Notes.getText().toString());

                        mDatabase.child("users").child(ID).child(eval)
                                .child(date).child("bullpenQuestion4Notes").setValue(bull4Notes.getText().toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
