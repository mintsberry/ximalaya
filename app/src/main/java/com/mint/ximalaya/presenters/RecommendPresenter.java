package com.mint.ximalaya.presenters;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mint.ximalaya.interfaces.IRecommendPresenter;
import com.mint.ximalaya.interfaces.IRecommendViewCallback;
import com.mint.ximalaya.utils.Constants;
import com.mint.ximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RecommendPresenter";
    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();
    private RecommendPresenter(){ }

    private static RecommendPresenter sInstance = null;

    /**
     * 获取单例对象
     * @return
     */
    public static RecommendPresenter getInstance(){
        if (sInstance == null){
            synchronized (RecommendPresenter.class){
                if (sInstance == null){
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }
    /**
     * 获取推荐内容，猜你喜欢
     */
    @Override
    public void getRecommendList() {
        Map<String, String> map = new HashMap<String, String>();
        //表示一页数据返回多少条
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMAND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null){
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    LogUtil.d(TAG, "thread name --> " + Thread.currentThread().getName());
                    if (albumList != null){
                        Log.d(TAG, "onSuccess: " + albumList.size());
                        //更新UI
                        handlerRecommendResult(albumList);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "error->code:" + i + " " + s);
            }
        });
    }



    private void handlerRecommendResult(List<Album> albumList) {
        //通知UI
        if (mCallbacks != null){
            for (IRecommendViewCallback mCallback : mCallbacks) {
                mCallback.onCommendListLoaded(albumList);
            }
        }
    }


    @Override
    public void pullRefresMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null &&!mCallbacks.contains(callback)){
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null){
            mCallbacks.remove(mCallbacks);
        }
    }
}
