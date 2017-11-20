package com.example.cashj.diamynperformance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordFragment extends android.app.Fragment implements View.OnClickListener {


    View view;
   //Button firstButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.forgot_password_fragment, container, false);
        //firstButton = view.findViewById(R.id.firstButton);
        //firstButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(), "First Fragment Jason", Toast.LENGTH_LONG).show();
    }
}