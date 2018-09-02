package com.example.msp.healthysport.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        setViewLayoutResouce();
        initValues();
        setActivityTitle();
        initViews();
        setViewsListener();
        setViewsFunction();
    }





    //初始化title bar相关控件
    public void initTitle() {

        titleView = findViewById(R.id.mytitle);
        leftImg = findViewById(R.id.my_left_btn);
        rightImg = findViewById(R.id.my_right_btn);
        leftImg.setVisibility(View.INVISIBLE);
        rightImg.setVisibility(View.INVISIBLE);
        titleBar = findViewById(R.id.title_bar);
    }

    //设置标签栏背景颜色
    public void settitleBarBackGround(int color) {
        titleBar.setBackgroundResource(color);
    }

    //在这个方法中取出layout中的控件
    protected abstract void initViews();

    //设置title
    protected abstract void setActivityTitle();


    //给view设置layout文件
    protected abstract void setViewLayoutResouce();

    //初始化actitivity中使用到的数据
    protected abstract void initValues();

    //设置title
    public void setTitle(String name) {

        if(titleView == null) {
            initTitle();
        }

        titleView.setText(name);
        leftImg.setVisibility(View.INVISIBLE);
        settitleBarBackGround(R.color.watm_background_gray);
        setTitleTextColor(R.color.theme_blue_two);
        setTitleLeftImage(R.mipmap.mrkj_back_blue,this);
    }

    //设置title,并传入相入的activity引用
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

    //设置title和返回键
    public void setTitleAndShowBack(String name) {
        titleView.setText(name);
        leftImg.setVisibility(View.VISIBLE);
    }

   //设置title bar上的所有控件
    public void setAllTitleItems(String name, int picID) {
        titleView.setText(name);
        leftImg.setVisibility(View.VISIBLE);

        if(picID != 0 ) {
            rightImg.setImageResource(picID);
        }
    }

    //设置title颜色
    public void setTitleTextColor(int colorID) {
        titleView.setTextColor(colorID);
    }

    //设置title bar左边的控件的图片（通常是返回）
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


    //设置title bar右边的图片
    public void setTitleRightImage(int picID) {
        rightImg.setImageResource(picID);
    }

    //设置该activity中用到的listener
    protected abstract void setViewsListener();


    protected abstract void setViewsFunction();


    /***自定义转场动画***/

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    /*******以上为自定义转卖动画*******/
}
