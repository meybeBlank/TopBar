package com.fz.personalview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ffengz on 2016/3/17.
 * Intro : 自定义的带边框的TextView
 */
public class MyTextView extends TextView{


    private Paint mPaint1;
    private Paint mPaint2;

    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ////////////////////////////////////////////////////////////

    // 构造方法中初始化画笔
    private void init() {
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setColor(Color.RED);
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.BLUE);
        mPaint2.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // 在原生的绘制之前绘制重叠的矩形
        // 最外层
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        // 中层
        canvas.drawRect(20,20,getMeasuredWidth()-20,getMeasuredHeight()-20,mPaint2);

        canvas.save();

        // 将文字绘制平移20px
        canvas.translate(20,20);


        super.onDraw(canvas);

        canvas.restore();
    }
}
