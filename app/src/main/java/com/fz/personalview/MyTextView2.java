package com.fz.personalview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ffengz on 2016/3/17.
 * Intro : 自定义控件 闪动效果的文字
 */
public class MyTextView2 extends TextView{

    private int mViewWidth;
    private TextPaint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int i;

    public MyTextView2(Context context) {
        super(context);
        init();
    }

    public MyTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /////////////////////////////////////////////////////////////////////

    private void init() {

    }

    // 当VIew  的大小发生变化时调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mViewWidth ==0 ){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0 ){
                // 初始化 画笔
                mPaint = getPaint();
                // 初始化 LinearGradient 线性渲染
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{Color.RED, Color.GREEN, Color.BLUE}
                        , null, Shader.TileMode.CLAMP);

                mPaint.setShader(mLinearGradient);

                // 初始化  渲染的模型 矩形
                mGradientMatrix = new Matrix();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mGradientMatrix != null) {
            i += mViewWidth / 7;
            if (i>2 * mViewWidth){
                i = -mViewWidth;
            }

            mGradientMatrix.setTranslate(i,0);

            mLinearGradient.setLocalMatrix(mGradientMatrix);

            postInvalidateDelayed(100);
        }
    }
}
