package com.zhengyi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhengyi.library.PulmListView;

public class MainActivity extends AppCompatActivity {
    private PulmListView mPulmListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setViewStatus();
    }

    private void initView() {

    }

    private void setViewStatus() {

    }
}
