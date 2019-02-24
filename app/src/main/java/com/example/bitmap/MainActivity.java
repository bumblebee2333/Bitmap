package com.example.bitmap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private ImageView imageView;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //ImageResizer imageResizer = new ImageResizer();
        //imageView.setImageBitmap(imageResizer.decodeSampledFromFile(getResources(),R.drawable.lixinyi,300,300));
    }

    private void initView(){
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.image);
        button.setOnClickListener(this);
    }

    private void openLoacalAlbum(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                openLoacalAlbum();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//            String[] filePathColumns = {MediaStore.Images.Media.DATA};
//            Cursor c = getContentResolver().query(selectedImage,filePathColumns,
//                    null,null,null);
//            c.moveToFirst();
//            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = RealPathFromUriUtils.getPathByUri4kitkat(this,data.getData());
            ImageResizer imageResizer = new ImageResizer();
            imageView.setImageBitmap(imageResizer.decodeSampledFromFile(imagePath,300,300));
        }
    }
}
