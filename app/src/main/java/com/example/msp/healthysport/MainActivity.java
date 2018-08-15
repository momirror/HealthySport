package com.example.msp.healthysport;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.msp.healthysport.base.BaseActivity;
import com.example.msp.healthysport.fragment.DiscovFragment;
import com.example.msp.healthysport.fragment.HeartFragment;
import com.example.msp.healthysport.fragment.MineFragment;
import com.example.msp.healthysport.fragment.SportFragment;
import com.example.msp.healthysport.utils.Constant;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup tabbar;
    private RadioButton btn_sport,btn_discov,btn_heart,btn_mine;
    private SportFragment sportFragment;
    private DiscovFragment discovFragment;
    private HeartFragment heartFragment;
    private MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setViewsListener();

    }

    @Override
    public void  setViewsListener() {

        tabbar.setOnCheckedChangeListener(this);

    }

    private void initView() {

        tabbar = findViewById(R.id.tab_group);
        btn_sport = findViewById(R.id.btn_sport);
        btn_discov = findViewById(R.id.btn_discov);
        btn_heart = findViewById(R.id.btn_heart);
        btn_mine = findViewById(R.id.btn_mine);

        sportFragment = new SportFragment();
        discovFragment = new DiscovFragment();
        heartFragment = new HeartFragment();
        mineFragment = new MineFragment();


        //初始化fragment界面
        getSupportFragmentManager().beginTransaction().add(R.id.flag_content,sportFragment, Constant.SPOT_TAG).commit();


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (checkId) {
            case R.id.btn_sport:
                if(!sportFragment.isAdded()) {
                    transaction.replace(R.id.flag_content,sportFragment,Constant.SPOT_TAG);
                }
                break;
            case R.id.btn_discov:
                if(!discovFragment.isAdded()) {
                    transaction.replace(R.id.flag_content,discovFragment,Constant.DISCOV_TAG);
                }
                break;
            case R.id.btn_heart:
                if(!heartFragment.isAdded()) {
                    transaction.replace(R.id.flag_content,heartFragment,Constant.HEART_TAG);
                }
                break;
            case R.id.btn_mine:
                if(!mineFragment.isAdded()) {
                    transaction.replace(R.id.flag_content,mineFragment,Constant.MINE_TAG);
                }
                break;
        }

        transaction.commit();

    }
}
