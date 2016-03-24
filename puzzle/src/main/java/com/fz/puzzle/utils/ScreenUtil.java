package com.fz.puzzle.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by ffengz on 2016/3/17.
 * Intro : 得到屏幕的相关参数
 */
public class ScreenUtil {

    /**
     * 获取屏幕的宽高蚕食
     * @param context
     * @return 屏幕的宽高
     */
    public static DisplayMetrics getScreenSize(Context context){

        // 屏幕属性的相关类 包含大小、像素密度、缩放等属性
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取窗口管理类
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        // 显示屏的属性  包含两种 除去装饰 或 全部
        Display display = windowManager.getDefaultDisplay();
        // 获取屏幕属性
        display.getMetrics(metrics);
        return metrics;
    }


    /**
     * 获取屏幕的像素密度
     * @param context
     * @return density
     */
    public static float getDeviceDensity(Context context){
//        // 获取显示器的度量
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metrics);
//        return metrics.density;

        return getScreenSize(context).density;
    }

}
