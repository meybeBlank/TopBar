package com.fz.puzzle.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ffengz on 2016/3/19.
 * Intro : 获得本地和相机的图片
 */
public class ForImageUtil {

    /* 图片响应码 */
    public static final int RESULT_IMAGE = 100;
    /* 图片的类型 */
    public static final String IMAGE_TYPE = "image/*";

    /* 相机响应码 */
    public static final int RESULT_CAMERA = 200;
    /* 图片的临时存储目录 */
    public static String TEMP_IMAGE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/temp.png";


    /**
     * 请求本地图库的图片
     */
    public static void requestImageForLocation(Activity activity) {
        // 打开本地图库
        // Intent ACTION_PICK：根据传入的类型选择数据
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 设置请求数据的路径、类型
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_TYPE
        );
        activity.startActivityForResult(intent,RESULT_IMAGE);
    }


    /**
     * 调用系统相机进行拍照
     * @param activity
     */
    public static void requestImageForCamera(Activity activity){

        // 调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 照片的uri
        Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));

        // 传递图片的路径
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                photoUri
        );
        activity.startActivityForResult(intent, RESULT_CAMERA);

    }

}
