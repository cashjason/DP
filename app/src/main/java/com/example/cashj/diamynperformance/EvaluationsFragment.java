package com.example.cashj.diamynperformance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EvaluationsFragment extends Fragment implements View.OnClickListener {

    Button bullpenEval, gameEval, practiceEval, pitcherGameEval;
    String ID;
    FirebaseUser user;
    FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_evaluations, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This is where everything is done
        practiceEval = (Button) view.findViewById(R.id.postPreacticeEval);
        practiceEval.setOnClickListener(this);
        gameEval = (Button) view.findViewById(R.id.postGameEval);
        gameEval.setOnClickListener(this);
        bullpenEval = (Button) view.findViewById(R.id.bullpenEval);
        bullpenEval.setOnClickListener(this);
        pitcherGameEval = (Button) view.findViewById(R.id.postGameEvalPitcher);
        pitcherGameEval.setOnClickListener(this);
        bullpenEval.setVisibility(View.GONE);
        pitcherGameEval.setVisibility(View.GONE);
        gameEval.setVisibility(View.GONE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ID =  user.getUid();
        database = FirebaseDatabase.getInstance();
        database.getReference("users/"+ID+"/PlayerInformation/Position").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("Hitter")){
                    gameEval.setVisibility(View.VISIBLE);
                }else{
                    bullpenEval.setVisibility(View.VISIBLE);
                    pitcherGameEval.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.postPreacticeEval) {
            System.out.println("Post Practice Eval");
            Intent act = new Intent(getContext(), DisplayEvaluations.class);
            act.putExtra("EVAL", "PostPracticeEval");
            startActivity(act);
        }else if (i == R.id.bullpenEval) {
            System.out.println("Post Bullpen Eval");
            Intent act = new Intent(getContext(), DisplayEvaluations.class);
            act.putExtra("EVAL", "PostBullpenEval");
            startActivity(act);
        } else if (i == R.id.postGameEval) {
            System.out.println("Post Game Eval");
            Intent act = new Intent(getContext(), DisplayEvaluations.class);
            act.putExtra("EVAL", "PostGameEval");
            startActivity(act);
        } else if (i == R.id.postGameEvalPitcher) {
            System.out.println("Post Game Pitcher Eval");
            Intent act = new Intent(getContext(), DisplayEvaluations.class);
            act.putExtra("EVAL", "PostGameEval");
            startActivity(act);
        }
    }
}