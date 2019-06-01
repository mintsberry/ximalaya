package com.mint.ximalaya.interfaces;

public interface IAlbumDetailPresenter {
    /**
     * 下拉刷新
     */
    void pullRefresMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     * @param albumId
     */
    void getAlbumDetail(int albumId, int page);


    void registerViewCallback(IAlbumDetailVaiewCallback detailViewCallback);

    void unRegisterViewCallback(IAlbumDetailVaiewCallback detailViewCallback);
}
