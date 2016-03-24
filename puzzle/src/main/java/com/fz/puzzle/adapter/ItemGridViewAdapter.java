package com.fz.puzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fz.puzzle.R;

import java.util.List;

/**
 * Created by ffengz on 2016/3/23.
 * Intro : item的适配器
 */
public class ItemGridViewAdapter extends BaseAdapter{

    private List<Bitmap> bitmapList;
    private Context context;

    public ItemGridViewAdapter(List<Bitmap> bitmapList, Context context) {
        this.bitmapList = bitmapList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        if (convertView != null){
            imageView = (ImageView) convertView.getTag();
        }else{
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_item,null);
            imageView = (ImageView) convertView;
            convertView.setTag(imageView);
        }

        imageView.setImageBitmap(bitmapList.get(position));
        return imageView;
    }

}
