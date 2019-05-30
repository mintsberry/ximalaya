package com.mint.ximalaya.interfaces;

public interface IRecommendPresenter {
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
    void registerViewCallback(IRecommendViewCallback callback);

    /**
     * 取消回调注册
     * @param callback
     */
    void unRegisterViewCallback(IRecommendViewCallback callback);
}
