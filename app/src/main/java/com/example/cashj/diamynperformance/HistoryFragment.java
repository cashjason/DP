package com.example.cashj.diamynperformance;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HistoryFragment extends Fragment implements View.OnClickListener {

    String ID, selectedDate;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    CompactCalendarView compactCalendar;
    TextView monthText;
    SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    ArrayList list;
    Button postGame, postPractice, postBullpen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This is where everything is done for history section
        user = FirebaseAuth.getInstance().getCurrentUser();
        ID =  user.getUid();
        compactCalendar = view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        monthText = view.findViewById(R.id.monthTitle);
        database = FirebaseDatabase.getInstance();
        postGame = view.findViewById(R.id.PostGameEvalBtn);
        postPractice = view.findViewById(R.id.PostPracticeEvalBtn);
        postBullpen = view.findViewById(R.id.PostBullpenBtn);
        postGame.setVisibility(View.GONE);
        postPractice.setVisibility(View.GONE);
        postBullpen.setVisibility(View.GONE);
        postBullpen.setOnClickListener(this);
        postGame.setOnClickListener(this);
        postPractice.setOnClickListener(this);
        postGame.setOnClickListener(this);
        selectedDate = "";
        monthText.setText(dateFormatMonth.format(System.currentTimeMillis()));


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users/"+ID+"/PostGameEval/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Date date = null;
                    try {
                        date = sdf.parse(snap.getKey().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long millis = date.getTime();
                    Event evt = new Event(Color.GREEN, millis);
                    compactCalendar.invalidate();
                    compactCalendar.addEvent(evt);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });//End of UserData Query
        mDatabase.child("users/"+ID+"/PostPracticeEval/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Date date = null;
                    try {
                        date = sdf.parse(snap.getKey().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long millis = date.getTime();
                    Event evt = new Event(Color.RED, millis);
                    compactCalendar.invalidate();
                    compactCalendar.addEvent(evt);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //TODO: Add bullpen evaluations to history section.
        //This is for the bullpen
        mDatabase.child("users/"+ID+"/PostBullpenEval/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Date date = null;
                    try {
                        date = sdf.parse(snap.getKey().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long millis = date.getTime();
                    Event evt = new Event(Color.BLUE, millis);
                    compactCalendar.invalidate();
                    compactCalendar.addEvent(evt);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });//End of UserData Query

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(final Date dateClicked) {
                mDatabase.child("users/"+ID+"/PostGameEval/").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        postGame.setVisibility(View.GONE);
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            long currentDate = dateClicked.getTime();
                            Date evalDate = null;
                            try {
                                evalDate = sdf.parse(snap.getKey().toString());
                                selectedDate = snap.getKey();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long millis = evalDate.getTime();
                            if (currentDate == millis){
                                postGame.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                mDatabase.child("users/"+ID+"/PostPracticeEval/").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        postPractice.setVisibility(View.GONE);
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            long currentDate = dateClicked.getTime();
                            Date evalDate = null;
                            try {
                                evalDate = sdf.parse(snap.getKey().toString());
                                selectedDate = snap.getKey();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long millis = evalDate.getTime();
                            if (currentDate == millis){
                                postPractice.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                mDatabase.child("users/"+ID+"/PostBullpenEval/").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        postBullpen.setVisibility(View.GONE);
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            long currentDate = dateClicked.getTime();
                            Date evalDate = null;
                            try {
                                evalDate = sdf.parse(snap.getKey().toString());
                                selectedDate = snap.getKey();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long millis = evalDate.getTime();
                            if (currentDate == millis){
                                postBullpen.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthText.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        compactCalendar.invalidate();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.PostGameEvalBtn) {
            Intent act = new Intent(super.getContext(), DisplayHistory.class);
            act.putExtra("EVAL", "PostGameEval");
            act.putExtra("DATE", selectedDate);
            startActivity(act);
        }
        if (i == R.id.PostPracticeEvalBtn) {
            Intent act = new Intent(super.getContext(), DisplayHistory.class);
            act.putExtra("EVAL", "PostPracticeEval");
            act.putExtra("DATE", selectedDate);
            startActivity(act);
        }
        if (i == R.id.PostBullpenBtn) {
            Intent act = new Intent(super.getContext(), DisplayHistory.class);
            act.putExtra("EVAL", "PostBullpenEval");
            act.putExtra("DATE", selectedDate);
            startActivity(act);
        }

    }
}