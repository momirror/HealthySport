package com.example.msp.healthysport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.msp.healthysport.InfoActivity;
import com.example.msp.healthysport.R;
import com.example.msp.healthysport.base.BaseFragment;


public class MineFragment extends BaseFragment {

    private View view;
    ImageButton editBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,null);
        editBtn = view.findViewById(R.id.btn_edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), InfoActivity.class));
            }
        });

        return view;
    }
}
