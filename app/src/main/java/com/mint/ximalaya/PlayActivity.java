package com.mint.ximalaya;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mint.ximalaya.base.BaseActivity;
import com.mint.ximalaya.presenters.PlayerPresenterImpl;

public class PlayActivity extends BaseActivity {

    private PlayerPresenterImpl mPlayerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mPlayerPresenter = PlayerPresenterImpl.getInstance();
        mPlayerPresenter.play();
    }
}
