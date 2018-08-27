package com.example.msp.healthysport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.msp.healthysport.base.BaseActivity;

import java.io.File;

/**
 *
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener {

    private Button btnEdit;
    private File tempFIle;

    private static final int PHOTO_REQUEST_CAMERA = 1; //拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;//相册选取
    private static final int PHOTO_REQUEST_GALLERY2 = 3;//相册选取
    private static final int PHOTO_REQUEST_RESULT = 4;//结果
    private static final  String PHOTO_FILE_NAME = "tmep_photo.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

        btnEdit = findViewById(R.id.btn_edit);

    }

    @Override
    protected void setActivityTitle() {
        setTitle("更改个人信息");
    }

    @Override
    protected void setViewLayoutResouce() {
        setContentView(R.layout.activity_info);
    }

    @Override
    protected void initValues() {

    }

    @Override
    protected void setViewsListener() {
        btnEdit.setOnClickListener(this);
    }

    @Override
    protected void setViewsFunction() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("选择图片");
                builder.setMessage("可以通过相册和相机来修改图片");
                builder.setPositiveButton("图库", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tempFIle = new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_NAME);
                        getPhotoFromGallery();
                    }
                });
                builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tempFIle = new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_NAME);
//                        takePhotoFromCamera();
                    }
                });
                builder.create();
                builder.show();
                break;
        }
    }

    /**
     *
     */
    public void getPhotoFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
        } else {
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY2);
        }
    }
}


























