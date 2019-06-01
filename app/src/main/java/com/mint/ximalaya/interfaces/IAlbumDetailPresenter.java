package com.mint.ximalaya.interfaces;

import com.mint.ximalaya.base.IBasePresenter;

public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailVaiewCallback> {
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

}
