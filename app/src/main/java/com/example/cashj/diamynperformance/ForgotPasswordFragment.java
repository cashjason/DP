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

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Forgot Password Fragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_fragment, container, false);



        return view;
    }

    @Override
    public void onClick(View v) {
        int i = getView().getId();
        if (i == R.id.btnLogin){
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            //navigate to home activity
        }else if(i == R.id.btnCreateAccount){
            ((MainActivity)getActivity()).setmViewPager(1);

        }else if(i == R.id.btnForgotPass){
            ((MainActivity)getActivity()).setmViewPager(2);
        }
    }
}