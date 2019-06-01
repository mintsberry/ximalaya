package com.mint.ximalaya.presenters;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mint.ximalaya.interfaces.IAlbumDetailPresenter;
import com.mint.ximalaya.interfaces.IAlbumDetailVaiewCallback;
import com.mint.ximalaya.utils.Constants;
import com.mint.ximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailPresenterImpl implements IAlbumDetailPresenter {
    private static final String TAG = "AlbumDetailPresenterImp";
    private static AlbumDetailPresenterImpl sInstance;
    private Album sTargetAlbum;
    private List<IAlbumDetailVaiewCallback> callbackList = new ArrayList<>();
    private int currentPage = 1;
    private AlbumDetailPresenterImpl(){

    }

    public static AlbumDetailPresenterImpl getInstance(){
        if (sInstance == null){
            synchronized (AlbumDetailPresenterImpl.class){
                if (sInstance == null){
                    sInstance = new AlbumDetailPresenterImpl();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void pullRefresMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(int albumId, int page) {
        //根据页码和专辑id获取
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_ID, albumId + "");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page + "");
        map.put(DTransferConstants.PAGE_SIZE, String.valueOf(Constants.DEFAULT_COUNT));
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                if (trackList != null) {
                    LogUtil.d(TAG, "trackListSize: " + trackList.getTracks().size());
                    handlerAlbumDetailResult(trackList.getTracks());
                }
            }

            @Override
            public void onError(int i, String s) {
                handlerError(s, i);
                LogUtil.d(TAG, "error code:" + i);
                LogUtil.d(TAG, "error mesg" + s);
            }
        });
    }

    /**
     * 网络错误通知UI
     * @param s
     * @param i
     */
    private void handlerError(String s, int i) {
        for (IAlbumDetailVaiewCallback callback : callbackList) {
            callback.onNetworkError(s, i);
        }
    }

    private void handlerAlbumDetailResult(List<Track> tracks) {
        for (IAlbumDetailVaiewCallback callback : callbackList) {
            callback.getDetailListLoaded(tracks);
        }
    }

    @Override
    public void registerViewCallback(IAlbumDetailVaiewCallback detailViewCallback) {
        if (!callbackList.contains(detailViewCallback)) {
            callbackList.add(detailViewCallback);
            if (detailViewCallback != null){
//                LogUtil.d(TAG, sTargetAlbum.getAlbumTitle());
                detailViewCallback.getAlbumLoaded(sTargetAlbum);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(IAlbumDetailVaiewCallback detailViewCallback) {
        callbackList.remove(detailViewCallback);
    }

    public void setTargetAlbum(Album targetAlbum){
        this.sTargetAlbum = targetAlbum;
    }
}
