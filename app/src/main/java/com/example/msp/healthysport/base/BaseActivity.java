package com.example.msp.healthysport.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.msp.healthysport.R;

public abstract class BaseActivity extends AppCompatActivity {

    private TextView titleView;
    private ImageView leftImg,rightImg;
    private RelativeLayout titleBar;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        getLayoutToView();
        initValues();
        setActivityTitle();
        initViews();
        setViewsListener();
        setViewsFunction();
    }


    public void initTitle() {

        titleView = findViewById(R.id.mytitle);
        leftImg = findViewById(R.id.my_left_btn);
        rightImg = findViewById(R.id.my_right_btn);
        leftImg.setVisibility(View.INVISIBLE);
        rightImg.setVisibility(View.INVISIBLE);
        titleBar = findViewById(R.id.title_bar);
    }

    public void settitleBarBackGround(int color) {
        titleBar.setBackgroundResource(color);
    }
    protected abstract void initViews();
    protected abstract void setActivityTitle();


    protected abstract void getLayoutToView();

    protected abstract void initValues();

    public void setTitle(String name) {
        titleView.setText(name);
        leftImg.setVisibility(View.INVISIBLE);
    }

    public void setTitle(String name, final Activity activity) {
        titleView.setText(name);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void setTitleAndShowBack(String name) {
        titleView.setText(name);
        leftImg.setVisibility(View.VISIBLE);
    }


    public void setAllTitleItems(String name, int picID) {
        titleView.setText(name);
        leftImg.setVisibility(View.VISIBLE);

        if(picID != 0 ) {
            rightImg.setImageResource(picID);
        }
    }

    public void setTitleTextColor(int colorID) {
        titleView.setTextColor(colorID);
    }

    public void setTitleLeftImage(int picID,final Activity activity) {
        leftImg.setImageResource(picID);
        leftImg.setVisibility(View.VISIBLE);

        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

    }


    public void setTitleRightImage(int picID) {
        rightImg.setImageResource(picID);
    }

    protected abstract void setViewsListener();

    protected abstract void setViewsFunction();
}
