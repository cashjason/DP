package com.dp.diamyn.diamynperformance;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ProgressFragment extends Fragment implements View.OnClickListener, ValueEventListener, OnChartValueSelectedListener {

    String ID;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    ArrayList<Integer> R1, A, I, D, E, R2;
    ArrayList<Long> dates, datesSorted;
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    BarChart chart;
    LineChart chart2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This is where everything is done
        user = FirebaseAuth.getInstance().getCurrentUser();
        ID = user.getUid();
        database = FirebaseDatabase.getInstance();
        R1 = new ArrayList<>();
        A = new ArrayList<>();
        I = new ArrayList<>();
        D = new ArrayList<>();
        E = new ArrayList<>();
        R2 = new ArrayList<>();
        dates = new ArrayList<>();
        datesSorted = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users/"+ID+"/PostGameEval").addValueEventListener(this);
        mDatabase.child("users/"+ID+"/PostPracticeEval").addValueEventListener(this);
        mDatabase.child("users/"+ID+"/PostBullpenEval").addValueEventListener(this);
        chart = view.findViewById(R.id.chart);
        chart2 = view.findViewById(R.id.chart2);
        //chart1 = (BarChart) view.findViewById(R.id.bchart);
        //YourData[] dataObjects = ...;
//
//        List<Entry> entries = new ArrayList<Entry>();
//
//        for (YourData data : dataObjects) {
//
//            // turn your data into Entry objects
//            entries.add(new Entry(data.getValueX(), data.getValueY()));
//        }


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

    }

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

            dates.add(millis);
            String s = snap.getValue().toString();
            s = s.substring(1, s.length()-1);
            List<String> list = new ArrayList<String>(Arrays.asList(s.split(", ")));
            for(int i = 0; i < list.size(); i++) {
                System.out.println("Values is " + list.get(i));
                List<String> items = new ArrayList<String>(Arrays.asList(list.get(i).split("=")));
                if(items.get(0).equals("question1Answer")){
                    R1.add(Integer.valueOf(items.get(1)));
                }else if(items.get(0).equals("question2Answer")){
                    A.add(Integer.valueOf(items.get(1)));
                }else if(items.get(0).equals("question3Answer")){
                    I.add(Integer.valueOf(items.get(1)));
                }else if(items.get(0).equals("question4Answer")){
                    D.add(Integer.valueOf(items.get(1)));
                }else if(items.get(0).equals("question5Answer")){
                    E.add(Integer.valueOf(items.get(1)));
                }else if(items.get(0).equals("question6Answer")){
                    R2.add(Integer.valueOf(items.get(1)));
                }
            }
        }
        updateGraph();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public double getAve(ArrayList<Integer> arr){
        double val = 0;
        for (int i = 0; i < arr.size(); i++){
            val = val + arr.get(i);
        }
        val = val / arr.size();
        return val;
    }

    ArrayList<Integer> newR1 = new ArrayList<>();
    ArrayList<Integer> newA = new ArrayList<>();
    ArrayList<Integer> newI = new ArrayList<>();
    ArrayList<Integer> newD = new ArrayList<>();
    ArrayList<Integer> newE = new ArrayList<>();
    ArrayList<Integer> newR2 = new ArrayList<>();

    public void updateGraph(){
        //System.out.println("Stuff  = " + dates.size() + datesSorted.size());
        datesSorted.clear();
        for (int i = 0; i < dates.size(); i++){
            datesSorted.add(dates.get(i));
        }
//        ArrayList<Integer> newR1 = new ArrayList<>();
//        ArrayList<Integer> newA = new ArrayList<>();
//        ArrayList<Integer> newI = new ArrayList<>();
//        ArrayList<Integer> newD = new ArrayList<>();
//        ArrayList<Integer> newE = new ArrayList<>();
//        ArrayList<Integer> newR2 = new ArrayList<>();
        newR1.clear();
        newA.clear();
        newI.clear();
        newD.clear();
        newE.clear();
        newR2.clear();
        Collections.sort(datesSorted);
        System.out.println("Dates = " + dates);
        System.out.println("Dates Sorted = " + datesSorted);
        for(int i = 0; i < dates.size(); i++){
            for(int j = 0; j < datesSorted.size(); j++){
                if(dates.get(i) == datesSorted.get(j)){
                    newR1.add(R1.get(j));
                    newA.add(A.get(j));
                    newI.add(I.get(j));
                    newD.add(D.get(j));
                    newE.add(E.get(j));
                    newR2.add(R2.get(j));
                }
            }
        }

        System.out.println("R1 = " + newR1);
        System.out.println("A = " + newA);
        System.out.println("I = " + newI);
        System.out.println("D = " + newD);
        System.out.println("E = " + newE);
        System.out.println("R2 = " + newR2);

        List<BarEntry> entries = new ArrayList<BarEntry>();

        entries.add(new BarEntry(1, (float) (0 + getAve(newR1))));
        entries.add(new BarEntry(2, (float) (0 + getAve(newA))));
        entries.add(new BarEntry(3, (float) (0 + getAve(newI))));
        entries.add(new BarEntry(4, (float) (0 + getAve(newD))));
        entries.add(new BarEntry(5, (float) (0 + getAve(newE))));
        entries.add(new BarEntry(6, (float) (0 + getAve(newR2))));

        BarDataSet dataSet = new BarDataSet(entries, "Average of evaluation scores");
        dataSet.setBarBorderColor(4);
        //dataSet.setColors(new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW });
        dataSet.setColors(Color.parseColor("#AF8446"));
        // this is the color i want #Af8446
        BarData barData = new BarData(dataSet);

        Legend legend = chart.getLegend();
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);



        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();

        rightAxis.removeAllLimitLines();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setMinWidth(0);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        leftAxis.setMaxWidth(10);

        chart.setData(barData);
        chart.setNoDataTextColor(Color.GRAY);
        chart.setScaleEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateY(300);
        chart.setOnChartValueSelectedListener(this);
        chart.invalidate(); // refresh

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int value = (int) e.getX();
        if (value == 0){
            showQuestionGraph(newR1, "Confidence");
        }else if(value == 1){
            showQuestionGraph(newA, "Self-Talk");
        }
        else if(value == 2){
            showQuestionGraph(newI, "Controlling the controllables");
        }
        else if(value == 3){
            showQuestionGraph(newD, "Competing one pitch at a time");
        }
        else if(value == 4){
            showQuestionGraph(newE, "Clear / calm mind");
        }
        else if(value == 5){
            showQuestionGraph(newR2, "Relaxed body");
        }
    }

    @Override
    public void onNothingSelected() {
    }

    public void showQuestionGraph(ArrayList<Integer> question, String title){

        List<Entry> entries = new ArrayList<>();

        //add entries to the list
        for(int i = 0; i < question.size(); i ++){
            entries.add(new BarEntry(0 + i, 0 + question.get(i)));
        }
        //entries.add(new BarEntry(0, (float) (0 + getAve(newR1))));

        Legend legend = chart2.getLegend();
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = chart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.WHITE);

        //xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart2.getAxisLeft();
        YAxis rightAxis = chart2.getAxisRight();


        rightAxis.removeAllLimitLines();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawAxisLine(false);
        //leftAxis.setDrawGridLines(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setMinWidth(0);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        leftAxis.setMaxWidth(10);
        LineDataSet dataSet = new LineDataSet(entries, "" + title);
        //dataSet.setColors(new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW });
        dataSet.setColors(Color.parseColor("#AF8446"));
        // this is the color i want #Af8446
        LineData lineData = new LineData(dataSet);

        //This hides the labels
        xAxis.setDrawLabels(false);
        leftAxis.setDrawLabels(false);

        chart2.setNoDataTextColor(Color.GRAY);

        chart2.animateXY(150, 150);
        chart2.getDescription().setEnabled(false);
        chart2.setData(lineData);
        chart2.invalidate(); // refresh
    }
}
