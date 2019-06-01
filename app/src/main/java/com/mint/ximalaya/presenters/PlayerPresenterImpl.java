package com.mint.ximalaya.presenters;

import android.media.session.MediaController;

import com.mint.ximalaya.base.BaseActivity;
import com.mint.ximalaya.base.BaseApplication;
import com.mint.ximalaya.interfaces.IPlayerCallback;
import com.mint.ximalaya.interfaces.IPlayerPresenter;
import com.mint.ximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public class PlayerPresenterImpl implements IPlayerPresenter {

    private static final String TAG = "PlayerPresenterImpl";
    private XmPlayerManager mXmPlayerManager;

    private PlayerPresenterImpl(){
        mXmPlayerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
    }
    private static PlayerPresenterImpl sPlayerPrensenter;
    public static PlayerPresenterImpl getInstance(){
        if (sPlayerPrensenter == null){
            synchronized (PlayerPresenterImpl.class){
                if (sPlayerPrensenter == null){
                    sPlayerPrensenter = new PlayerPresenterImpl();
                }
            }
        }
        return sPlayerPrensenter;
    }

    private boolean isPlayListset = false;

    public void setPlayList(List<Track> list, int playIndex){
        if (mXmPlayerManager != null) {
            mXmPlayerManager.setPlayList(list, playIndex);
            isPlayListset = true;
        } else {
            LogUtil.d(TAG, "mXmPlayerManager is null");
        }
    }

    @Override
    public void play() {
        if (isPlayListset){
            mXmPlayerManager.play();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void playPre() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int progress) {

    }

    @Override
    public void registerViewCallback(IPlayerCallback iPlayerCallback) {

    }

    @Override
    public void unRegisterViewCallback(IPlayerCallback iPlayerCallback) {

    }
}
