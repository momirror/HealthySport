package com.example.msp.healthysport;

import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.msp.healthysport.base.BaseActivity;
import com.example.msp.healthysport.utils.GetPictureFromLocation;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener {

    private Button btnEdit;
    private File tempFIle;

    private static final int PHOTO_REQUEST_CAMERA = 1; //拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;//相册选取
    private static final int PHOTO_REQUEST_GALLERY2 = 3;//相册选取
    private static final int PHOTO_REQUEST_CUT = 4;//结果
    private static final  String PHOTO_FILE_NAME = "tmep_photo.jpg";
    private static ImageView avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

        btnEdit = findViewById(R.id.btn_edit);
        avatar = findViewById(R.id.avatar);

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
        intent.setType("image/*");


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
        } else {
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY2);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                if (data != null) {

                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        avatar.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    //TODO:crop image

                }
            } else if (requestCode == PHOTO_REQUEST_GALLERY2) {
                if (data != null) {


                }
            }
        }
    }






    private void crop(Uri uri) {
        Log.d("URI",uri.getPath());

        //裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop",true);

        //裁剪比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);

        //输出的尺寸大小
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFIle));

        //格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection",true);
        intent.putExtra("return-data",true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}


























