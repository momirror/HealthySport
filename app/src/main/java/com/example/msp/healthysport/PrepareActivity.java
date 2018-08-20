package com.example.msp.healthysport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.msp.healthysport.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrepareActivity extends BaseActivity implements View.OnClickListener {

    private ImageView sportImage;
    private Button finishBtn;
    private ImageView playBtn;
    private ImageView nextBtn;
    private TextView more;
    private TextView backFromDesc,sportDesc;
    private LinearLayout downOne;
    private LinearLayout downTwo;
    private Boolean isShowDesc;
    private int sportIndex;
    private int what;
    private int[] frameRes = new int[]{R.drawable.sport_animation_0,R.drawable.sport_animation_1,R.drawable.sport_animation_2,
            R.drawable.sport_animation_3,R.drawable.sport_animation_4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void initViews() {

        sportImage = findViewById(R.id.sport_image);
        finishBtn = findViewById(R.id.finish);
        playBtn = findViewById(R.id.play_on_off);
        nextBtn = findViewById(R.id.play_next);
        more = findViewById(R.id.sport_desc);
        backFromDesc = findViewById(R.id.desc_back);
        sportDesc = findViewById(R.id.play_detail);
        downOne = findViewById(R.id.down_one);
        downTwo = findViewById(R.id.down_two);


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

        isShowDesc = false;
        sportIndex = 0;

    }

    @Override
    protected void setViewsListener() {

        finishBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        more.setOnClickListener(this);
        backFromDesc.setOnClickListener(this);
        downOne.setOnClickListener(this);
        downTwo.setOnClickListener(this);

    }


    private String getSportDesc(int type) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();

        try{

            inputStream = getAssets().open("sports/sport"+type+".txt");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            String text = buffer.toString();
            return text;


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void setViewsFunction() {

        sportImage.setImageResource(frameRes[sportIndex]);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish:
                finish();
                break;
            case R.id.desc_back:
            case R.id.sport_desc:
                showDown();
                break;
        }
    }

    private void showDown() {
        if(isShowDesc) {
            downOne.setVisibility(View.VISIBLE);
            downTwo.setVisibility(View.GONE);
        } else {
            sportDesc.setText(getSportDesc(sportIndex));
            downOne.setVisibility(View.GONE);
            downTwo.setVisibility(View.VISIBLE);
        }

        isShowDesc = !isShowDesc;
    }
}
