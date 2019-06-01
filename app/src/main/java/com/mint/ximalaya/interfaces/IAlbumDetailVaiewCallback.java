package com.mint.ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailVaiewCallback {
    /**
     * 加载详情列表
     * @param tracks
     */
    void getDetailListLoaded(List<Track> tracks);

    /**
     *
     * @param album
     */
    void getAlbumLoaded(Album album);

    /**
     * 网络错误
     */
    void onNetworkError(String s, int i);
}
