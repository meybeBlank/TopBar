package com.fz.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fz.puzzle.adapter.ItemGridViewAdapter;
import com.fz.puzzle.utils.ForImageUtil;
import com.fz.puzzle.utils.GameUtil;
import com.fz.puzzle.utils.ImageUtil;
import com.fz.puzzle.utils.MyApp;
import com.fz.puzzle.utils.ScreenUtil;
import com.fz.puzzle.utils.aaaa;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /* 最后一张图片 */
    public static Bitmap mLastBitmap = null;
    /* 被选中的图片 */
    Bitmap picSelect = null;
    /* 游戏难度的类型 */
    int type = MyApp.difficulty;
    /* gridview控件 */
    private GridView mGVItem;
    /* 获取bitmap集合 */
    private List<Bitmap> bitmapItemList = new ArrayList<>();
    private ItemGridViewAdapter itemAdapter;
    private ImageView mIVOriginal;
    private TextView mTVStep;
    private TextView mTVTime;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private int stepCount = 0;

    /* 是否成功 */
    private boolean isSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        getSupportActionBar().hide();

        // 获取传递过来的图片
        getSelectPic();

        // 对图片进行处理
        handlerImage();

        // 初始化控件
        initView();

        // 生成游戏
        generateGame();

        // 开始计时
        startTimer();
    }

    /**
     * 生成游戏
     */
    private void generateGame() {
        // 清除集合
        bitmapItemList.clear();
        // 获得按顺序排列的图片
        new ImageUtil().createInitBitmap(type, picSelect, this);
        // 打乱顺序
        GameUtil.getPuzzle();
        // 获取bitmap集合
        for (int i = 0; i < GameUtil.mImageItems.size(); i++) {
            bitmapItemList.add(GameUtil.mImageItems.get(i).getBitmap());
        }

        itemAdapter = new ItemGridViewAdapter(bitmapItemList,this);
        mGVItem.setAdapter(itemAdapter);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        mGVItem = (GridView) findViewById(R.id.gv_puzzle_pic_item);
        // 设置展示的行列数
        mGVItem.setNumColumns(type);
        mGVItem.setOnItemClickListener(this);

        mIVOriginal = (ImageView) findViewById(R.id.iv_puzzle_original);

        mTVStep = (TextView) findViewById(R.id.tv_puzzle_step);

        mTVTime = (TextView) findViewById(R.id.tv_puzzle_time);
    }

    /**
     * 对图片进行处理
     */
    private void handlerImage() {
        // 将图片放大到固定尺寸
        int screenWidth = ScreenUtil.getScreenSize(this).widthPixels;
        int screenHeigt = ScreenUtil.getScreenSize(this).heightPixels;
        picSelect = new ImageUtil().resizeBitmap(screenWidth * 0.8f, screenHeigt * 0.7f, picSelect);
    }

    /**
     * 获取传递的图片
     */
    private void getSelectPic() {
        // 获取选择的图片
        Bundle extras = getIntent().getExtras();
        int picSelectID = extras.getInt("picSelectID");
        String mPicPath = extras.getString("mPicPath");


        if (picSelectID != 0){
//            picSelect = ImageUtil.decodeBitmap(getResources(),picSelectID);
            picSelect = BitmapFactory.decodeResource(getResources(),picSelectID);
        }else {
            picSelect= ImageUtil.decodeBitmap(mPicPath);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////
    //  监听事件

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 不能移动
        if (!GameUtil.isMoveable(position)){
            return;
        }

        // 交换点击图片与空白图片的位置
        GameUtil.swapItems(GameUtil.mImageItems.get(position), GameUtil.imageBlank);

        // 记步
        stepCount++;
        mTVStep.setText(String.valueOf(stepCount));

        // 重新获取变换后的图片
        bitmapItemList.clear();
        for (int i = 0; i < GameUtil.mImageItems.size(); i++) {
            bitmapItemList.add(GameUtil.mImageItems.get(i).getBitmap());
        }
        // 更新
        itemAdapter.notifyDataSetChanged();

        if (GameUtil.isSuccess()){
            // 成功
            afterSuccess();
        }
    }

    /**
     * 成功之后
     */
    private void afterSuccess() {
        // 重新获取变换后的图片
        bitmapItemList.clear();
        for (int i = 0; i < GameUtil.mImageItems.size()-1; i++) {
            bitmapItemList.add(GameUtil.mImageItems.get(i).getBitmap());
        }
        bitmapItemList.add(mLastBitmap);
        itemAdapter.notifyDataSetChanged();

        // 成功之后,不能点击
        mGVItem.setOnItemClickListener(null);
        // 计时器停止
        mTimer.cancel();

        isSuccess = true;

        Toast.makeText(PuzzleActivity.this, "拼图成功！！！", Toast.LENGTH_SHORT).show();


    }


    ///////////////////////////////////////////////////////////////////////////////
    ///   三个按钮的功能实现

    /**
     * 展示原图
     * @param view
     */
    public void getAriginal(View view) {

        // 停止计时
        mTimer.cancel();

        mIVOriginal.setImageBitmap(picSelect);
        // 展示最后一张图片
        isOriginal = true;
        mIVOriginal.setVisibility(View.VISIBLE);
        mGVItem.setVisibility(View.GONE);
    }

    /* 是否是展示原图界面 */
    private boolean isOriginal = false;

    /**
     * 返回事件
     * @param view
     */
    public void back(View view) {
        if (isOriginal){

            if ( !isSuccess ){
                // 重新开始计时
                startTimer();
            }
            isOriginal = false;
            mIVOriginal.setVisibility(View.GONE);
            mGVItem.setVisibility(View.VISIBLE);
        }else {
            // 返回上一个activity
            onBackPressed();
        }
    }

    /**
     * 重置
     * @param view
     */
    public void restart(View view) {
        // 重新开启监听
        mGVItem.setOnItemClickListener(this);
        // 计时、记步 清零
        mTimer.cancel();
        timeCount =0;
        stepCount =0;
        mTVTime.setText("0");
        mTVStep.setText("0");
        // 重启计时
        startTimer();

        isSuccess = false;

        generateGame();
    }


    /////////////////////////////////////////////////////////////////////////////////
    // 记步 功能的实现

    private int timeCount = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                timeCount++;
                mTVTime.setText(String.valueOf(timeCount));
            }
            super.handleMessage(msg);
        }
    };

    private void startTimer(){
        // 使用 Timer 计时器计时
        // 启用计时器
        mTimer = new Timer(true);
        // 开启计时器线程,计时不能在主线程
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                // 线程之间通讯
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        };

        mTimer.schedule(mTimerTask, 0, 1000);
    }

}
