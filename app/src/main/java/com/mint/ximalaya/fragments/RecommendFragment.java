package com.mint.ximalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mint.ximalaya.R;
import com.mint.ximalaya.base.BaseFragment;

public class RecommendFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_recommend, container,false);
        return view;
    }
}
