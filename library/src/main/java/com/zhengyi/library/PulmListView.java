package com.zhengyi.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * 上拉加载更多的ListView
 */
public class PulmListView extends ListView {
    /**
     * 是否处于加载更多状态中.
     */
    private boolean mIsLoading;

    /**
     * 分页是否结束.
     */
    private boolean mIsPageFinished;

    /**
     * Footer View,支持自定义.
     */
    private View mLoadMoreView;

    private OnScrollListener mUserOnScrollListener;

    private OnPullUpLoadMoreListener mOnPullUpLoadMoreListener;

    public PulmListView(Context context) {
        this(context, null);
    }

    public PulmListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PulmListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mIsLoading = false;
        mIsPageFinished = false;
        mLoadMoreView = new LoadMoreView(getContext());
        addFooterView(mLoadMoreView);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 调用用户设置的OnScrollListener
                if (mUserOnScrollListener != null) {
                    mUserOnScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 调用用户设置的OnScrollListener
                if (mUserOnScrollListener != null) {
                    mUserOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                if (!mIsLoading && !mIsPageFinished && lastVisibleItem == totalItemCount) {
                    if (mOnPullUpLoadMoreListener != null) {
                        mIsLoading = true;
                        mOnPullUpLoadMoreListener.onPullUpLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        this.mUserOnScrollListener = l;
    }

    /**
     * 加载更多结束后ListView回调方法.
     *
     * @param isPageFinished 分页是否结束
     * @param newItems       分页加载的数据
     * @param isFirstLoad    是否第一次加载数据(用于配置下拉刷新框架使用，避免出现页面闪现)
     */
    public void onFinishLoading(boolean isPageFinished, List<?> newItems, boolean isFirstLoad) {
        mIsLoading = false;
        setIsPageFinished(isPageFinished);
        if (newItems != null && newItems.size() > 0) {
            PulmBaseAdapter adapter = (PulmBaseAdapter) ((HeaderViewListAdapter) getAdapter()).getWrappedAdapter();
            adapter.addMoreItems(newItems, isFirstLoad);
        }
    }

    private void setIsPageFinished(boolean isPageFinished) {
        mIsPageFinished = isPageFinished;
        if (mIsPageFinished) {
            removeFooterView(mLoadMoreView);
        } else {
            if (findViewById(R.id.id_load_more_layout) == null) {
                addFooterView(mLoadMoreView);
            }
        }
    }

    /**
     * 设置自定义的加载更多View
     *
     * @param view 加载更多View
     */
    public void setLoadMoreView(View view) {
        removeFooterView(mLoadMoreView);
        mLoadMoreView = view;
        addFooterView(mLoadMoreView);
    }

    /**
     * 设置上拉加载更多的回调接口.
     */
    public void setOnPullUpLoadMoreListener(OnPullUpLoadMoreListener l) {
        this.mOnPullUpLoadMoreListener = l;
    }

    /**
     * 上拉加载更多的回调接口
     */
    public interface OnPullUpLoadMoreListener {
        void onPullUpLoadMore();
    }
}
