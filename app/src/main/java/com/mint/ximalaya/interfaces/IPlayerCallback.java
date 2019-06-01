package com.mint.ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public interface IPlayerCallback {
    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 播放暂停
     */
    void onPlayPause();

    /**
     * 播放停止
     */
    void onPlayStop();

    /**
     * 播放错误
     */
    void onPlayError();

    /**
     * 下一首播放
     */
    void nextPlay(Track track);

    /**
     * 上一首播放
     */
    void prePlay(Track track);

    /**
     * 播放列表数据加载
     * @param list
     */
    void onListLoaded(List<Track> tracks);

    /**
     * 播放模式改变
     * @param mode
     */
    void onPlayModeChage(XmPlayListControl.PlayMode mode);

    /**
     * 播放器进度改变
     */
    void onProgressChange(long currentProgress, long total);

    /**
     * 广告加载
     */
    void onAdLoading();

    /**
     * 广告结束
     */
    void onAdFinished();
}
