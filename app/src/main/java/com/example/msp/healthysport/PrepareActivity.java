package com.example.msp.healthysport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.msp.healthysport.base.BaseActivity;

public class PrepareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setActivityTitle() {
        initTitle();
        setTitle("运动");
        settitleBarBackGround(R.color.watm_background_gray);
        setTitleTextColor(R.color.theme_blue_two);
        setTitleLeftImage(R.mipmap.mrkj_back_blue,this);
    }

    @Override
    protected void getLayoutToView() {
        setContentView(R.layout.activity_prepare);
    }

    @Override
    protected void initValues() {

    }

    @Override
    protected void setViewsListener() {

    }

    @Override
    protected void setViewsFunction() {

    }


}
