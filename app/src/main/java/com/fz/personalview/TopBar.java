package com.fz.personalview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ffengz on 2016/3/17.
 * Intro : 自定义 应用的 上部分 相同的 bar
 */
public class TopBar extends RelativeLayout{

    private int mLeftTextColor;
    private Drawable mLeftTextBackground;
    private String mLeftText;
    private int mRightTextColor;
    private Drawable mRightTextBackground;
    private String mRightText;
    private int mTitleTextColor;
    private float mTitleTextSize;
    private String mTitleText;
    private Button mRightButton;
    private Button mLeftButton;
    private TextView mTitleView;
    private RelativeLayout.LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;
    private topbarClickListener mListener;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initView( context);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView( context);
    }


    //////////////////////////////////////////////////////////////////////////////////////

    // 初始化属性信息
    private void initAttrs(Context context,AttributeSet attrs){
        // 获得所有传入的参数
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        // 从 TypeArray中获得所需要的所有属性
        mLeftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
        mLeftTextBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = ta.getString(R.styleable.TopBar_leftText);

        mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
        mRightTextBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        mRightText = ta.getString(R.styleable.TopBar_rightText);

        mTitleTextColor = ta.getColor(R.styleable.TopBar_mtitleTextColor, 0);
        mTitleTextSize = ta.getDimension(R.styleable.TopBar_mtitleTextSize, 0);
        mTitleText = ta.getString(R.styleable.TopBar_mtitle);

        // 回收资源
        ta.recycle();
    }

    /**
     * 初始化控件内容、属性
     */
    private void initView(Context context){
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        // 为所有控件进行属性赋值
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackgroundDrawable(mLeftTextBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackgroundDrawable(mRightTextBackground);
        mRightButton.setText(mRightText);

        mTitleView.setText(mTitleText);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        // 文字居中
        mTitleView.setGravity(TEXT_ALIGNMENT_CENTER);


        // 为组件设置相应的布局元素
        mLeftParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        // 绑定属性和控件
        addView(mLeftButton,mLeftParams);


        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mRightParams.addRule(ALIGN_PARENT_RIGHT,TRUE);
        // 绑定属性和控件
        addView(mRightButton,mRightParams);


        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(CENTER_IN_PARENT,TRUE);
        // 绑定属性和控件
        addView(mTitleView,mTitleParams);


        // 暴露左右按钮的点击事件接口
        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.leftClick();
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.rightClick();
            }
        });

    }

    /**
     * 暴露回调的接口
     * @param listener 回调接口，处理点击事件
     */
    public void setOnTopBarClickListener(topbarClickListener listener){
            mListener = listener;
    }

    /**
     * 设置按钮visible
     * @param id 按钮的id  0： 左边；   1： 右边
     * @param flag 是否隐藏
     */
    public void setButtonVisable(int id, boolean flag){
        if (flag){
            switch (id){
                case 0:
                    mLeftButton.setVisibility(GONE);
                    break;
                case 1:
                    mRightButton.setVisibility(GONE);
                    break;
                default:
                    break;
            }
        }else {
            switch (id) {
                case 0:
                    mLeftButton.setVisibility(VISIBLE);
                    break;
                case 1:
                    mRightButton.setVisibility(VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }
}
