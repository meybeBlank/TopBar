package com.fz.puzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fz.puzzle.adapter.ImagesGridViewAdapter;
import com.fz.puzzle.utils.ForImageUtil;
import com.fz.puzzle.utils.MyApp;
import com.fz.puzzle.utils.ScreenUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mTVSelector;
    private GridView mGVPicture;
    private PopupWindow mPopupWindow;
    /* 难度选择 */
    private String[] selectList = new String[]{"2 X 2","3 X 3","4 X 4"};
    /* 图片选择 */
    private Integer[] imageArray = new Integer[]{
        R.drawable.img021,R.drawable.girl003,R.drawable.girl002,R.drawable.girl004,
            R.drawable.girl003,R.drawable.girl002,R.drawable.girl004,
            R.drawable.girl003,
            R.drawable.more
    };
    private ListView mLVSelect;
    private View mViewMain;
    private TextView mTVsizetext;
    private MyApp mMyApp;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取MyApp对象，用于共享数据
        mMyApp = (MyApp) getApplication();
        // 隐藏ActionBar
        hideActionbar();
        // findview
        initView();
        // 生成对话框对象
        getSelectList();
        // 生成图片选择列表
        getImageList();
        // 生成选择方式对话框
        getDialog();


        // 难度的选择事件
        mTVSelector.setOnClickListener(this);

    }


    /**
     * 获取焦点、重置难度
     */
    @Override
    protected void onResume() {
        super.onResume();
        MyApp.difficulty =3 ;
    }

    /**
     * 生成图片的选择列表
     */
    private void getImageList() {
        ArrayList<Integer> imgList = new ArrayList<>();
        for (int i :
                imageArray) {
            imgList.add(i);
        }
        mGVPicture.setAdapter(new ImagesGridViewAdapter(imgList, this));

       mGVPicture.setOnItemClickListener(this);
    }

    /**
     * 获取view
     */
    private void initView() {
        mViewMain = LayoutInflater.from(this).inflate(R.layout.activity_main,null);

        mTVSelector = (TextView) findViewById(R.id.tv_puzzle_selector_pic);
        mGVPicture = (GridView) findViewById(R.id.gv_puzzle_pic_list);
        mTVsizetext = (TextView) findViewById(R.id.tv_viewsize);
    }


    /**
     *  初始化popupWindow，并显示
     * @param view
     */
    private void popupShow(View view){

        mPopupWindow = new PopupWindow(mLVSelect, mTVSelector.getWidth() + 10, 240);
        // 获取popup焦点
        mPopupWindow.setFocusable(true);
        // 点击外面，关闭对话框
        mPopupWindow.setOutsideTouchable(true);
        // 设置透明背景，防止出现问题
        ColorDrawable bg_temp = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(bg_temp);
        // 获取view的坐标位置,存在数组location中
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        // 获得使对话框出现在 2 X 2 正下方 的水平距离
        int halfWidth = ScreenUtil.getScreenSize(this).widthPixels / 2;
        int showWidth = 4 + halfWidth + (mTVsizetext.getWidth() - mTVSelector.getWidth()) / 2;
        // 显示对话框
        mPopupWindow.setAnimationStyle(R.style.select_in);
        mPopupWindow.showAsDropDown(mLVSelect, showWidth, -100);

    }

    /**
     * 生成选择对话框
     */
    private void getDialog(){
        String[] dia = {"从相册选择图片","相机拍摄图片"};
        builder = new AlertDialog.Builder(this);
        builder.setTitle("选择获取方式")
                .setItems(dia, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                ForImageUtil.requestImageForLocation(MainActivity.this);
                                break;
                            case 1:
                                ForImageUtil.requestImageForCamera(MainActivity.this);
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    /**
     * 获取难度选择列表
     */
    private void getSelectList() {
        mLVSelect = (ListView)LayoutInflater.from(this).inflate(R.layout.select_listview, null);
        mLVSelect.setAdapter(new ArrayAdapter<String>(this, R.layout.select_textview,selectList));

        mLVSelect.setOnItemClickListener(this);
    }

    // 隐藏 ActionBar
    private void hideActionbar() {
        getSupportActionBar().hide();
    }


    /////////////////////////////////////////////////////////////////////////
    //  难度选择监听

    @Override
    public void onClick(View v) {
        // 添加popup对话框，选择难度
        popupShow(mViewMain);
    }

    /////////////////////////////////////////////////////////////////////////
    // 难度选择监听

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // 难度选择的监听
        if (parent instanceof ListView){
            switch (position){
                case 0:
                    mTVSelector.setText("2 X 2");
                    mMyApp.difficulty = 2;
                    mPopupWindow.dismiss();
                    break;
                case 1:
                    mTVSelector.setText("3 X 3");
                    mMyApp.difficulty = 3;
                    mPopupWindow.dismiss();
                    break;
                case 2:
                    mTVSelector.setText("4 X 4");
                    mMyApp.difficulty = 4;
                    mPopupWindow.dismiss();
                    break;

                default:
                    break;
            }
            // 图片选择的监听
        } else if (parent instanceof GridView){
            // 普通图片点击事件
            if (position == imageArray.length -1){
                builder.create().show();
            }else {
                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                intent.putExtra("picSelectID", imageArray[position]);
                startActivity(intent);
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////
    ///    重写方法，获取返回的图片

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 请求成功
        if (resultCode == RESULT_OK){
            // 本地图片返回成功
            if (requestCode == ForImageUtil.RESULT_IMAGE
                    && data != null){

                // 相册数据
                Cursor query = this.getContentResolver().query(
                        data.getData(), null, null, null, null
                );
                // 移动到数据第一行
                query.moveToFirst();
                // 获取图片的路径
                String imagePath = query.getString(query.getColumnIndex("_data"));

                // 跳转到拼图界面
                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                intent.putExtra("mPicPath", imagePath);
                query.close();
                startActivity(intent);
            } else if (requestCode == ForImageUtil.RESULT_CAMERA){
                // 相机数据
                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                intent.putExtra("mPicPath", ForImageUtil.TEMP_IMAGE_PATH);
                startActivity(intent);
            }
        }
    }
}
