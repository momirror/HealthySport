package com.example.msp.healthysport;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msp.healthysport.application.SportApplication;
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
    private TextView more,sportName;
    private TextView backFromDesc,sportDesc,playTime;
    private LinearLayout downOne;
    private LinearLayout downTwo;
    private Boolean isShowDesc;
    private int sportIndex = 0;
    private int what;
    private AnimationDrawable animationDrawable;
    private Boolean isPlaying = false;
    private int values;
    private ProgressBar progressBar;
    private Boolean isStopCal = false;
    private Thread thread;
    private int[] frameRes = new int[]{R.drawable.sport_animation_0,R.drawable.sport_animation_1,R.drawable.sport_animation_2,
            R.drawable.sport_animation_3,R.drawable.sport_animation_4};
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what) {
                case 1:

                    int value = (int) message.obj;

                    if(value < 10) {
                        playTime.setText("00:0"+value);
                    } else {
                        playTime.setText("00:"+value);
                    }

                    if(value == 11) {
                        isStopCal = true;

                        if(animationDrawable != null && animationDrawable.isRunning()) {
                            animationDrawable.stop();
                        }


                    }

                    progressBar.setProgress(value);

                    if(value == 12) {
                        Toast.makeText(PrepareActivity.this,"热身完成",Toast.LENGTH_SHORT);
                        playBtn.setImageResource(R.mipmap.mrkj_play_start);
                        isPlaying = false;
                        progressBar.setProgress(0);
                        playTime.setText("00:00");
                        values = 0;
                    }


                    break;
            }

            return false;
        }
    });

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
        sportName = findViewById(R.id.sport_name);
        progressBar = findViewById(R.id.play_progress);
        playTime = findViewById(R.id.sport_play_time);


    }

    @Override
    protected void setActivityTitle() {
        initTitle();
        setTitle("运动");

    }

    @Override
    protected void setViewLayoutResouce() {
        setContentView(R.layout.activity_prepare);
    }

    @Override
    protected void initValues() {

        isShowDesc = false;
        sportIndex = 0;
        values = 0;

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

    private void calculateTime() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStopCal) {
                    try{
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = ++values;
                        handler.sendMessage(message);

                    }catch (InterruptedException io) {
                        io.printStackTrace();
                    }
                }
            }
        });

        thread.start();
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
        sportName.setText(SportApplication.sportNames[sportIndex]);

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
            case R.id.play_on_off:
                playSportAnimations();
                break;
            case R.id.play_next:
                playNext();
                break;
        }
    }

    private void playSportAnimations() {

        animationDrawable = (AnimationDrawable) sportImage.getDrawable();

        if(!isPlaying) {
            animationDrawable.start();
            playBtn.setImageResource(R.mipmap.mrkj_play_stop);
            isStopCal = false;
            calculateTime();
        } else {
            animationDrawable.stop();
            isStopCal = true;
            playBtn.setImageResource(R.mipmap.mrkj_play_start);
        }

        isPlaying = !isPlaying;



    }

    private void playNext() {

        sportIndex++;
        isPlaying = false;
        playBtn.setImageResource(R.mipmap.mrkj_play_start);

        if(animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }



        if(sportIndex > 4) {
            sportIndex = 0;
        }

        sportImage.setImageResource(frameRes[sportIndex]);
        sportName.setText(SportApplication.sportNames[sportIndex]);

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
