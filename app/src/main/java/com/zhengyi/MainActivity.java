package com.zhengyi;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zhengyi.adapter.PulmImplAdapter;
import com.zhengyi.library.PulmListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private PulmListView mPulmListView;
    private PulmImplAdapter mAdapter;
    private List<String> mItems = new ArrayList<>();
    private int index;
    private static final int MAX_NUM = 30;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mItems.addAll(generateItems());
    }

    private List<String> generateItems() {
        List<String> newItems = new ArrayList<>();
        for (int i = 0; i < 12 && index < MAX_NUM; i++, index++) {
            newItems.add("标题是" + index);
        }

        return newItems;
    }

    private void initView() {
        mPulmListView = (PulmListView) findViewById(R.id.id_pulm_lv);
        mAdapter = new PulmImplAdapter(this, mItems);
        mPulmListView.setAdapter(mAdapter);
        // 设置加载更多的回调
        mPulmListView.setOnPullUpLoadMoreListener(new PulmListView.OnPullUpLoadMoreListener() {
            @Override
            public void onPullUpLoadMore() {
                // 实现加载更多
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newItems = generateItems();
                        Log.e(TAG, "new items num=" + newItems + ", already has items=" + mAdapter.getCount());
                        boolean isPageFinished = index >= MAX_NUM;
                        mPulmListView.onFinishLoading(isPageFinished, newItems, false);
                    }
                }, 2000);
            }
        });
    }

    private View createCustomLoadMoreView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        return inflater.inflate(R.layout.custom_load_more_view, mPulmListView, false);
    }
}
