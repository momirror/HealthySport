package com.example.msp.healthysport.application;

import android.app.Application;
import android.widget.Toast;

import com.example.msp.healthysport.utils.BringData;
import com.example.msp.healthysport.utils.CrashHandler;
import com.example.msp.healthysport.utils.Storage;

import java.io.IOException;

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

        //设置异常捕获，将异常日志保存到手机中
        CrashHandler.getInstance().init(this);

        //Preference工具类初始化
        Storage.createSharePrefences(this);

        int saveDateIndex = Storage.getIntValue("date_index",0);
        if(saveDateIndex == 0) {
            try {
                Storage.saveIntValues("date_index",1);
                BringData.getDataFromAssets(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
