package com.dp.diamyn.diamynperformance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by cashj on 12/2/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {
    String ID;
    FirebaseUser user;
    FirebaseDatabase database;
    Button done;
    EditText pName, pNum, pPhone, pHeight, pWeight;
    Spinner pTeam, pYear, pPosition;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This is where everything is done
        done = view.findViewById(R.id.doneBtn);
        done.setOnClickListener(this);
        pName = view.findViewById(R.id.profName);
        pNum = view.findViewById(R.id.profNum);
        pPhone = view.findViewById(R.id.profPhone);
        pHeight = view.findViewById(R.id.profHeight);
        pWeight = view.findViewById(R.id.profWeight);
        pTeam = view.findViewById(R.id.teamSpinner);
        pYear = view.findViewById(R.id.yearSpinner);
        pPosition = view.findViewById(R.id.positionSpinner);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ID = user.getUid();

        database.getReference("users/" + ID + "/PlayerInformation/Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pName.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pNum.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pPhone.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Height").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pHeight.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Weight").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pWeight.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Team").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String[] pTeamArr = getResources().getStringArray(R.array.team_arrays);
                pTeam.setSelection(0);
                for (int j = 0; j < pTeamArr.length; j++) {
                    try{
                        if (value.equals(pTeamArr[j])) {
                            pTeam.setSelection(j);
                        }
                    }catch(NullPointerException e){
                        continue;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Year").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String[] pYearArr = getResources().getStringArray(R.array.year_arrays);
                pYear.setSelection(0);
                for (int j = 0; j < pYearArr.length; j++) {
                    try{
                        if (value.equals(pYearArr[j])) {
                            pYear.setSelection(j);
                        }
                    }catch(NullPointerException e){
                        continue;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Position").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String[] pPosArr = getResources().getStringArray(R.array.position_arrays);
                pPosition.setSelection(0);
                for (int j = 0; j < pPosArr.length; j++) {
                    try{
                        if (value.equals(pPosArr[j])) {
                            pPosition.setSelection(j);
                        }
                    }catch(NullPointerException e){
                        continue;
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
        int i = v.getId();
        if (i == R.id.doneBtn) {
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Name").setValue(pName.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Number").setValue(pNum.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Phone").setValue(pPhone.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Team").setValue(pTeam.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Year").setValue(pYear.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Position").setValue(pPosition.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Height").setValue(pHeight.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Weight").setValue(pWeight.getText().toString());
            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
        }
    }


}
