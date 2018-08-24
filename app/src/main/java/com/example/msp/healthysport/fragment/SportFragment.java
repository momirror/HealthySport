package com.example.msp.healthysport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.msp.healthysport.PrepareActivity;
import com.example.msp.healthysport.R;
import com.example.msp.healthysport.base.BaseFragment;
import com.example.msp.healthysport.entity.TodayInfo;
import com.example.msp.healthysport.utils.Constant;
import com.example.msp.healthysport.utils.HttpUtils;


public class SportFragment extends BaseFragment {

    private View view;
    private ImageButton warm_up_btn;
    private TextView cityText,temperatureText,qualityText;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sport,null);

        warm_up_btn = view.findViewById(R.id.warm_up);


        warm_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PrepareActivity.class));
            }
        });

        cityText = view.findViewById(R.id.text_city);
        temperatureText = view.findViewById(R.id.text_temperature);
        qualityText = view.findViewById(R.id.text_air);

        getWetherData();

        return view;
    }

    private void getWetherData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String weatherStr = HttpUtils.getData(String.format(Constant.WEATHER_URL,"深圳","utf-8"));
                TodayInfo todayInfo =  HttpUtils.parseTodayInfo(weatherStr);
                Log.d("SportFragment",weatherStr);

                //TODO: Update UI

            }
        }).start();


    }

}
