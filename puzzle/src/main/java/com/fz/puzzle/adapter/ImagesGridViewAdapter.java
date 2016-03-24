package com.fz.puzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.fz.puzzle.MainActivity;
import com.fz.puzzle.utils.ScreenUtil;

import java.util.List;

/**
 * Created by ffengz on 2016/3/19.
 * Intro : ImageGridView 的 adapter
 */
public class ImagesGridViewAdapter extends BaseAdapter{

    private List<Integer> imgArray;
    private Context context;

    public ImagesGridViewAdapter(List<Integer> imgArray, Context context) {
        this.imgArray = imgArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgArray.size();
    }

    @Override
    public Object getItem(int position) {
        return imgArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        int widthPixels = ScreenUtil.getScreenSize(parent.getContext()).widthPixels;
        int heightPixels = ScreenUtil.getScreenSize(parent.getContext()).heightPixels;

        if (imageView == null) {
            imageView = new ImageView(context);
            // 动态设置图片
            GridView.LayoutParams params = new GridView.LayoutParams(
                    widthPixels/3-24,heightPixels/5 + 12
            );
            imageView.setLayoutParams(params);
            // 填充控件
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView)convertView;
        }

        imageView.setBackgroundColor(Color.BLACK);
        imageView.setImageResource(imgArray.get(position));
        return imageView;
    }
}
