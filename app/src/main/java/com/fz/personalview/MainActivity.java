package com.fz.personalview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TopBar mTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopbar = (TopBar) findViewById(R.id.topbar);
        mTopbar.setOnTopBarClickListener(new topbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this, "点击了左边", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(MainActivity.this, "点击了右边", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
