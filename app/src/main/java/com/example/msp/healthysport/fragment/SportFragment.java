package com.example.msp.healthysport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.msp.healthysport.PrepareActivity;
import com.example.msp.healthysport.R;
import com.example.msp.healthysport.base.BaseFragment;



public class SportFragment extends BaseFragment {

    private View view;
    private ImageButton warm_up_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sport,null);

        warm_up_btn = view.findViewById(R.id.warm_up);


        warm_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PrepareActivity.class));
//                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        return view;
    }

}
