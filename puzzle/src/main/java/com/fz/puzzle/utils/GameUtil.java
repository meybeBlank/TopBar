package com.fz.puzzle.utils;

import android.graphics.Bitmap;

import com.fz.puzzle.entiy.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffengz on 2016/3/22.
 * Intro :  游戏工具类
 */
public class GameUtil {

    // 游戏小图片集合
    public static List<ImageItem> mImageItems = new ArrayList<>();
    // 空图片
    public static ImageItem imageBlank = new ImageItem();

    /**
     * 交换图片的位置
     * @param from
     * @param blank
     */
    public static void swapItems(ImageItem from, ImageItem blank){
        ImageItem temp = new ImageItem();
        // 交换BitmapID
        temp.setmBitmapID(from.getmBitmapID());
        from.setmBitmapID(blank.getmBitmapID());
        blank.setmBitmapID(temp.getmBitmapID());
        // 交换bitmap
        temp.setBitmap(from.getBitmap());
        from.setBitmap(blank.getBitmap());
        blank.setBitmap(temp.getBitmap());
        // 设置新的Blank
        GameUtil.imageBlank = from;
    }

    /**
     * 是否可以移动
     * @param position
     * @return
     */
    public static boolean isMoveable(int position){
        // 获得空白图片的Item
        int blankID = GameUtil.imageBlank.getmItemID() - 1;
        // 获取难度类型
        int type = MyApp.difficulty;

        // 不同行 相差为type
        if (Math.abs(blankID - position) == type) {
            return true;
        }
        // 相同行 相差为1
        if ((blankID / type == position / type) && Math.abs(blankID - position) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 计算倒置和算法
     *
     * @param data
     * @return 该序列的倒置和
     */
    public static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }

    /**
     * 判断是否有解 生成就有解
     * @param data
     * @return
     */
    public static boolean canSolve(List<Integer> data) {
        // 生成有解
        if (isSuccess()){
            getPuzzle();
        }
        // 获取空格Id
        int blankId = GameUtil.imageBlank.getmItemID();
        // 可行性原则
        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            // 从底往上数,空格位于奇数行
            if (((int) (blankId - 1) / MyApp.difficulty) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                // 从底往上数,空位位于偶数行
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 判断是否拼图成功
     * @return
     */
    public static boolean isSuccess(){
        for (ImageItem tempBean : GameUtil.mImageItems) {
            if (tempBean.getmBitmapID() != 0 && (tempBean.getmItemID()) == tempBean.getmBitmapID()) {
                continue;
            } else if (tempBean.getmBitmapID() == 0 && tempBean.getmItemID() == MyApp.difficulty * MyApp.difficulty) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 将图片随机打乱
     */
    public static void getPuzzle(){
        int index = 0;
        for (int i = 0; i < mImageItems.size(); i++) {
            index = (int) (Math.random() * MyApp.difficulty * MyApp.difficulty );
            swapItems(mImageItems.get(index), GameUtil.imageBlank);
        }
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < mImageItems.size(); i++) {
            data.add(mImageItems.get(i).getmBitmapID());
        }
        // 判断生成是否有解
        if (canSolve(data)) {
            return;
        } else {
            getPuzzle();
        }
    }


}
