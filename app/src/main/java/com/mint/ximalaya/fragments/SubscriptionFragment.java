package com.mint.ximalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mint.ximalaya.R;
import com.mint.ximalaya.base.BaseFragment;

public class SubscriptionFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_subscription, container,false);
        return view;
    }
}
