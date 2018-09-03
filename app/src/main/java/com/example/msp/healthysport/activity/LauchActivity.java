package com.example.msp.healthysport.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.msp.healthysport.R;

/***
 * 启动界面
 * 通过在AndroidManifest.xml中设置theme来配置启动图片，稍作延迟后跳转到主界面
 */

public class LauchActivity extends AppCompatActivity {

    private  Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if(message.what == 1) {
                startActivity(new Intent(LauchActivity.this,MainActivity.class));
            }

            finish();

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lauch);

        handler.sendEmptyMessageDelayed(1,0);
    }
}
