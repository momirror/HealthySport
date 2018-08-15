package com.example.msp.healthysport.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

public class BaseFragment extends Fragment {

    public LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getContext());
    }
}
