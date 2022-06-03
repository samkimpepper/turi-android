package com.example.turibuildtest0405.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
}
