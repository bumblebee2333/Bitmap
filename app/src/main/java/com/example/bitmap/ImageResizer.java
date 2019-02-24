package com.example.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import java.io.IOException;

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public ImageResizer(){}

    public Bitmap decodeSampledFromFile(String imagePath ,int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight,imagePath);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight,String imagePath){
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if(height == 0 || width ==0){
            try {
                ExifInterface exifInterface = new ExifInterface(imagePath);
                int tempHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH
                        , ExifInterface.ORIENTATION_NORMAL);
                int tempWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH
                        , ExifInterface.ORIENTATION_NORMAL);

                height = tempHeight;
                width = tempWidth;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while ((halfHeight/inSampleSize) >= reqHeight
                    && halfWidth/inSampleSize >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
