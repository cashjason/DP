package com.example.cashj.diamynperformance;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HistoryFragment extends Fragment implements View.OnClickListener {

    String ID, selectedDate;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    CompactCalendarView compactCalendar;
    TextView monthText;
    SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
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
        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        monthText = (TextView) view.findViewById(R.id.monthTitle);
        database = FirebaseDatabase.getInstance();
        postGame = (Button) view.findViewById(R.id.PostGameEvalBtn);
        postPractice = (Button) view.findViewById(R.id.PostPracticeEvalBtn);
        postBullpen = (Button) view.findViewById(R.id.PostBullpenBtn);
        postGame.setVisibility(View.GONE);
        postPractice.setVisibility(View.GONE);
        postBullpen.setVisibility(View.GONE);
        postBullpen.setOnClickListener(this);
        postGame.setOnClickListener(this);
        postPractice.setOnClickListener(this);
        postGame.setOnClickListener(this);
        selectedDate = "";



    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

    }
}