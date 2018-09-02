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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.msp.healthysport.base.BaseActivity;
import com.example.msp.healthysport.utils.Constant;
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
public class InfoActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


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
    private EditText editNickname,editHeight,editWeight,editStepLen;
    private int gender;//1:男 0：女
    private RadioGroup genderGroup;
    private RadioButton male,female;


    private String mImagePath = Environment.getExternalStorageDirectory()+"/meta/";
    private String avatarPath;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String dateStr = birthdayYear + "-" + ( birthdayMonth + 1) + "-" + birthdayDay;
        changeBirthday.setText(dateStr);

        if(Storage.getStringValues(Constant.NICK,null) != null) {
            editNickname.setText(Storage.getStringValues(Constant.NICK,null));
        }

        if(Storage.getStringValues(Constant.HEIGHT,null) != null) {
            editHeight.setText(Storage.getStringValues(Constant.HEIGHT,null));
        }

        if(Storage.getStringValues(Constant.WEIGHT,null) != null) {
            editWeight.setText(Storage.getStringValues(Constant.WEIGHT,null));
        }

        if(Storage.getStringValues(Constant.STEPLEN,null) != null) {
            editStepLen.setText(Storage.getStringValues(Constant.STEPLEN,null));
        }


        genderGroup.check(gender == 0? R.id.female:R.id.male);

    }

    @Override
    protected void initViews() {

        btnEdit = findViewById(R.id.btn_edit);
        avatar = findViewById(R.id.avatar);
        changeBirthday = findViewById(R.id.btn_change_birthday);
        btnOk = findViewById(R.id.btn_ok);
        editNickname = findViewById(R.id.edit_nickname);
        genderGroup = findViewById(R.id.gender);
        editHeight = findViewById(R.id.edit_high);
        editStepLen = findViewById(R.id.edit_step_len);
        editWeight = findViewById(R.id.edit_weight);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);


        if(avatarPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(avatarPath);
            avatar.setImageBitmap(bitmap);
        }


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
        birthdayYear = Storage.getIntValue(Constant.BIRTHDAYYEAR,1988);
        birthdayMonth = Storage.getIntValue(Constant.BIRTHDAYMONTH,12);
        birthdayDay = Storage.getIntValue(Constant.BIRTHDAYDAY,22);
        avatarPath = Storage.getStringValues(Constant.AVATAR,null);
        gender = Storage.getIntValue(Constant.GENDER,1);


    }

    @Override
    protected void setViewsListener() {
        btnEdit.setOnClickListener(this);
        changeBirthday.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        genderGroup.setOnCheckedChangeListener(this);

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
                        try {
                            tempFIle = createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        getPhotoFromGallery();
                    }
                });
                builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            tempFIle = createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                        birthdayYear = year;
                        birthdayMonth = month;
                        birthdayDay = day;

                        String dateStr = year + "-" + ( month + 1) + "-" + day;
                        changeBirthday.setText(dateStr);

                    }
                },birthdayYear,birthdayMonth,birthdayDay);
                datePickerDialog.setTitle("设置生日日期");
                datePickerDialog.show();
                break;
            case R.id.btn_ok:

                Storage.saveIntValues(Constant.BIRTHDAYYEAR,birthdayYear);
                Storage.saveIntValues(Constant.BIRTHDAYMONTH,birthdayMonth);
                Storage.saveIntValues(Constant.BIRTHDAYDAY,birthdayDay);

                if(tempFIle != null && tempFIle.getPath() != null) {
                    Storage.saveStringValues(Constant.AVATAR, tempFIle.getPath());
                }


                if(!editNickname.getText().toString().equals("")) {
                    Storage.saveStringValues(Constant.NICK,editNickname.getText().toString());
                }

                if(!editHeight.getText().toString().equals("")) {
                    Storage.saveStringValues(Constant.HEIGHT,editHeight.getText().toString());
                }

                if(!editStepLen.getText().toString().equals("")) {
                    Storage.saveStringValues(Constant.STEPLEN,editStepLen.getText().toString());
                }

                if(!editWeight.getText().toString().equals("")) {
                    Storage.saveStringValues(Constant.WEIGHT,editWeight.getText().toString());
                }

                Storage.saveIntValues(Constant.GENDER,gender);



                finish();
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

                    Uri uri = data.getData();
                    crop(uri);

                }
            } else if (requestCode == PHOTO_REQUEST_GALLERY2) {
                if (data != null) {


                }
            } else if(requestCode == PHOTO_REQUEST_CAMERA) {
//                crop(Uri.fromFile(tempFIle));

                Bitmap bitmap = BitmapFactory.decodeFile(tempFIle.getPath());
                avatar.setImageBitmap(bitmap);
            } else if (requestCode == PHOTO_REQUEST_CUT) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(tempFIle.getPath());
                    Log.e("uri", Uri.fromFile(tempFIle).toString());
                    avatar.setImageBitmap(bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件,不添加会失败
        }

        //裁剪比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);

        //输出的尺寸大小
        intent.putExtra("outputX",222);
        intent.putExtra("outputY",222);
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFIle));


        //格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection",true);
        intent.putExtra("return-data",true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        if(male.getId() == checkId) {
            gender = 1;
        } else {
            gender = 0;
        }
    }
}


























