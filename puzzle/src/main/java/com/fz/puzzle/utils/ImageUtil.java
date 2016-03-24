package com.fz.puzzle.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.fz.puzzle.PuzzleActivity;
import com.fz.puzzle.R;
import com.fz.puzzle.entiy.ImageItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffengz on 2016/3/22.
 * Intro : 图片处理工具类
 */
public class ImageUtil {

    /**
     * 图片初始化、切图
     * @param type 游戏难度
     * @param picSelected 所选择的图片
     * @param context 上下文
     */
    public void createInitBitmap(int type, Bitmap picSelected, Context context){

        // 清空数据
        GameUtil.mImageItems.clear();

        ImageItem imageItem = null;

        Bitmap bitmap = null;
        List<Bitmap> bitmapList = new ArrayList<>();
        // 碎片的大小
        int itemWidth = picSelected.getWidth() / type;
        int itemHeight = picSelected.getHeight() / type;

        for (int i = 1; i <= type; i++) {
            for (int j = 1; j <= type; j++) {
                /**
                 * 切割图片
                 * 第一个参数：父图片
                 * 头两个参数：左上角坐标，
                 * 后两个参数：宽高
                 */
                bitmap = Bitmap.createBitmap(
                        picSelected,
                        (j-1)*itemWidth,
                        (i-1)*itemHeight,
                        itemWidth,
                        itemHeight
                );

                bitmapList.add(bitmap);
                // 获得小图片
                imageItem = new ImageItem(
                        (i-1)*type +j,
                        (i-1)*type +j,
                        bitmap
                );
                // 添加小图片
                GameUtil.mImageItems.add(imageItem);
            }
        }
        // 保存最后一个图片在拼图完成时填充
        PuzzleActivity.mLastBitmap = bitmapList.get(MyApp.difficulty * MyApp.difficulty -1);

        // 设置最后一个为空
        bitmapList.remove(type*type -1);
        GameUtil.mImageItems.remove(type*type -1);

        Bitmap bitmapBlank = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank);
        bitmapBlank = Bitmap.createBitmap(bitmapBlank,0,0,itemWidth,itemHeight);

        bitmapList.add(bitmapBlank);
        GameUtil.mImageItems.add(new ImageItem(type*type,0,bitmapBlank));
        GameUtil.imageBlank = GameUtil.mImageItems.get(MyApp.difficulty * MyApp.difficulty -1);
    }

    /**
     *  处理图片的大小到合理的大小
     * @param newWidth
     * @param newHeight
     * @param bitmap
     * @return
     */
    public Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap){
        Matrix matrix = new Matrix();

        matrix.postScale(
                newWidth/bitmap.getWidth(),
                newHeight/bitmap.getHeight()
        );
        // 按照模型缩放
        Bitmap newBitmap = bitmap.createBitmap(
                bitmap,0,0,
                bitmap.getWidth(),
                bitmap.getHeight(),
                matrix,true
        );
        return newBitmap;
    }


    /**
     * 根据图片字节数组，对图片可能进行二次采样，不致于加载过大图片出现内存溢出
     * @return
     */
    public static Bitmap decodeBitmap(String path) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高
        BitmapFactory.decodeFile(path, opts);

        opts.inSampleSize = 10;
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        return BitmapFactory.decodeFile(path,opts);
    }

    /**
     * 根据图片字节数组，对图片可能进行二次采样，不致于加载过大图片出现内存溢出
     * @return
     */
    public static Bitmap decodeBitmap(Resources resources, int picSelectID){
        // 获得options对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只加载边缘，避免内存泄漏
        options.inJustDecodeBounds = true;
        // 将图片传入加载器
        BitmapFactory.decodeResource(resources, picSelectID, options);
        // 对图片进行指定比例的压缩
        int sampleSize = 10; //默认缩放
        //并且制定缩放比例
        options.inSampleSize = sampleSize;
        //不再只加载图片实际边缘
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, picSelectID,options);
    }


}
