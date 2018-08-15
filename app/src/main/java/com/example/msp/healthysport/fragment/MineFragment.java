package com.example.msp.healthysport.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msp.healthysport.R;
import com.example.msp.healthysport.base.BaseFragment;


public class MineFragment extends BaseFragment {

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,null);


        return view;
    }
}
