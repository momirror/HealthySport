package com.example.msp.healthysport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.text.DecimalFormat;

import mrkj.library.wheelview.circlebar.CircleBar;


public class SportFragment extends BaseFragment {

    private int currentSteps = 0;
    private int duration = 800;
    private View view;
    private ImageButton warm_up_btn;
    private TextView cityText,temperatureText,qualityText,show_mileage,show_heat,want_steps;
    private Boolean isStop = false;
    private CircleBar circleBar;
    private double distanceValue = 0;//里程
    private int customStepLen = 4;
    private double heat_values = 0;
    private int customWeight = 50;




    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
//                    TodayInfo todayInfo = (TodayInfo) message.obj;
//                    cityText.setText("城市: "+todayInfo.getCity());
//                    temperatureText.setText("温度: "+todayInfo.getTemperature());
//                    qualityText.setText("空气质量: "+todayInfo.getQuality());
                    break;
                case 2:
                    currentSteps += 5;
                    circleBar.update(currentSteps,duration);
                    duration = 0;

                    //里程
                    distanceValue = customStepLen * currentSteps*0.01*0.001;
                    show_mileage.setText(formatDouble(distanceValue)+"公里");

                    //热量
                    heat_values = customWeight * distanceValue * 1.036;
                    show_heat.setText(formatDouble(heat_values)+ "大卡");

                    break;
            }

            return false;
        }
    });



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sport,null);

        warm_up_btn = view.findViewById(R.id.warm_up);


        warm_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().startActivity(new Intent(getContext(), PrepareActivity.class));
//                throw new RuntimeException("这是自己定义的异常");

            }
        });

        cityText = view.findViewById(R.id.text_city);
        temperatureText = view.findViewById(R.id.text_temperature);
        qualityText = view.findViewById(R.id.text_air);
        circleBar = view.findViewById(R.id.show_progress);
        show_mileage = view.findViewById(R.id.mileage_text);
        show_heat = view.findViewById(R.id.heat_text);
        want_steps = view.findViewById(R.id.text_plan);

        getWetherData();
        updateStep();

        return view;
    }

    private void getWetherData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String weatherStr = HttpUtils.getData(String.format(Constant.WEATHER_URL,"深圳","utf-8"));
                TodayInfo todayInfo =  HttpUtils.parseTodayInfo(weatherStr);
                Log.d("SportFragment",weatherStr);

                Message message = Message.obtain();
                message.what = 1;
                message.obj = todayInfo;
                handler.sendMessage(message);

            }
        }).start();


    }

    private void updateStep() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private String formatDouble(Double value){
        DecimalFormat format = new DecimalFormat("####.##");
        String strValue = format.format(value);
        return strValue.equals("0")?"0.00":strValue;
    }

}
