package com.fz.puzzle.entiy;

import android.graphics.Bitmap;

/**
 * Created by ffengz on 2016/3/22.
 * Intro : 单张图片的实体类
 */
public class ImageItem {


    private int mItemID;
    private int mBitmapID;
    private Bitmap bitmap;

    public ImageItem() {
    }

    public int getmItemID() {

        return mItemID;
    }

    public void setmItemID(int mItemID) {
        this.mItemID = mItemID;
    }

    public int getmBitmapID() {
        return mBitmapID;
    }

    public void setmBitmapID(int mBitmapID) {
        this.mBitmapID = mBitmapID;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ImageItem(int mItemID, int mBitmapID, Bitmap bitmap) {

        this.mItemID = mItemID;
        this.mBitmapID = mBitmapID;
        this.bitmap = bitmap;
    }
}
