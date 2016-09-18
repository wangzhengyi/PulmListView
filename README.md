# PulmListView

上拉加载更多的ListView.

------
# 效果图

![screen](https://github.com/wangzhengyi/PulmListView/raw/master/screenrecord/screen.gif)

------
# Usage

使用上拉加载更多ListView非常简单，分为以下几步：

1. 替换ListView为PulmListView.
2. 使用PulmBaseAdapter来代替BaseAdapter.

Example：
```java
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
    // 设置自定义的加载更多的View(ps:自定义View的根布局id必须声明为id_load_more_layout)
    mPulmListView.setLoadMoreView(createCustomLoadMoreView());
}
```

------
# 原理

[Android上拉加载更多ListView——PulmListView](http://blog.csdn.net/wzy_1988/article/details/52527562)