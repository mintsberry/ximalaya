package com.mint.ximalaya.interfaces;

import com.mint.ximalaya.base.IBasePresenter;

public interface IRecommendPresenter extends IBasePresenter<IRecommendViewCallback> {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新
     */
    void pullRefresMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 这个方法用于注册UI的回调
     */

}
