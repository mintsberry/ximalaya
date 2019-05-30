package com.mint.ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendViewCallback {
    /**
     * 获取推荐内容
     * @param result
     */
    void onCommendListLoaded(List<Album> result);

    /**
     * 加载更多
     * @param result
     */
    void onLoadMore(List<Album> result);

    /**
     * 刷新
     * @param result
     */
    void onRefreshMore(List<Album> result);
}
