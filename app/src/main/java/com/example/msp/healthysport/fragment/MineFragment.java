package com.example.msp.healthysport.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.msp.healthysport.activity.FoodHeatActivity;
import com.example.msp.healthysport.activity.InfoActivity;
import com.example.msp.healthysport.R;
import com.example.msp.healthysport.base.BaseFragment;
import com.example.msp.healthysport.utils.Constant;
import com.example.msp.healthysport.utils.Storage;

import mrkj.library.wheelview.circleimageview.CircleImageView;


public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    ImageButton editBtn;
    private CircleImageView avatar;
    private TextView textNick,foodHeat,sportHistory,aboutUs,plan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine,null);
        editBtn = view.findViewById(R.id.btn_edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), InfoActivity.class));
            }
        });
        avatar = view.findViewById(R.id.avatar);
        textNick = view.findViewById(R.id.nick);
        foodHeat = view.findViewById(R.id.food_heat);
        sportHistory = view.findViewById(R.id.sport_history);
        aboutUs = view.findViewById(R.id.about_us);
        plan = view.findViewById(R.id.plan);


        setUp();
        setListeners();

        return view;
    }

    private void setListeners() {
        foodHeat.setOnClickListener(this);
        sportHistory.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        plan.setOnClickListener(this);
    }

    private  void setUp() {
        String avatarPath = Storage.getStringValues("avatarPath",null);
        if(avatarPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(avatarPath);
            avatar.setImageBitmap(bitmap);
        }

        if(Storage.getStringValues(Constant.NICK,null) != null) {
            textNick.setText(Storage.getStringValues(Constant.NICK,null));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_heat:
                getActivity().startActivity(new Intent(getContext(), FoodHeatActivity.class));
                break;
        }
    }
}
