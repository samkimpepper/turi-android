package com.example.turibuildtest0405.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Url;

public class CommonUtil {

    public static MultipartBody.Part forImageSend(ContentResolver contentResolver, Uri selectedImage) {

        Cursor cursor = contentResolver.query(selectedImage, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

        // 사진 보낼 준비
        File file = new File(mediaPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        // 이미지 파일 이름은 무조건 file로 하기

        cursor.close();

        return uploadFile;
    }

    public static MultipartBody.Part forImageSend2(ContentResolver contentResolver, Uri uri) {
        String filePath = uri.getPath();
        File file = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
        } catch(IOException ex) {

        }

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), byteArrayOutputStream.toByteArray());
        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        return uploadFile;

    }

    public static Bitmap resizeImage(Context context, Uri uri, int resize) {
        Bitmap resizeBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while(true) {
                if(width / 2 < resize || height / 2 < resize) {
                    break;
                }
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            resizeBitmap = bitmap;
        } catch(FileNotFoundException ex) {
            Log.d("이미지 비트맵 압축 실패", "resizeImage: ");
            ex.printStackTrace();
        }
        return resizeBitmap;
    }

    public static Bitmap resizeImage(Context context, String src, int resize) {
        Bitmap resizeBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            //Bitmap myBitmap = BitmapFactory.decodeStream(input);
            BitmapFactory.decodeStream(input, null, options);

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while(true) {
                if(width / 2 < resize || height / 2 < resize) {
                    break;
                }
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
            resizeBitmap = bitmap;
        } catch(FileNotFoundException ex) {
            Log.d("이미지 비트맵 압축 실패", "resizeImage: ");
            ex.printStackTrace();
        } catch (MalformedURLException ex) {

        } catch(IOException ex) {

        }
        return resizeBitmap;
    }


}
