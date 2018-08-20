package com.example.msp.healthysport.application;

import android.app.Application;
import android.widget.Toast;

public class SportApplication extends Application {
    public static String[] sportNames = new String[5];

    @Override
    public void onCreate(){
        super.onCreate();

        sportNames[0] = "俯身哑铃飞鸟";
        sportNames[1] = "俯卧撑";
        sportNames[2] = "滚轮支点俯卧撑";
        sportNames[3] = "平板卧推";
        sportNames[4] = "仰卧平板杠铃肱三弯举";

    }
}
