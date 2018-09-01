package com.example.msp.healthysport;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msp.healthysport.base.BaseActivity;
import com.example.msp.healthysport.utils.GetPictureFromLocation;
import com.example.msp.healthysport.utils.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 *
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener {

    private Button btnEdit;
    private File tempFIle;
    private Uri imageUri;

    private static final int PHOTO_REQUEST_CAMERA = 1; //拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;//相册选取
    private static final int PHOTO_REQUEST_GALLERY2 = 3;//相册选取
    private static final int PHOTO_REQUEST_CUT = 4;//结果
    private static final  String PHOTO_FILE_NAME = "tmep_photo.jpg";
    private static ImageView avatar;
    private static TextView changeBirthday;
    private int birthdayYear,birthdayMonth,birthdayDay;

    private String mImagePath = Environment.getExternalStorageDirectory()+"/meta/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String dateStr = birthdayYear + "-" + ( birthdayMonth + 1) + "-" + birthdayDay;
        changeBirthday.setText(dateStr);

    }

    @Override
    protected void initViews() {

        btnEdit = findViewById(R.id.btn_edit);
        avatar = findViewById(R.id.avatar);
        changeBirthday = findViewById(R.id.btn_change_birthday);

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
        birthdayYear = Storage.getIntValue("birthdayYear",1988);
        birthdayMonth = Storage.getIntValue("birthdayMonth",12);
        birthdayDay = Storage.getIntValue("birthdayDay",22);
    }

    @Override
    protected void setViewsListener() {
        btnEdit.setOnClickListener(this);
        changeBirthday.setOnClickListener(this);
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
                        takePhotoFromCamera();
                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.btn_change_birthday:


                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateStr = year + "-" + ( month + 1) + "-" + day;
                        changeBirthday.setText(dateStr);

                        Storage.saveIntValues("birthdayYear",year);
                        Storage.saveIntValues("birthdayMonth",month);
                        Storage.saveIntValues("birthdayDay",day);

                    }
                },birthdayYear,birthdayMonth,birthdayDay);
                datePickerDialog.setTitle("设置生日日期");
                datePickerDialog.show();
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


    protected void takePhotoFromCamera() {

        String tag = "takephoto";


        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");


        //判断是否是7.0的系统
        if (Build.VERSION.SDK_INT < 24) {
            //拍照的照片路径
            imageUri = Uri.fromFile(tempFIle);//7.0这里会闪退，imgfile是图片文件路径
            Log.i(tag, "7.0之前的系统，拍照的照片：imageUri路径：" + imageUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        } else {

            takePicture();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePicture(){


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                tempFIle = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (tempFIle != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.msp.healthysport.fileprovider",
                        tempFIle);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PHOTO_REQUEST_CAMERA);
            }
        }

    }




    private boolean hasSDCard() {

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }




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
            } else if(requestCode == PHOTO_REQUEST_CAMERA) {
//                crop(Uri.fromFile(tempFIle));

                Bitmap bitmap = BitmapFactory.decodeFile(tempFIle.getPath());
                avatar.setImageBitmap(bitmap);
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


























