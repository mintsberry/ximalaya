package com.mint.ximalaya.fragments;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mint.ximalaya.R;
import com.mint.ximalaya.adapters.RecommendListAdapter;
import com.mint.ximalaya.base.BaseFragment;
import com.mint.ximalaya.interfaces.IRecommendViewCallback;
import com.mint.ximalaya.presenters.RecommendPresenter;
import com.mint.ximalaya.utils.Constants;
import com.mint.ximalaya.utils.LogUtil;
import com.mint.ximalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback, UILoader.OnRetryClickListener {
    private static final String TAG = "RecommendFragment";
    private View view;
    private RecommendListAdapter recommendListAdapter;
    private RecommendPresenter mRecommendPrestneter;
    private UILoader uiLoader;

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {

         uiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater, container);
            }
        };

        //获取数据
        //获取逻辑层对象
        mRecommendPrestneter = RecommendPresenter.getInstance();
        //设置接口通知
        mRecommendPrestneter.registerViewCallback(this);
        mRecommendPrestneter.getRecommendList();

        if (uiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) uiLoader.getParent()).removeView(uiLoader);
        }

        uiLoader.setOnRetryClickListener(this);

        //返回view
        return uiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        //view加载
        view = layoutInflater.inflate(R.layout.fragment_recommend, container,false);
        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recommend_list);
        //布局管理区
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);

            }
        });
        //设置适配器
        recommendListAdapter = new RecommendListAdapter();
        recyclerView.setAdapter(recommendListAdapter);
        return view;
    }


    private void upRecommendUI(List<Album> albumList) {
        //把数据给适配器，并更新ui
    }

    @Override
    public void onCommendListLoaded(List<Album> result) {
        //获取当推荐内容改方法被调用
        recommendListAdapter.setData(result);
        uiLoader.updateStatus(UILoader.UIStatus.SUCCESS);

    }

    @Override
    public void onNetworkError() {
        uiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        uiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        uiLoader.updateStatus(UILoader.UIStatus.LOADING);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册，避免内存泄漏
        if (mRecommendPrestneter != null) {
            mRecommendPrestneter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void onRetryClick() {
        //表示网络不佳，用户点击重新刷新
        if (mRecommendPrestneter != null){
            mRecommendPrestneter.getRecommendList();
        }
    }
}
